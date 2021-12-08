/*Run to own goal or perhaps initilize the goalie at own goal at start*/
!goToOwnGoal.

/* used for unit testing */
+!goToOwnGoal
    :ball_seen & at_ball & enemy_goal_seen & testing
    <-  kick_at_net.


// If the goalie cant see their own goal, search for the goal
+!goToOwnGoal
    :   not own_goal_seen
    <-  look_right;
        !goToOwnGoal.

// If the goalie is not near the goal and is not currently trying to run to the ball, 
// go to own goal
+!goToOwnGoal
    :   own_goal_seen & not at_own_net
    <-  turn_to_own_goal;  
        run_to_own_goal;
        !goToOwnGoal.

// If you goalie is at goal, add goalKeep goal
+!goToOwnGoal
    :   own_goal_seen & at_own_net
    <-  !goalKeep.

// If the ball is seen, and you're not directly facing the ball, turn towards the ball
+!goalKeep
    :   ball_seen & not facing_ball
    <-  turn_to_ball;
        !goalKeep.

// If the ball is a medium distance from the goalie, run towards the ball
+!goalKeep
    :   ball_seen & facing_ball & ball_med_dist_from_goalie & not at_ball
    <-  !runToBall.


// If you run forward to the ball, but lose sight of it, return back to the goal
+!goalKeep
    : not ball_seen & not at_own_net
    <-  !goToOwnGoal.

+!goalKeep
    :   not ball_seen
    <-  !findball.

// If you're facing your own goal, and the ball is near you, kick the ball behind you
+!goalKeep
    :   ball_seen & at_ball & own_goal_seen
    <-  kick_to_defend;
        !goToOwnGoal.

// If youre facing away from your goal, and the ball is near you, kick the ball forward    
+!goalKeep
    :   ball_seen & at_ball & not own_goal_seen
    <-  kick_straight;
        !goToOwnGoal.
    
// If the ball is seen, keep facing the ball
+!goalKeep
    :   ball_seen
    <-  turn_to_ball;
        !findball.

// If you see the ball and you're not near the ball, run towards the ball
+!runToBall
    :   facing_ball & not at_ball
    <-  run_to_ball;
        !runToBall.

// When running to ball, if you lose sight of the ball, find the ball
+!runToBall
    :   not ball_seen | not facing_ball
    <-  !findball.

// Once you get to the ball, perform goal keep tasks
+!runToBall
    :   at_ball
    <-  !goalKeep.

// If you're not facing the ball direcrly, turn towards the ball
+!findball
    :   ball_seen & not facing_ball 
    <-  turn_to_ball;
		!goalKeep.

// Once the ball has been found, perfom goal keeping tasks
+!findball
    :   ball_seen & facing_ball
    <-  !goalKeep.

+!findball
    :   not ball_seen & ball_to_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.

