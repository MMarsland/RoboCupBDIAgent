!findBall.

+!findBall
    :   BALL_SEEN & not AT_BALL 
    <-  TURN_TO_BALL;
        !moveToBall.

+!findBall
    :   not BALL_SEEN;
    <-  LOOK_FOR_BALL;
        !findBall.

+!findBall
    :   BALL_SEEN & AT_BALL & ENEMY_GOAL_SEEN
    <-  KICK_AT_NET;
        !findBall.

+!findBall
    :   BALL_SEEN & AT_BALL & not ENEMY_GOAL_SEEN
    <-  !findOppGoal.

+!moveToBall
    :   BALL_SEEN & not FACING_BALL
    <-  !findBall.

+!moveToBall
    :   BALL_SEEN & FACING_BALL
    <-  RUN_TO_BALL;
        !findBall.

+!findOppGoal
    :   not ENEMY_GOAL_SEEN
    <-  LOOK_FOR_OPPOSING_GOAL;
        !findOppGoal.

+!findOppGoal
    :   ENEMY_GOAL_SEEN & AT_BALL
    <-  KICK_AT_NET;
        !findBall.

+!findOppGoal
    :   ENEMY_GOAL_SEEN & not AT_BALL
    <-  !findBall.