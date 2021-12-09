!movetopenalty.

/* used for unit testing */
+!movetopenalty
    :ball_seen & at_ball & enemy_goal_seen & testing
    <-  kick_at_net.

+!movetopenalty
    :   close_to_own_penalty | was_close_to_penalty
    <-  +was_close_to_penalty
        !monitorball;
        !findball.

+!movetopenalty
   :    not close_to_own_penalty & not own_penalty_seen & penalty_was_left
   <-   look_left;
        !movetopenalty.

+!movetopenalty
   :    not close_to_own_penalty & not own_penalty_seen
   <-   look_right;
        !movetopenalty.

+!movetopenalty
   :    not close_to_own_penalty & own_penalty_seen & not facing_own_penalty
   <-   turn_to_own_penalty;
        !movetopenalty.

+!movetopenalty
   :    not close_to_own_penalty & own_penalty_seen & facing_own_penalty
   <-   run_to_own_penalty;
        !movetopenalty.

+!monitorball
    : ball_seen
    <- turn_to_ball.

+!monitorball
    : not ball_seen
    <- true.

+!findball
    :   not ball_seen & ball_was_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.

+!findball
    :  ball_seen & (ball_on_own_side | was_ball_on_own_side)
    <- !remeber;
       !gotoball.

+!findball
   :  ball_seen
   <- !remeber;
      !movetopenalty.


+!gotoball
  :   not at_ball & not ball_seen
  <-  !findball.

+!gotoball
  :   not at_ball & ball_seen & not facing_ball
  <-  turn_to_ball;
      !remeber;
      !gotoball.

+!gotoball
  :   not at_ball & ball_seen & facing_ball
  <-  -was_close_to_penalty
      run_to_ball;
      !remeber;
      !findball.

+!gotoball
  :   at_ball
  <-  !kick.


+!kick
  :   not at_ball
  <-  !findball.

+!kick
  :  own_goal_seen
  <- kick_to_defend;
     !findball.

+!kick
    :  not own_goal_seen
    <- !shoot.

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


+!remeber
    :   true
    <- !remeberballside;
       !remeberpenaltydirection;
       !remeberballdirection;
       !remebergoaldirection.


+!remeberballside
    : ball_on_own_side
    <- +was_ball_on_own_side.

+!remeberballside
    : ball_on_enemy_side
    <- -was_ball_on_own_side.

+!remeberballside.

+!remeberpenaltydirection
    : penalty_to_left
    <- +penalty_was_left.

+!remeberpenaltydirection
    : penalty_to_right
    <- -penalty_was_left.

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
