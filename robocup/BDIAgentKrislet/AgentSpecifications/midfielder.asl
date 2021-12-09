!findball.

/* used for unit testing */
+!findball
    :ball_seen & at_ball & enemy_goal_seen & testing
    <-  kick_at_net.




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
       !remebergoaldirection;
       !remebercentredirection;
       !movetomid.

+!findball
    : ball_seen & not teammate_closer_to_ball
    <- !remeberballdirection;
       !remebergoaldirection;
       !remebercentredirection;
       !gotoball.

+!monitorball
    : ball_seen
    <- turn_to_ball.

+!monitorball
    : not ball_seen
    <- true.

+!movetomid
    :   close_to_centre | was_close_to_centre
    <-  +was_close_to_centre;
        !monitorball;
        !findball.

+!movetomid
   :    not close_to_centre & not centre_seen & centre_was_left
   <-   look_left;
        !movetomid.

+!movetomid
   :    not close_to_centre & not centre_seen
   <-   look_right;
        !movetomid.

+!movetomid
   :    not close_to_centre & centre_seen & not facing_centre
   <-   turn_to_centre;
        !movetomid.

+!movetomid
   :    not close_to_centre & centre_seen & facing_centre
   <-   run_to_centre;
        !findball.

+!gotoball
    :   not at_ball & not ball_seen
    <-  !findball.

+!gotoball
    :   not at_ball & ball_seen & not facing_ball
    <-  turn_to_ball;
        !remeberballdirection;
        !remebergoaldirection;
        !remebercentredirection;
        !gotoball.

+!gotoball
    :   not at_ball & ball_seen & facing_ball
    <-  -was_close_to_centre;
        run_to_ball;
        !remeberballdirection;
        !remebergoaldirection;
        !remebercentredirection;
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



+!remebercentredirection
    : centre_to_left
    <- +centre_was_left.

+!remebercentredirection
    : centre_to_right
    <- -centre_was_left.

+!remebercentredirection.

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
