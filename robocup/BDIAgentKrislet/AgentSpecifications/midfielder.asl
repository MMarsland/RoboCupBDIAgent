!findball.

+!findball
    :   not ball_seen & ball_was_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.

+!findball
    : ball_seen & teammate_closer_to_ball
    <- !remeberballdirection;
       !movetomid.

+!findball
    : ball_seen & not teammate_closer_to_ball
    <- !remeberballdirection;
       !gotoball.

+!movetomid
    :   close_to_centre
    <-  wait;
        !findball.

+!movetomid
   :    not close_to_centre & not centre_line_seen
   <-   look_left;
        !movetomid.

+!movetomid
   :    not close_to_centre & centre_line_seen
   <-   run_to_centre;
        !findball.

+!gotoball
    :   not at_ball & not ball_seen
    <-  !findball.

+!gotoball
    :   not at_ball & ball_seen & not facing_ball
    <-  turn_to_ball;
        !remebergoaldirection;
        !gotoball.

+!gotoball
    :   not at_ball & ball_seen & facing_ball
    <-  run_to_ball;
        !remebergoaldirection;
        !findball.

+!gotoball
    :   at_ball
    <-  !shoot.

+!shoot
    :   not at_ball
    <-  !findball.

+!shoot
    :   enemy_goal_seen
    <-  kick_at_net;
        !findball.

+!shoot
    :   not enemy_goal_seen
    <-  !findenemygoal.

+!findenemygoal
    :   not enemy_goal_seen & enemy_goal_was_left
    <-  look_left;
        !findenemygoal.

+!findenemygoal
    :   not enemy_goal_seen
    <-  look_right;
        !findenemygoal.

+!findenemygoal
    :   enemy_goal_seen
    <-  !shoot.





+!remeberballdirection
    : ball_to_left
    <- +ball_was_left.

+!remeberballdirection
    : ball_to_right
    <- -ball_was_left.

+!remeberballdirection.

+!remebergoaldirection
    : enemy_goal_to_left
    <- +enemy_goal_was_left.

+!remebergoaldirection
    : enemy_goal_to_right
    <- -enemy_goal_was_left.

+!remebergoaldirection.
