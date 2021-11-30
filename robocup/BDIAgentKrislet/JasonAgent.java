
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Agent;
import jason.asSemantics.TransitionSystem;
import jason.asSyntax.Literal;


// TODO: All all needed improts from Jason

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;




/**
*   JasonAgent represents a BDIAgent that runs using the Jason library to make
*   descisions on an agents intents based on it's current beliefs.
*
*   This class extends Jason's AgArch class that allows for Jason to
*   make decisions based on the given belief inputs.
*
*
*/
public class JasonAgent extends AgArch {

    // TEMP: For testing of brain.java
    // A basic list of "beliefs", these will actually be stored inside Jason
    public List<Belief> beliefs;
    public String agentType;

    public JasonAgent(String agent_asl_file_name) {
        // TODO: we need an agent, and a transision system
        agentType = agent_asl_file_name;
        Agent ag = new Agent();
        new TransitionSystem(ag, null, null, this);

        try {

            //new TransitionSystem(ag, null, null, this);
            ag.initAg("demo.asl");
           // ag.load("/demo.asl");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        //TEMP: For testing of brain.java


        this.beliefs = new LinkedList<Belief>();
    }

    // a very simple implementation of sleep
    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }


    public void run() {
        try {
            while (isRunning()) {
                // calls the Jason engine to perform one reasoning cycle
                //logger.fine("Reasoning....");
                getTS().reasoningCycle();
                System.out.println("isRunning");
                if (getTS().canSleep())
                    sleep();
            }
        } catch (Exception e) {
            //logger.log(Level.SEVERE, "Run error", e);
        }
    }


    @Override
    public List<Literal> perceive() {
        List<Literal> l = new ArrayList<Literal>();
        l.add(Literal.parseLiteral("x(10)"));
        return l;
    }

    // this method get the agent actions
    @Override
    public void act(ActionExec action) {
        getTS().getLogger().info("Agent " + getAgName() + " is doing: " + action.getActionTerm());
        // set that the execution was ok
        action.setResult(true);
        actionExecuted(action);
    }

    @Override
    public boolean canSleep() {
        return true;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    // Not used methods
    // This simple agent does not need messages/control/...
    @Override
    public void sendMsg(jason.asSemantics.Message m) throws Exception {
    }

    @Override
    public void broadcast(jason.asSemantics.Message m) throws Exception {
    }

    @Override
    public void checkMail() {
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

    //For testing to see if connecting to jason is working
    public static void main(String a[]){
        //new RunLocalMAS().setupLogger();
        JasonAgent jason = new JasonAgent("attacker.asl");
        jason.run();
        System.out.println(jason.agentType);

        // Keep the main open, so you can debug
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                for(;;)
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
            }
        }).run();
    }
}
