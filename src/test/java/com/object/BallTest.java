package com.object;

import org.junit.Assert;
import org.junit.Test;

public class BallTest {
    @Test
    public void testBallMovement() {
        Ball ball = new Ball();
        ball.update();
        Assert.assertEquals(ball.getX(), 0);

        // Paddle paddle = new Paddle(50, 100, 10, 50);

    }
}
