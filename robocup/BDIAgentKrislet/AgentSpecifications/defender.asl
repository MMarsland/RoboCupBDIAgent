is_on_own_side.
!defend.

+!defend
    :   not is_on_own_side
    <-  !movetoownside.

+!defend
    :   not ball_on_own_side & not teammate_closer_to_ball & not at_own_net
    <-  !movetoowngoal.

+!defend
    :   is_on_own_side & ball_on_own_side
    <-  !movetoball.

+!defend
    :   is_on_own_side
    <-  !findball.

+!movetoownside
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
    <-  !findball.

+!movetoowngoal
    :   not own_goal_seen
    <-  look_right;
        !movetoowngoal.

+!movetoowngoal
    :   own_goal_seen & not facing_own_goal & not at_own_net & not ball_on_own_side
    <-  turn_to_own_goal;
		!movetoowngoal.

+!movetoowngoal
    :   own_goal_seen & facing_own_goal & not at_own_net & not ball_on_own_side
    <-  run_to_own_goal;
		!movetoowngoal.

+!movetoowngoal
    :   at_own_net
    <-  !findball.

+!movetoowngoal
    :   ball_on_own_side
    <-  !movetoball.

+!movetodefend
    :   not own_goal_seen
    <-  look_right;
        !findowngoal.

+!findowngoal
    : not own_goal_seen
    <- look_right;
        !findowngoal.

+!findowngoal
    : own_goal_seen
    <- look_right;
        !movetoownside.

+!movetodefend
    :   own_goal_seen & not facing_own_goal & not at_own_net
    <-  turn_to_own_goal;
		!movetodefend.

+!movetodefend
    :   own_goal_seen & facing_own_goal & not at_own_net
    <-  run_to_own_goal;
		!movetodefend.

+!findball
    :   ball_seen & not at_ball & ball_to_left
    <-  turn_to_ball;
        +ball_was_left;
        !defend.

+!findball
    :   ball_seen & not at_ball & ball_to_right
    <-  turn_to_ball;
        -ball_was_left;
        !defend.

+!findball
    :   not ball_seen & ball_was_left
    <-  look_left;
        !findball.

+!findball
    :   not ball_seen
    <-  look_right;
        !findball.

+!findball
    :   ball_seen & at_ball
    <-  turn_to_ball;
        !movetoball.

+!movetoball
    :   at_ball
    <-  !kick.

+!movetoball
    :   (not ball_seen | not facing_ball) & not at_ball
    <-  !findball.

+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_left & ball_on_own_side
    <-  run_to_ball;
        +enemy_goal_was_left;
        !movetoball.

+!movetoball
    :   ball_seen & facing_ball & enemy_goal_to_right & ball_on_own_side
    <-  run_to_ball;
        -enemy_goal_was_left;
        !movetoball.

+!movetoball
    :   ball_seen & facing_ball & ball_on_own_side
    <-  run_to_ball;
        !movetoball.

+!movetoball
    : not ball_on_own_side
    <-  !defend.

+!kick
    :   not at_ball
    <-  !movetoball.

+!kick
    :   at_ball & own_goal_seen
    <-  kick_to_defend;
        !defend.

+!kick
    :   at_ball
    <-  !findoppgoal.

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
        !defend.

+!findoppgoal
    :   enemy_goal_seen & not at_ball
    <-  !movetoball.
