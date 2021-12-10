/*Run to own goal or perhaps initilize the goalie at own goal at start*/
!goToOwnGoal.

/* used for unit testing */
+!goToOwnGoal
    :   ball_seen & at_ball & enemy_goal_seen & testing
    <-  kick_at_net.


// If you goalie is at goal, add goalKeep goal
+!goToOwnGoal
    :   at_own_net | was_at_own_net
    <-  +was_at_own_net;
        !goalKeep.

+!goToOwnGoal
    :   own_goal_seen & not facing_own_goal
    <-  turn_to_own_goal;
        !goToOwnGoal.

// If the goalie is not near the goal and is not currently trying to run to the ball,
// go to own goal
+!goToOwnGoal
    :   own_goal_seen & facing_own_goal
    <-  run_to_own_goal;
        !goToOwnGoal.

// If the goalie cant see their own goal, search for the goal
+!goToOwnGoal
    :   not own_goal_seen
    <-  look_right;
        !goToOwnGoal.


// If you're facing your own goal, and the ball is near you, kick the ball behind you
+!goalKeep
    :   at_ball & own_goal_seen
    <-  kick_to_defend;
        !goToOwnGoal.

// If youre facing away from your goal, and the ball is near you, kick the ball forward
+!goalKeep
    :   at_ball & not own_goal_seen
    <-  kick_straight;
        !goToOwnGoal.

// If the ball is seen, and you're not directly facing the ball, turn towards the ball
+!goalKeep
    :   ball_seen & not facing_ball & (at_own_net | was_at_own_net)
    <-  turn_to_ball;
        !goalKeep.

// If the ball is a medium distance from the goalie, run towards the ball
+!goalKeep
    :   ball_seen & facing_ball & ball_med_dist_from_goalie & not at_ball & (at_own_net | was_at_own_net)
    <-  !runToBall.

+!goalKeep
    :   at_own_net | was_at_own_net
    <-  !findball.

+!goalKeep
    :   true
    <-  !goToOwnGoal.


// If you see the ball and you're not near the ball, run towards the ball
+!runToBall
    :   ball_seen & facing_ball & not at_ball
    <-  -was_at_own_net;
        run_to_ball;
        !runToBall.

// If you see the ball and you're not near the ball, run towards the ball
+!runToBall
    :   ball_seen & penalty_seen & not at_ball
    <-  turn_to_ball;
        !runToBall.

// Once you get to the ball, perform goal keep tasks
+!runToBall
    :   at_ball
    <-  !goalKeep.

// When running to ball, if you lose sight of the ball, find the ball
+!runToBall
    :   not ball_seen | not facing_ball | not penalty_seen
    <-  !goToOwnGoal.


// If you're not facing the ball direcrly, turn towards the ball
+!findball
    :   ball_seen & not facing_ball
    <-  turn_to_ball;
		!findball.

// Once the ball has been found, perfom goal keeping tasks
+!findball
    :   ball_seen & facing_ball
    <-  turn_to_ball;
        !goalKeep.

+!findball
    :   not ball_seen
    <-  look_left;
        !findball.
