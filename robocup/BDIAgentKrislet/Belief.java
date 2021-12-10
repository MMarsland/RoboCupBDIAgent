/**
*   Descritized environment states into a list of beliefs that the
*   BDI agent can have about its Environment
*
*   These beliefs are used by the JasonAgent to make a desision on import junit.framework.TestCase;
*   intention based on the perceved environment
*/
public enum Belief {
    BALL_SEEN,
    FACING_BALL,
    OWN_GOAL_SEEN,
    ENEMY_GOAL_SEEN,
    ENEMY_GOAL_TO_LEFT,
    ENEMY_GOAL_TO_RIGHT,

    BALL_WENT_PAST,
    BALL_TO_LEFT,
    BALL_TO_RIGHT,

    TEAMMATE_AT_BALL,
    TEAMMATE_CLOSER_TO_BALL,
    AT_BALL,
    ENEMY_AT_BALL,
    ENEMY_BLOCKING_SHOT,

    AT_OWN_NET,
    AT_OPPOSING_NET,
    ON_OWN_SIDE,

    BALL_ON_OWN_SIDE,
    BALL_ON_ENEMY_SIDE,
    BALL_MED_DIST_FROM_GOALIE,
    FACING_OWN_GOAL,
    FACING_OPPOSING_GOAL,

    TEAMMATE_AVAILABLE,

    CENTRE_SEEN,
    FACING_CENTRE,
    CENTRE_TO_LEFT,
    CENTRE_TO_RIGHT,
    CLOSE_TO_CENTRE,
    OWN_GOAL_LINE_SEEN, 
    CLOSE_TO_OWN_GOAL_LINE,

    OWN_PENALTY_SEEN,
    FACING_OWN_PENALTY,
    OWN_PENALTY_TO_LEFT,
    OWN_PENALTY_TO_RIGHT,
    CLOSE_TO_OWN_PENALTY,

    


    CLOSE_TO_SAME_GOAL_LINE,
    SAME_GOAL_LINE_SEEN,


    TESTING
}
