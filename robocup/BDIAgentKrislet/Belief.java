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
    BALL_WENT_PAST,
    CLOSEST_TO_BALL,
    TEAMMATE_AT_BALL,
    TEAMMATE_CLOSER_TO_BALL,
    AT_BALL,
    AT_OWN_NET,
    AT_OPPOSING_NET,
}
