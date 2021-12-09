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
    KICK_STRAIGHT,
    KICK_TO_SIDE,

    LOOK_LEFT,
    LOOK_RIGHT,

    TURN_TO_BALL,
    TURN_TO_OWN_GOAL,
    TURN_TO_OPPOSING_GOAL,
    TURN_TO_PLAYER,
    TURN_TO_CENTRE,
    TURN_TO_OWN_PENALTY,

    RUN_TO_PLAYER,
    RUN_TO_BALL,
    RUN_TO_OWN_GOAL,
    RUN_TO_OPPOSING_GOAL,
    RUN_TO_CENTRE,
    RUN_TO_OWN_PENALTY,

    WAIT
}
