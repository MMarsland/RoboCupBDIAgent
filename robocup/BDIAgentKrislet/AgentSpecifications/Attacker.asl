/*Start by always trying to find the ball */
!findball.


/* used for unit testing */
+!findball
    :ball_seen & at_ball & enemy_goal_seen & testing
    <-  kick_at_net.

/* If a teammate is closer to the ball, and the team is attacking, run to the enemy net */
+!findball
    :   teammate_closer_to_ball & enemy_goal_seen & not ball_on_own_side
    <-  run_to_opposing_goal;
        !findball.

/* If facing the ball and not at the ball, then run to the ball. Rememeber the ball is to the left */
+!findball
    :   ball_seen & not at_ball & ball_to_left & facing_ball
    <-  run_to_ball;
        +ball_was_left
        !findball.

/* If facing the ball and not at the ball, then run to the ball. Rememeber the ball is to the right  */
+!findball
    :   ball_seen & not at_ball & ball_to_right & facing_ball
    <-  run_to_ball;
        -ball_was_left
        !findball.

/* If the ball is seen, but not facing the ball, turn left to the ball */
+!findball
    :   ball_seen & not at_ball & ball_to_left
    <-  turn_to_ball;
        +ball_was_left
        !movetoball.

/* If the ball is seen, but not facing the ball, turn right to the ball */
+!findball
    :   ball_seen & not at_ball & ball_to_right
    <-  turn_to_ball;
        -ball_was_left
        !movetoball.

/* If the ball was last seen left, turn left to look for the ball */
+!findball
    :   not ball_seen & ball_was_left
    <-  look_left;
        !findball.

/* Turn right to look for the ball */
+!findball
    :   not ball_seen
    <-  look_right;
        !findball.

/* If at the ball and an enermy is at the ball, kick the ball to the side */
+!findball
    :   ball_seen & at_ball & enemy_at_ball
    <-  kick_to_side;
        !findball.                       

/* If at the ball and the enemy net is seen, kick the ball at their net */
+!findball
    :   ball_seen & at_ball & enemy_goal_seen
    <-  kick_at_net;
        !findball.


/* If at the ball and the enemy net is not seen, find the enemy net */
+!findball
    :   ball_seen & at_ball & not enemy_goal_seen
    <-  !findoppgoal.

/* If the ball is not found, find the ball */
+!movetoball
    :   not ball_seen | not facing_ball
    <-  !findball.

/* If facing the ball, run to the ball. Remeber the enermy goal was left */
+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_left
    <-  run_to_ball;
        +enemy_goal_was_left;
        !findball.

/* If facing the ball, run to the ball. Remeber the enermy goal was right */
+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_right
    <-  run_to_ball;
        -enemy_goal_was_left;
        !findball.

/* If facing the ball, run to the ball*/
+!movetoball
    :   ball_seen & facing_ball
    <-  run_to_ball;
        !findball.

/* if the enemy goal was left, look left for it*/
+!findoppgoal
    :   not enemy_goal_seen & at_ball & enemy_goal_was_left
    <-  look_left;
        !findoppgoal.


/* look right for the enemy goal */
+!findoppgoal
    :   not enemy_goal_seen & at_ball
    <-  look_right;
        !findoppgoal.

/* if the enemy goal is found, kick the ball */
+!findoppgoal
    :   enemy_goal_seen & at_ball
    <-  kick_at_net;
        -at_ball;
        !findball.

/* if the ball hast moved, find the ball again */
+!findoppgoal
    :   not at_ball
    <-  !findball.

