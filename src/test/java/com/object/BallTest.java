package com.object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BallTest {
    @Test
    public void testBallMovement() {
        Ball ball = new Ball( 100, 100, 3, -3, 10, 5.0, 45);
        ball.update();
        assertEquals(100, ball.getX());
        assertEquals(100, ball.getY());
        
    }
}
