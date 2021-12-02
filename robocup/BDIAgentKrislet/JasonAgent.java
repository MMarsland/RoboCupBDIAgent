

// TODO: All all needed improts from Jason
import jason.architecture.AgArch;
import jason.asSyntax.*;
import jason.asSemantics.*;
import jason.environment.*;
import jason.runtime.*;
import jason.JasonException;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.*;
import java.util.*;

/*
/**
*   JasonAgent represents a BDIAgent that runs using the Jason library to make
*   descisions on an agents intents based on it's current beliefs.
*
*   This class extends Jason's AgArch class that allows for Jason to
*   make decisions based on the given belief inputs.
*
*   TODO:
*   Ensure that the returns from the asl files (Literals) can match up to
*   the intents. TransformToIntent fucntion.
*/
// CODE FROM THE FAQ (https://github.com/jason-lang/jason/blob/master/doc/faq.adoc)

public class JasonAgent extends AgArch {

    private boolean running = false;
    private Intent cycleIntent;
    private List<Belief> cyclePerceptions;

    public JasonAgent(String agent_asl) {
        Agent ag = new Agent();
        new TransitionSystem(ag, new Circumstance(), new Settings(), this);

        try {
            ag.initAg(agent_asl);
        }catch(JasonException e){
            System.out.println(e.toString());
        }
    }

    /**
    *   This function should take in the new current perceptions of the
    *   environment, run a resoning cycle,
    *   and return an intent to perform for the current cycle.
    *
    *   This function should stall until the reasoning cycle is complete and
    *   then synchronously return the intent back to Brain.java, we may
    *   need to make some optimizations on this depending on the nautre of
    *   the jason librbary.
    */
    public Intent getIntent(List<Belief> perceptions) {
        //System.out.println("before2");
        cyclePerceptions = perceptions;
        //System.out.println("mid");
        run();
        //System.out.println("end");
        return cycleIntent;
    }

    /**
    *   My over arching assumption about Jason is that calling reasoningCycle
    *   here will call act at the end of a series of reasoning cycles.
    */
    public void run() {
        running = true;

        while (isRunning()) {

          // calls the Jason engine to perform one reasoning cycle
          //System.out.println("in while loop");
          getTS().reasoningCycle();
          //System.out.println("past getTS");
          // Sleep for 1 second so we don't run extra reasoning cycles
          // if we don't need to
          if (canSleep()) {
              sleep();
          }
        }
    }

    /**
    *   This is called during the reasoning cycle to update beliefs from
    *   the current perceptions.
    */
    public List<Literal> perceive() {
        List<Literal> l = new ArrayList<Literal>();
        // TODO
        //l.add(Literal.parseLiteral("x(10)"));
        for (Belief perception : cyclePerceptions) {
            l.add(Literal.parseLiteral(perception.toString().toLowerCase()));
        }
        return l;
    }

    /**
    *   This is called during the reasoning cycle to perform an action.
    *   It will set the cycles Intent and stop the reasoning cycles FROM
    *   running so we can return the selected intent to Brain.java
    */
    public void act(ActionExec action) {

        //System.out.println("in act---");

        // return confirming the action execution was OK
        action.setResult(true);
        //System.out.println("before action Executed");
        actionExecuted(action);

        cycleIntent = Intent.valueOf(action.getActionTerm().toString().toUpperCase());
        running = false;
    }

    /** Sleeps the thread for 1 second */
    public void sleep() {
        try{
            Thread.sleep(0);
        }catch(Exception e){

        }
    }

    /** If the reasoning cycle process is running
    *   this is also called from Transition System during the reasoning cycle
    */
    public boolean isRunning() {
        return running;
    }

    /**
    *
    */
    public boolean canSleep() {
        return true;
    }


    // Not Currently Used
    public String getAgName() {
        return "bob";
    }
    public void sendMsg(jason.asSemantics.Message m) throws Exception {
    }
    public void broadcast(jason.asSemantics.Message m) throws Exception {
    }
    public void checkMail() {
    }

    public static void main(String a[]){
        //new RunLocalMAS().setupLogger();
        JasonAgent jason = new JasonAgent("AgentSpecifications/Krislet.asl");
        jason.run();
    }
}
