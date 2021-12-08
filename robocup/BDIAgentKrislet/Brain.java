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

import javax.sound.sampled.Line;


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
    private String m_team;
	private Memory m_memory;				// place where all information is stored
    private char m_side;
	private String m_agent_asl;
    private List<Belief> perceptions;
    private ObjectInfo[] environmentObjects;


	//---------------------------------------------------------------------------
    // This constructor:
    // - stores connection to krislet
    // - starts thread for this object
    public Brain(SendCommand krislet, String team, char side, String agent_asl, String playMode){
    	m_timeOver = false;
    	m_krislet = krislet;
    	m_memory = new Memory();
    	m_team = team;
    	m_side = side;
    	m_agent_asl = agent_asl;
    	m_playMode = playMode;
    	start();
        perceptions = new LinkedList<Belief>();
        environmentObjects = new ObjectInfo[7];
    }

    /**
    *   getPerceptions takes the current environment state percieved by the player
    *   and stored in m_memory and returns a list of descritized perceptions that
    *   the agent will have about the current environment
    */
    public List<Belief> getPerceptions() {
        BallInfo ball = (BallInfo) m_memory.getObject("ball");

        GoalInfo ownGoal;
        GoalInfo opposingGoal;
        LineInfo ownSideLine;
        FlagInfo centre_c = (FlagInfo) m_memory.getObject("flag c");
        FlagInfo ownPenalty_c;

        if(centre_c != null){
            System.out.println("Flag c: " + centre_c.getDistance());
        }

        if(this.m_side == 'r'){
            ownGoal = (GoalInfo) m_memory.getObject("goal r");
            opposingGoal = (GoalInfo) m_memory.getObject("goal l");
            ownPenalty_c = (FlagInfo) m_memory.getObject("flag p r c");
            ownSideLine = (LineInfo) m_memory.getObject("line r");
        }else{
            ownGoal = (GoalInfo) m_memory.getObject("goal l");
            opposingGoal = (GoalInfo) m_memory.getObject("goal r");
            ownPenalty_c = (FlagInfo) m_memory.getObject("flag p l c");
            ownSideLine = (LineInfo) m_memory.getObject("line l");
        }

        this.environmentObjects[0] = ball;
        this.environmentObjects[1] = ownGoal;
        this.environmentObjects[2] = opposingGoal;
        this.environmentObjects[5] = centre_c;
        this.environmentObjects[6] = ownPenalty_c;



		List<ObjectInfo> players = m_memory.getObjects("player");
        List<Belief> previousPerceptions = perceptions;
        List<Belief> currentPerceptions = new LinkedList<Belief>();
        // Descitizing code goes here to translate the current Environment
        // state into a list of Perceptions for the agent, these

        if( ball != null ) {
            currentPerceptions.add(Belief.BALL_SEEN);
            if( ball.m_distance < 0.75) {
                currentPerceptions.add(Belief.AT_BALL);
            }

            if(Math.abs(ball.m_direction) < 10) {
                currentPerceptions.add(Belief.FACING_BALL);
            }

            if(ball.m_direction < 0) {
                currentPerceptions.add(Belief.BALL_TO_LEFT);
            } else {
                currentPerceptions.add(Belief.BALL_TO_RIGHT);
            }

            // TODO: Check to see if you're a goalie
            if(ball.m_distance < 25){
                currentPerceptions.add(Belief.BALL_MED_DIST_FROM_GOALIE);
            }
        }

        if(ownSideLine != null){
            currentPerceptions.add(Belief.SAME_GOAL_LINE_SEEN);
            if(ownSideLine.m_distance < 10){
                currentPerceptions.add(Belief.CLOSE_TO_SAME_GOAL_LINE);
            }

        }

        if(centre_c != null){
            currentPerceptions.add(Belief.CENTRE_SEEN);
            if(Math.abs(centre_c.m_direction) < 10) {
                currentPerceptions.add(Belief.FACING_CENTRE);
            }

            if(centre_c.m_direction < 0) {
                currentPerceptions.add(Belief.CENTRE_TO_LEFT);
            }else{
                currentPerceptions.add(Belief.CENTRE_TO_RIGHT);
            }

            if(centre_c.m_distance < 3){
                currentPerceptions.add(Belief.CLOSE_TO_CENTRE);
            }
        }

        if(ownGoal == null && opposingGoal == null){

        }else{
            if(ownGoal != null){
                currentPerceptions.add(Belief.OWN_GOAL_SEEN);
                if(ownGoal.m_distance < 50.0) {
                    currentPerceptions.add(Belief.ON_OWN_SIDE);
                }
                if( ownGoal.m_distance < 2) {
                    currentPerceptions.add(Belief.AT_OWN_NET);
                }
                if(Math.abs(ownGoal.m_direction) < 10) {
                    currentPerceptions.add(Belief.FACING_OWN_GOAL);
                }
            }

            if(opposingGoal != null){
                currentPerceptions.add(Belief.ENEMY_GOAL_SEEN);
                if(opposingGoal.m_distance > 75.0) {
                    currentPerceptions.add(Belief.ON_OWN_SIDE);
                }
                if( opposingGoal.m_distance < 0.75) {
                    currentPerceptions.add(Belief.AT_OPPOSING_NET);
                }

                if(opposingGoal.m_direction < 0) {
                    currentPerceptions.add(Belief.ENEMY_GOAL_TO_LEFT);
                }else{
                    currentPerceptions.add(Belief.ENEMY_GOAL_TO_RIGHT);
                }
            }
        }

        if(ownPenalty_c != null){
            currentPerceptions.add(Belief.OWN_PENALTY_SEEN);
            if(Math.abs(ownPenalty_c.getDirection()) < 10) {
                currentPerceptions.add(Belief.FACING_OWN_PENALTY);
            }

            if(ownPenalty_c.getDirection() < 0) {
                currentPerceptions.add(Belief.OWN_PENALTY_TO_LEFT);
            }else{
                currentPerceptions.add(Belief.OWN_PENALTY_TO_RIGHT);
            }

            if(ownPenalty_c.getDistance() < 3){
                currentPerceptions.add(Belief.CLOSE_TO_OWN_PENALTY);
            }
        }

        if (ball != null && ownGoal != null) {
            double angle_rads = (Math.abs(ball.m_direction - ownGoal.m_direction) * Math.PI) / 180.0;
            double distance = Math.sqrt(Math.pow(ball.m_distance, 2) + Math.pow(ownGoal.m_distance, 2) - 2.0 * ball.m_distance * ownGoal.m_distance * Math.cos(angle_rads));
            if (distance < 50.0) {
                currentPerceptions.add(Belief.BALL_ON_OWN_SIDE);
            } else {
                currentPerceptions.add(Belief.BALL_ON_ENEMY_SIDE);
            }
        }
        else if (ball != null && opposingGoal != null) {
            double angle_rads = (Math.abs(ball.m_direction - opposingGoal.m_direction) * Math.PI) / 180.0;
            double distance = Math.sqrt(Math.pow(ball.m_distance, 2) + Math.pow(opposingGoal.m_distance, 2) - 2.0 * ball.m_distance * opposingGoal.m_distance * Math.cos(angle_rads));
            if (distance > 60.0) {
                currentPerceptions.add(Belief.BALL_ON_OWN_SIDE);
            } else {
                currentPerceptions.add(Belief.BALL_ON_ENEMY_SIDE);
            }
        }

        if(players.size() > 0){
            if(ball != null){
                double ballDistance = ball.getDistance();
                double ballDirection = ball.getDirection();
                double shortestBallDistance = 0;
                for (ObjectInfo currentPlayer : players) {
                    PlayerInfo player = (PlayerInfo) currentPlayer;

                    if(player.m_teamName.equals(m_team)){

                        if(!currentPerceptions.contains(Belief.TEAMMATE_AVAILABLE)){
                            currentPerceptions.add(Belief.TEAMMATE_AVAILABLE);
                            this.environmentObjects[3] = player;
                        }
                        double angle_rads = (Math.abs(ballDirection - player.m_direction) * Math.PI) / 180.0;
                        shortestBallDistance = Math.sqrt(Math.pow(ballDistance, 2) + Math.pow(player.m_distance, 2) - 2 * ballDistance * player.m_distance * Math.cos(angle_rads));
                        if(shortestBallDistance < ballDistance){
                            currentPerceptions.add(Belief.TEAMMATE_CLOSER_TO_BALL);
                            if(shortestBallDistance < 0.75){
                                currentPerceptions.add(Belief.TEAMMATE_AT_BALL);
                            }
                        }else{
                            currentPerceptions.add(Belief.CLOSEST_TO_BALL);
                        }
                    }else{
                        if(player.m_distance < 2 && ball.m_distance < 0.25){
                            this.environmentObjects[4] = player;
                            currentPerceptions.add(Belief.ENEMY_AT_BALL);
                        }
                    }
                }
            }
        }else{

        }

        return currentPerceptions;
    }


    /**
    *   This function takes in the BDI Agents current intent and sends an
    *   action to krislet for the player to perform on the server.
    */
    public void performIntent(Intent intent) {


		BallInfo ball = (BallInfo) environmentObjects[0];
        GoalInfo ownGoal = (GoalInfo) environmentObjects[1];;
        GoalInfo opposingGoal = (GoalInfo) environmentObjects[2];;
        PlayerInfo player = (PlayerInfo) environmentObjects[3];
        PlayerInfo enemy =  (PlayerInfo) environmentObjects[4];
        FlagInfo centre = (FlagInfo) environmentObjects[5];
        FlagInfo ownPenalty = (FlagInfo) environmentObjects[6];

        try {
            switch(intent){
                case KICK_AT_NET:
                    m_krislet.kick(75, opposingGoal.m_direction);
                    break;
                case KICK_TO_PLAYER:
                    m_krislet.kick(75, player.m_direction);
                    break;
                case KICK_TO_DEFEND:
                    m_krislet.kick(75, 180);
                    break;
                case KICK_TO_SIDE:
                    System.out.println(perceptions.toString());
                    System.out.println("Kicking to side");
                    m_krislet.kick(10, enemy.m_direction + 35);
                    m_krislet.turn(45);
                    break;
                case KICK_STRAIGHT:
                    m_krislet.kick(75,0);
                    break;
                case LOOK_LEFT:
                    m_krislet.turn(-80);
                    m_memory.waitForNewInfo();
                    break;
                case LOOK_RIGHT:
                    m_krislet.turn(80);
                    m_memory.waitForNewInfo();
                    break;
                case TURN_TO_BALL:
                    m_krislet.turn(ball.getDirection());
                    m_memory.waitForNewInfo();
                    break;
                case TURN_TO_OWN_GOAL:
                     m_krislet.turn(ownGoal.getDirection());
                    break;
                case TURN_TO_OPPOSING_GOAL:
                    m_krislet.turn(opposingGoal.getDirection());
                    break;
                case TURN_TO_PLAYER:
                    m_krislet.turn(player.getDirection());
                    break;
                case TURN_TO_CENTRE:
                    m_krislet.turn(centre.getDirection());
                    break;
                case TURN_TO_OWN_PENALTY:
                    m_krislet.turn(ownPenalty.getDirection());
                    break;
                case RUN_TO_PLAYER:
                    m_krislet.dash(100*player.getDistance());
                    break;
                case RUN_TO_BALL:
                    if(ball.getDistance() > 5){
                        m_krislet.dash(50);
                    }else{
                        m_krislet.dash(50*ball.getDistance());
                    }
                    break;
                case RUN_TO_OWN_GOAL:
                    m_krislet.dash(100*ownGoal.getDistance());
                    break;
                case RUN_TO_OPPOSING_GOAL:
                    m_krislet.dash(100*opposingGoal.getDistance());
                    break;
                case RUN_TO_CENTRE:
                    m_krislet.dash(100*centre.getDistance());
                    break;
                case RUN_TO_OWN_PENALTY:
                    m_krislet.dash(100*ownPenalty.getDistance());
                    break;
                case WAIT:
                    m_memory.waitForNewInfo();
                    break;
                default:
                System.out.printf("UNKNOWN INTENT (%s) - WAITING", intent);
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
        System.out.println("BDI Agent Loaded: Begining new game of RoboCup");


    	// first put it somewhere on my side
    	if(Pattern.matches("^before_kick_off.*",m_playMode))
    	    m_krislet.move( -Math.random()*20.5 , Math.random()*30.0 );

    	while( !m_timeOver ){
    		// sleep one step to ensure that we will not send
    		// two commands in one cycle.
    		try{
    		    Thread.sleep(1*SoccerParams.simulator_step);
    		}catch(Exception e){
    	    }

            // Get current perceptions
            perceptions = this.getPerceptions();

            //for (ObjectInfo currentPlayer : players) {
            // Get an intent from the Jason Agent based on this cycles new
            // current perceptions so we can perform an action
            System.out.println("Starting Reasoning:");
            System.out.println(perceptions.toString());
            Intent intent = agent.getIntent(perceptions);

            System.out.println("Got Intent:");
            System.out.println(intent.toString());
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
