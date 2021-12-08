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
    : ball_seen & ball_on_enemy_side
    <- !remeberballdirection;
       !remebergoaldirection;
       !remeberpenaltydirection;
       !movetopenalty.

+!findball
    : ball_seen & not ball_on_enemy
    <- !remeberballdirection;
       !remebergoaldirection;
       !remeberpenaltydirection;
       !gotoball.

+!movetopenalty
    :   close_to_own_penalty | was_close_to_own_penalty
    <-  +was_close_to_own_penalty;
        wait;
        !findball.

+!movetopenalty
   :    not close_to_own_penalty & not own_penalty_seen
   <-   look_left;
        !movetopenalty.

+!movetopenalty
   :    not close_to_own_penalty & own_penalty_seen & not facing_own_penalty
   <-   turn_to_own_penalty;
        !movetopenalty.

+!movetopenalty
   :    not close_to_own_penalty & own_penalty_seen & facing_own_penalty
   <-   run_to_own_penalty;
        !movetopenalty.

+!gotoball
    :   not at_ball & not ball_seen
    <-  !findball.

+!gotoball
    :   not at_ball & ball_seen & not facing_ball
    <-  turn_to_ball;
        !remeberballdirection;
        !remebergoaldirection;
        !remeberpenaltydirection;
        !gotoball.

+!gotoball
    :   not at_ball & ball_seen & facing_ball
    <-  -was_close_to_own_penalty
        run_to_ball;
        !remeberballdirection;
        !remebergoaldirection;
        !remeberpenaltydirection;
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
    <-  kick_to_defend;
        !findball.

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



+!remeberpenaltydirection
    : own_penalty_to_left
    <- +own_penalty_was_left.

+!remeberpenaltydirection
    : own_penalty_to_right
    <- -own_penalty_was_left.

+!remeberpenaltydirection.

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
