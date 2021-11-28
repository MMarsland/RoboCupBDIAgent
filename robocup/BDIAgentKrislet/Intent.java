/**
*   The descritized intents that the BDI agent can have depending on the
*   the beliefs and the desires of the agent.
*
*   These descritized intents are converted to actions that are sent to
*   krislet for the player to perform.
*/
public enum Intent {
    KICK_AT_NET,
    KICK_TO_PLAYER,
    KICK_TO_DEFEND,

    LOOK_FOR_BALL,
    LOOK_FOR_PLAYER,
    LOOK_FOR_OWN_GOAL,
    LOOK_FOR_OPPOSING_GOAL,


    TURN_TO_BALL,
    TURN_TO_OWN_GOAL,
    TURN_TO_OPPOSING_GOAL,
    TURN_TO_PLAYER,

    RUN_TO_PLAYER,
    RUN_TO_BALL,
    RUN_TO_OWN_GOAL,
    RUN_TO_OPPOSING_GOAL,

    REST

}
