!defend.

+!defend
    :   not on_own_side & not own_goal_seen 
    <-  look_right;
        !movetoownside.

+!defend
    :   not ball_on_own_side & not teammate_closer_to_ball
    <-  look_right;
        !movetoownnet.

+!defend
    :   on_own_side & ball_on_own_side & ball_to_left
    <-  look_left;
        !movetoball.

+!defend
    :   on_own_side & ball_on_own_side
    <-  look_right;
        !movetoball.

+!movetoownside
    :   not own_goal_seen
    <-  defend.

+!movetoownside
    :   own_goal_seen & not facing_own_goal 
    <-  turn_to_own_goal;
		!movetoownside.

+!movetoownside
    :   own_goal_seen & facing_own_goal & not on_own_side
    <-  run_to_own_goal;
		!movetoownside.

+!movetoownside
    :   on_own_side 
    <-  !findball.

+!movetoownnet
    :   not own_goal_seen
    <-  defend.

+!movetoownnet
    :   own_goal_seen & not facing_own_goal & not at_own_net 
    <-  turn_to_own_goal;
		!movetoownnet.

+!movetoownnet
    :   own_goal_seen & facing_own_goal & not at_own_net 
    <-  run_to_own_goal;
		!movetoownnet.

+!movetoownnet
    :   at_own_net 
    <-  !findball.

+!movetodefend
    :   not own_goal_seen
    <-  defend.

+!movetodefend
    :   own_goal_seen & not facing_own_goal & not at_own_net 
    <-  turn_to_own_goal;
		!movetodefend.

+!movetodefend
    :   own_goal_seen & facing_own_goal & not at_own_net 
    <-  run_to_own_goal;
		!movetodefend.

+!findball
    :   ball_seen & not facing_ball 
    <-  turn_to_ball;
		!defend.

+!findball
    :   not ball_seen & ball_to_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.

+!movetoball
    :   not ball_seen | not facing_ball
    <-  !findball;
		!movetoball.

+!movetoball
    :   ball_seen & facing_ball & at_ball
    <-  !findoppgoal.

+!findoppgoal
    :   not enemy_goal_seen & at_ball & enemy_goal_to_left
    <-  look_left;
        !findoppgoal.

+!findoppgoal
    :   not enemy_goal_seen & at_ball
    <-  look_right;
        !findoppgoal.

+!findoppgoal
    :   enemy_goal_seen & at_ball
    <-  kick_at_net;
        !defend.

+!findoppgoal
    :   enemy_goal_seen & not at_ball
    <-  !movetoball.
