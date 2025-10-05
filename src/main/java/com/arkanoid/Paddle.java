package com.arkanoid;

import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    public Paddle() {
        super();
    }

    public Paddle(int x, int y, int dx, int dy, int width, int height, double speed) {
        super(x, y, width, height, dx, dy, speed);
    }


    public void moveLeft() {
        setDx(-1);
        move();
    }

    public void moveRight() {
        setDx(1);
        move();
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
