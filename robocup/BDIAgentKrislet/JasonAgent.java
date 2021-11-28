

// TODO: All all needed improts from Jason
//import jason.architecture.AgArch;
import java.util.List;
import java.util.LinkedList;

/**
*   JasonAgent represents a BDIAgent that runs using the Jason library to make
*   descisions on an agents intents based on it's current beliefs.
*
*   This class extends Jason's AgArch class that allows for Jason to
*   make decisions based on the given belief inputs.
*
*
*/
class JasonAgent /*extends AgArch*/ {

    // TEMP: For testing of brain.java
    // A basic list of "beliefs", these will actually be stored inside Jason
    public List<Belief> beliefs;

    public JasonAgent(String agent_asl_file_name) {
        // TODO: we need an agent, and a transision system

        //TEMP: For testing of brain.java
        this.beliefs = new LinkedList<Belief>();
    }

    /**
    *   This function should take in the new current perceptions of the
    *   environment, modify the agent's beliefs, run a resoning cycle,
    *   and return an intent to perform for the current cycle.
    *
    *   This function should stall until the reasoning cycle is complete and
    *   then synchronously return the intent back to Brain.java, we may
    *   need to make some optimizations on this depending on the nautre of
    *   the jason librbary.
    */
    public Intent getIntent(List<Belief> perceptions) {

        //TEMP: For testing of brain.java
        // THIS IS HARD NEGATION
        this.beliefs = perceptions;

        if(!this.beliefs.contains(Belief.BALL_SEEN)) {
            return Intent.LOOK_FOR_BALL;
        } else if(!this.beliefs.contains(Belief.FACING_BALL)) {
            return Intent.TURN_TO_BALL;
        } else {
            return Intent.RUN_TO_BALL;
        }
    }
}
