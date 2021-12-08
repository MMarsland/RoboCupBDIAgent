is_on_own_side.
!defend.

/* used for unit testing */
+!defend
    :ball_seen & at_ball & enemy_goal_seen & testing
    <-  kick_at_net.




+!defend
    :   not is_on_own_side
    <-  !movetoownside.

+!defend
    :   is_on_own_side & ball_was_on_own_side
    <-  !movetoball.

+!defend
    :   not was_close_to_own_penalty & not ball_on_own_side & not teammate_closer_to_ball 
    <-  !movetoowngoal.

+!defend
    :   is_on_own_side
    <-  !findball.



/* +!movetoownside
    :   not own_goal_seen
    <-  look_right;
        !movetoownside.

+!movetoownside
    :   own_goal_seen & not facing_own_goal
    <-  turn_to_own_goal;
		!movetoownside.

+!movetoownside
    :   own_goal_seen & facing_own_goal & not is_on_own_side
    <-  run_to_own_goal;
		!movetoownside.

+!movetoownside
    :   is_on_own_side
    <-  !findball. */



+!movetoowngoal
    :   not own_penalty_seen
    <-  look_right;
        !movetoowngoal.

+!movetoowngoal
    :   own_penalty_seen & not own_penalty_seen & not close_to_own_penalty & not ball_on_own_side
    <-  turn_to_own_penalty;
		!movetoowngoal.

+!movetoowngoal
    :   own_penalty_seen & own_penalty_seen & not close_to_own_penalty & not ball_on_own_side
    <-  run_to_own_penalty;
		!movetoowngoal.

+!movetoowngoal
    :   close_to_own_penalty
    <-  +was_close_to_own_penalty;
        !findball.

+!movetoowngoal
    :   ball_on_own_side
    <-  !movetoball.


/* 
 * Plan to find the ball which keeps track of whether it was last to the left or right of the agent. The plan
 * includes a catchall for moving to the ball if it can see it, is at it, but is not facing it (this was done 
 * to break an infinite loop). 
 */


+!findball
    :   ball_seen & at_ball
    <-  +was_at_ball;
        !kick.

+!findball
    :   ball_seen & ball_to_left
    <-  turn_to_ball;
        +ball_was_left;
        !findballside.

+!findball
    :   ball_seen & ball_to_right
    <-  turn_to_ball;
        -ball_was_left;
        !findballside.

+!findball
    :   not ball_seen & ball_was_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.



+!findballside
    :   ball_seen & ball_on_own_side
    <-  +ball_was_on_own_side;
        !defend.

+!findballside
    :   ball_seen & not ball_on_own_side
    <-  -ball_was_on_own_side;
        !defend.

+!findballside
    :   not ball_seen
    <-  !defend.



+!movetoball
    :   at_ball
    <-  +was_at_ball;
        !kick.

+!movetoball
    :   not ball_was_on_own_side | not is_on_own_side
    <-  !defend.

+!movetoball
    :   (not ball_seen | not facing_ball) & not at_ball
    <-  !findball.

+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_left & ball_was_on_own_side & not at_ball & not was_at_ball
    <-  run_to_ball;
        +enemy_goal_was_left;
        -was_close_to_own_penalty;
        !movetoball.

+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_right & ball_was_on_own_side & not at_ball & not was_at_ball
    <-  run_to_ball;
        -enemy_goal_was_left;
        -was_close_to_own_penalty;
        !movetoball.

+!movetoball
    :   ball_seen & facing_ball & ball_was_on_own_side & not at_ball & not was_at_ball
    <-  run_to_ball;
        -was_close_to_own_penalty;
        !movetoball.



+!kick
    :   not was_at_ball
    <-  !movetoball.

+!kick
    :   was_at_ball & (own_penalty_seen | own_goal_seen)
    <-  kick_to_defend;
        -was_at_ball;
        !defend.

+!kick
    :   was_at_ball
    <-  !findoppgoal.

+!findoppgoal
    :   not enemy_goal_seen & was_at_ball & enemy_goal_was_left
    <-  look_left;
        !findoppgoal.

+!findoppgoal
    :   own_goal_seen & was_at_ball
    <-  !kick.

+!findoppgoal
    :   not enemy_goal_seen & was_at_ball
    <-  look_right;
        !findoppgoal.

+!findoppgoal
    :   enemy_goal_seen & was_at_ball
    <-  kick_at_net;
        -was_at_ball;
        !defend.

+!findoppgoal
    :   enemy_goal_seen & not was_at_ball
    <-  !movetoball.


