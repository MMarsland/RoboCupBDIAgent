!findball.

+!findball
    :   ball_seen & not at_ball & ball_to_left
    <-  turn_to_ball;
        +ball_was_left
        !movetoball.

+!findball
    :   ball_seen & not at_ball & ball_to_right
    <-  turn_to_ball;
        -ball_was_left
        !movetoball.

+!findball
    :   not ball_seen & ball_was_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
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
    :   ball_seen & facing_ball & enemy_goal_to_left
    <-  run_to_ball;
        +enemy_goal_was_left;
        !findball.

+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_right
    <-  run_to_ball;
        -enemy_goal_was_left;
        !findball.

+!movetoball
    :   ball_seen & facing_ball
    <-  run_to_ball;
        !findball.

+!findoppgoal
    :   not enemy_goal_seen & at_ball & enemy_goal_was_left
    <-  look_left;
        !findoppgoal.


+!findoppgoal
    :   not enemy_goal_seen & at_ball
    <-  look_right;
        !findoppgoal.

+!findoppgoal
    :   enemy_goal_seen & at_ball
    <-  kick_at_net;
        -at_ball;
        !findball.

+!findoppgoal
    :   not at_ball
    <-  !findball.
