!findball.

+!findball
    :   ball_seen & not at_ball 
    <-  turn_to_ball;
        !movetoball.

+!findball
    :   not ball_seen
    <-  look_for_ball;
        !findball.

+!findball
    :   ball_seen & at_ball & enemy_goal_seen
    <-  kick_at_net;
        !findball.

+!findball
    :   ball_seen & at_ball & not enemy_goal_seen
    <-  !findoppgoal.

+!movetoball
    :   not ball_seen | not facing_ball
    <-  !findball.

+!movetoball
    :   ball_seen & facing_ball
    <-  run_to_ball;
        !findball.

+!findoppgoal
    :   not enemy_goal_seen & at_ball
    <-  look_for_opposing_goal;
        !findoppgoal.

+!findoppgoal
    :   enemy_goal_seen & at_ball
    <-  kick_at_net;
        !findball.

+!findoppgoal
    :   enemy_goal_seen & not at_ball
    <-  !findball.
