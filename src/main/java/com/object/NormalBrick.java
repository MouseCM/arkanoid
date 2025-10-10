package com.object;

import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;

public class NormalBrick extends Brick {
    public NormalBrick() {
        super();
        setHitPoints(1);
        setType("normal");
        setScoreValue(10);
    }

    public NormalBrick(float x, float y, float width, float height) {
        super(x, y, width, height, 1, "normal", 10);
    }

    public NormalBrick(float x, float y) {
        super(x, y, 30, 10, 1, "normal", 10);
    }
    
    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            gc.setFill(javafx.scene.paint.Color.BLUE);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
