/*Run to own goal or perhaps initilize the goalie at own goal at start*/
!goToOwnGoal.

+!goToOwnGoal
    :   not own_goal_seen
    <-  look_right;
        !goToOwnGoal.

+!goToOwnGoal
    :   own_goal_seen & not at_own_net
    <-  turn_to_own_goal;  
        run_to_own_goal;
        !goToOwnGoal.

+!goToOwnGoal
    :   own_goal_seen & at_own_net
    <-  !goalKeep.

+!goalKeep
    :   ball_seen & not facing_ball
    <-  turn_to_ball;
        !goalKeep.

+!goalKeep
    :   ball_seen & facing_ball & ball_med_dist_from_goalie & not at_ball
    <-  !runToBall.

+!goalKeep
    :   ball_seen & not ball_med_dist_from_goalie
    <-  turn_to_ball;
        !goalKeep.

+!goalKeep
    : not ball_seen & not at_own_net
    <-  !goToOwnGoal.

+!goalKeep
    :   not ball_seen
    <-  !findball.

+!goalKeep
    :   ball_seen & at_ball & own_goal_seen
    <-  kick_to_defend;
        !goalKeep.
    
+!goalKeep
    :   ball_seen & at_ball & not own_goal_seen
    <-  kick_straight;
        !goalKeep.

+!runToBall
    :   ball_seen & not at_ball
    <-  run_to_ball;
        !runToBall.

+!runToBall
    :   not ball_seen
    <-  !findball.

+!runToBall
    :   at_ball & not own_goal_seen
    <-  kick_straight;
        !goToOwnGoal.

+!runToBall
    :   at_ball & own_goal_seen
    <-  kick_to_defend
        !goToOwnGoal.

+!findball
    :   ball_seen & not facing_ball 
    <-  turn_to_ball;
		!goalKeep.

+!findball
    :   ball_seen & facing_ball
    <-  turn_to_ball;
        !goalKeep.

+!findball
    :   not ball_seen & ball_to_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.
