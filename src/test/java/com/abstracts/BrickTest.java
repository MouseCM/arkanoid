package com.abstracts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.object.NormalBrick;

public class BrickTest {
    @Test
    public void testTakeHit() {
        Brick brick = new NormalBrick(0, 0);
        Assertions.assertFalse(brick.isDestroyed());
        Assertions.assertEquals(1, brick.getHitPoints()); 

        boolean hitResult1 = brick.takeHit();
        Assertions.assertTrue(hitResult1);
        Assertions.assertEquals(0, brick.getHitPoints());
        Assertions.assertTrue(brick.isDestroyed());

        boolean hitResult2 = brick.takeHit();
        Assertions.assertFalse(hitResult2);
        Assertions.assertEquals(0, brick.getHitPoints());
        Assertions.assertTrue(brick.isDestroyed());
    }
}
