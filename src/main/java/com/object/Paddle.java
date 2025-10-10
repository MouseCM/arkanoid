package com.object;

import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    public Paddle() {
        super();
    }

    public Paddle(float x, float y, float dx, float dy, float width, float height, float speed) {
        super(x, y, width, height, dx, dy, speed);
    }


    public void moveLeft() {
        setDx(-1);
        update();
    }

    public void moveRight() {
        setDx(1);
        update();
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
