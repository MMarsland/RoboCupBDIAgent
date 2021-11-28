//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008


// Modefied By:  ----


import java.lang.Math;
import java.util.*;
import java.util.regex.*;




/**
*   Handler class for the BDIAgent
*   This class descritizes the envrionment into "Beliefs" and also
*   sends those beliefs to the JasonAgent to get a decision based on the agent's
*   desires as stored initally in the asl file for the agent
*   then an action is returned and executed by this handler
*/

class Brain extends Thread implements SensorInput
{

	private SendCommand	m_krislet;			// robot which is controled by this brain
    volatile private boolean m_timeOver;
    private String m_playMode;
	private Memory m_memory;				// place where all information is stored
    private char m_side;
	private String m_agent_asl;


	//---------------------------------------------------------------------------
    // This constructor:
    // - stores connection to krislet
    // - starts thread for this object
    public Brain(SendCommand krislet, String team, char side, String agent_asl, String playMode){
    	m_timeOver = false;
    	m_krislet = krislet;
    	m_memory = new Memory();
    	//m_team = team;
    	m_side = side;
    	m_agent_asl = agent_asl;
    	m_playMode = playMode;
    	start();
    }



    /**
    *   getPerceptions takes the current environment state percieved by the player
    *   and stored in m_memory and returns a list of descritized perceptions that
    *   the agent will have about the current environment
    */
    public List<Belief> getPerceptions() {
        ObjectInfo ball;
        List<Belief> currentPerceptions = new LinkedList<Belief>();
        // TEMP: Descitizing code goes here to translate the current Environment
        // state into a list of Perceptions for the agent, these

        //TODO-?: Are we using hard negation? Can we remove beliefs if they are
        // not present in the perceptions? How do we establish this in Jason?
        ball = m_memory.getObject("ball");
        if( ball == null )
        {

        }
        else {
            currentPerceptions.add(Belief.BALL_SEEN);
            if( ball.m_direction != 0 ) {

            }
            else {
                currentPerceptions.add(Belief.FACING_BALL);
            }
        }
        return currentPerceptions;
    }




    /**
    *   This function takes in the BDI Agents current intent and sends an
    *   action to krislet for the player to perform on the server.
    */
    public void performIntent(Intent intent) {
        ObjectInfo ball;
        try {
            ball = m_memory.getObject("ball");
            switch (intent) {
                case LOOK_FOR_BALL:
                    System.out.println("LOOK_FOR_BALL");
                    m_krislet.turn(40);
                    m_memory.waitForNewInfo();
                    break;
                case TURN_TO_BALL:
                    System.out.println("TURN_TO_BALL");
                    m_krislet.turn(ball.m_direction);
                    break;
                case RUN_TO_BALL:
                    System.out.println("RUN_TO_BALL");
                    m_krislet.dash(10*ball.m_distance);
                    break;
                default:
                    System.out.println("DEFAULT INTENT (WAITING)");
                    m_memory.waitForNewInfo();
    				break;
            }
        } catch (Exception e) {
            System.out.printf("INTENT FAILED (%s)", intent);
            m_memory.waitForNewInfo();
        }
    }

    /**
    *   The main loop for the agent.
    *
    *   This function runs internally while the game is running, controlling
    *   the player based on the current environement state, JasonAgent and
    *   outputs.
    *
    *   The while loop constantly descritizes envrionment into "Beliefs",
    *   sends those beliefs to the JasonAgent to get an intent,
    *   and then undescritizes that intent into an action to send to krislet.
    *
    */

    public void run()
    {
        // Establish Agent
        JasonAgent agent = new JasonAgent(this.m_agent_asl);



    	// first put it somewhere on my side
    	if(Pattern.matches("^before_kick_off.*",m_playMode))
    	    m_krislet.move( -Math.random()*52.5 , 34 - Math.random()*68.0 );

    	while( !m_timeOver )
    	{
    		// sleep one step to ensure that we will not send
    		// two commands in one cycle.
    		try{
    		    Thread.sleep(1*SoccerParams.simulator_step);
    		}catch(Exception e){
    	    }

            // Get current perceptions
            List<Belief> currentPerceptions = this.getPerceptions();
            // Get an intent from the Jason Agent based on this cycles new
            // current perceptions so we can perform an action
            Intent intent = agent.getIntent(currentPerceptions);
            // Perform the action
            this.performIntent(intent);
        }

    	m_krislet.bye();
    }


    //===========================================================================
    // Here are suporting functions for implement logic


    //===========================================================================
    // Implementation of SensorInput Interface

    //---------------------------------------------------------------------------
    // This function sends see information
    public void see(VisualInfo info)
    {
	m_memory.store(info);
    }


    //---------------------------------------------------------------------------
    // This function receives hear information from player
    public void hear(int time, int direction, String message)
    {
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    public void hear(int time, String message)
    {
	if(message.compareTo("time_over") == 0)
	    m_timeOver = true;

    }




}
