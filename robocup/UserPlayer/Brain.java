//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import java.lang.Math;
import java.util.regex.*;
import java.util.Scanner;  // Import the Scanner class
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;


class Brain extends Thread implements SensorInput
{
    private List<Belief> perceptions;
    private ObjectInfo[] environmentObjects;
    private String m_team;
    private int m_number;
    //---------------------------------------------------------------------------
    // This constructor:
    // - stores connection to krislet
    // - starts thread for this object
    public Brain(SendCommand krislet,
		 String team,
		 char side,
		 int number,
		 String playMode)
    {
        m_timeOver = false;
        m_krislet = krislet;
        m_memory = new Memory();
        m_team = team;
        m_side = side;
        m_number = number;
        m_playMode = playMode;
        start();
        perceptions = new LinkedList<Belief>();
        environmentObjects = new ObjectInfo[8];
    }


    public List<Belief> getPerceptions() {
        BallInfo ball = (BallInfo) m_memory.getObject("ball");

        GoalInfo ownGoal;
        GoalInfo opposingGoal;
        LineInfo ownSideLine;
        FlagInfo centre_c = (FlagInfo) m_memory.getObject("flag c");
        FlagInfo ownPenalty_c;

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
        this.environmentObjects[7] = ownSideLine;



		List<ObjectInfo> players = m_memory.getObjects("player");
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
            currentPerceptions.add(Belief.OWN_GOAL_LINE_SEEN);
            if(ownSideLine.m_distance < 10){
                currentPerceptions.add(Belief.CLOSE_TO_OWN_GOAL_LINE);
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

    //---------------------------------------------------------------------------
    // This is main brain function used to make decision
    // In each cycle we decide which command to issue based on
    // current situation. the rules are:
    //
    //	1. If you don't know where is ball then turn right and wait for new info
    //
    //	2. If ball is too far to kick it then
    //		2.1. If we are directed towards the ball then go to the ball
    //		2.2. else turn to the ball
    //
    //	3. If we dont know where is opponent goal then turn wait
    //				and wait for new info
    //
    //	4. Kick ball
    //
    //	To ensure that we don't send commands to often after each cycle
    //	we waits one simulator steps. (This of course should be done better)

    // ***************  Improvements ******************
    // Allways know where the goal is.
    // Move to a place on my side on a kick_off
    // ************************************************

    public void run()
    {
    	ObjectInfo object;
        System.out.println("----------UserPlayer Running---------");
        System.out.println("Commands typed will be sent to the server");
        System.out.println("Commands are typed in the form: action value");
        System.out.println("where action can be one of (turn, dash, or kick)");
        System.out.println("and value can either be any float OR");
        System.out.println("the name of an object to use the DIRECTION of from the list:");
        System.out.println("(ball, ownGoal, opposingGoal, player, enemy, centre, ownPenalty, or ownSideLine)");
        System.out.println("Distances to objects are not used.");
        System.out.println("Examples:");
        System.out.println("turn 55");
        System.out.println("turn -1.5");
        System.out.println("turn ball");
        System.out.println("turn centre");
        System.out.println("dash 50");
        System.out.println("dash 100");
        System.out.println("kick 55");
        System.out.println("kick -10");
        System.out.println("kick opposingGoal");

    	// first put it somewhere on my side
    	if(Pattern.matches("^before_kick_off.*",m_playMode)) {
            Scanner scanner = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter starting position: x y (ex. 100 100)");

            String coordsStr = scanner.nextLine();  // Read user input
            List<Integer> coords = Arrays.asList(coordsStr.split(" ")).stream().map( i -> Integer.parseInt(i)).collect(Collectors.toList());
            System.out.println("Selected Coords: "+coords.get(0)+" "+coords.get(1));  // Output user input
    	    m_krislet.move( coords.get(0), coords.get(1) );
        }
        System.out.println("Position Set");

    	while( !m_timeOver ) {
            try {
                Thread.sleep(SoccerParams.simulator_step);
            } catch(Exception e){
            }
            System.out.println("---Starting Player Cycle---");


            BallInfo ball = null;
            GoalInfo ownGoal = null;
            GoalInfo opposingGoal = null;
            PlayerInfo player = null;
            PlayerInfo enemy = null;
            FlagInfo centre = null;
            FlagInfo ownPenalty = null;
            LineInfo ownSideLine = null;

            try{
                // Gather data
                perceptions = this.getPerceptions();

                // Print perceptions
                ball = (BallInfo) environmentObjects[0];
                ownGoal = (GoalInfo) environmentObjects[1];;
                opposingGoal = (GoalInfo) environmentObjects[2];;
                player = (PlayerInfo) environmentObjects[3];
                enemy =  (PlayerInfo) environmentObjects[4];
                centre = (FlagInfo) environmentObjects[5];
                ownPenalty = (FlagInfo) environmentObjects[6];
                ownSideLine = (LineInfo) environmentObjects[7];

                if (ball != null) {
                    System.out.printf("ball:\n  Distance: %s\n  Direction: %s\n", ball.getDistance(), ball.getDirection());
                } else {
                    System.out.printf("ball: NULL\n");
                }
                if (ownGoal != null) {
                    System.out.printf("ownGoal:\n  Distance: %s\n  Direction: %s\n", ownGoal.getDistance(), ownGoal.getDirection());
                } else {
                    System.out.printf("ownGoal: NULL\n");
                }
                if (opposingGoal != null) {
                    System.out.printf("opposingGoal:\n  Distance: %s\n  Direction: %s\n", opposingGoal.getDistance(), opposingGoal.getDirection());
                } else {
                    System.out.printf("opposingGoal: NULL\n");
                }
                if (player != null) {
                    System.out.printf("player:\n  Distance: %s\n  Direction: %s\n", player.getDistance(), player.getDirection());
                } else {
                    System.out.printf("player: NULL\n");
                }
                if (enemy != null) {
                    System.out.printf("enemy:\n  Distance: %s\n  Direction: %s\n", enemy.getDistance(), enemy.getDirection());
                } else {
                    System.out.printf("enemy: NULL\n");
                }
                if (centre != null) {
                    System.out.printf("centre:\n  Distance: %s\n  Direction: %s\n", centre.getDistance(), centre.getDirection());
                } else {
                    System.out.printf("centre: NULL\n");
                }
                if (ownPenalty != null) {
                    System.out.printf("ownPenalty:\n  Distance: %s\n  Direction: %s\n", ownPenalty.getDistance(), ownPenalty.getDirection());
                } else {
                    System.out.printf("ownPenalty: NULL\n");
                }
                if (ownSideLine != null) {
                    System.out.printf("ownSideLine:\n  Distance: %s\n  Direction: %s\n", ownSideLine.getDistance(), ownSideLine.getDirection());
                } else {
                    System.out.printf("ownSideLine: NULL\n");
                }
                System.out.println("All Perceptions:");
                System.out.println(perceptions);
            } catch (Exception e) {
                System.out.println("Error getting perceptions:" + e);
            }



            String actionName = "";
            String actionValueStr = "";
            Float actionValue = Float.parseFloat("0.0");
            try {
                // ASk for action
                Scanner scanner = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Enter an action: (turn|dash|kick) (float|(ball,ownGoal,opposingGoal,player,enemy,centre,ownPenalty,ownSideLine))");

                String actionStr = scanner.nextLine();  // Read user input
                String[] action = actionStr.split(" ");
                System.out.println("Action: "+actionStr);  // Output user input
                actionName = action[0];
                actionValueStr = action[1];
            } catch (Exception e) {
                System.out.println("Error getting input:" + e);
            }

            try {
            // Convert object names to values
                switch(actionValueStr){
                    case "ball":
                        actionValue = ball.getDirection();
                        break;
                    case "ownGoal":
                        actionValue = ownGoal.getDirection();
                        break;
                    case "opposingGoal":
                        actionValue = opposingGoal.getDirection();
                        break;
                    case "player":
                        actionValue = player.getDirection();
                        break;
                    case "enemy":
                        actionValue = enemy.getDirection();
                        break;
                    case "centre":
                        actionValue = centre.getDirection();
                        break;
                    case "ownPenalty":
                        actionValue = ownPenalty.getDirection();
                        break;
                    case "ownSideLine":
                        actionValue = ownSideLine.getDirection();
                        break;
                    default:
                        actionValue = Float.parseFloat(actionValueStr);
                }
            } catch (Exception e) {
                System.out.println("Error getting action value:" + e);
            }


            // Perform action (Action format: action val1|objectName)
            // turn dir
            // turn objectName.getDir() (goal, e_goal, centre, )
            // dash pow
            // kick dir
            // kick objectName.getDir()
            try {
                switch(actionName){
                    case "turn":
                        m_krislet.turn(actionValue);
                        m_memory.waitForNewInfo();
                        break;
                    case "dash":
                        m_krislet.dash(actionValue);
                        m_memory.waitForNewInfo();
                        break;
                    case "kick":
                        m_krislet.kick(75, actionValue);
                        m_memory.waitForNewInfo();
                        break;
                    case "wait":
                        m_memory.waitForNewInfo();
                        break;
                    default:
                }
            } catch (Exception e) {
                System.out.println("Error performing action:" + e);
            }

        }

        System.out.println("--------BYE-------");
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


    //===========================================================================
    // Private members
    private SendCommand	                m_krislet;			// robot which is controled by this brain
    private Memory			m_memory;				// place where all information is stored
    private char			m_side;
    volatile private boolean		m_timeOver;
    private String                      m_playMode;

}
