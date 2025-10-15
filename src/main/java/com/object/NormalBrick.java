package com.object;

import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class NormalBrick extends Brick {
    Image img;

    public NormalBrick() {
        super();
        setHitPoints(1);
        setType("normal");
        setScoreValue(10);
        setHasPowerUp(false);
    }

    public NormalBrick(float x, float y, float width, float height) {
        super(x, y, width, height, 1, "normal", 10);
        img = new Image("file:assets/iceburg/normalbrick.png");
        setHasPowerUp(false);
    }

    public NormalBrick(float x, float y) {
        super(x, y, 70, 20, 1, "normal", 10);
        img = new Image("file:assets/iceburg/normalbrick.png");
        setHasPowerUp(false);
    }

    public void render(GraphicsContext gc, int LEFT) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                
                gc.drawImage(img, getX() + LEFT, getY(), getWidth(), getHeight());
            }
        }
    }
}
