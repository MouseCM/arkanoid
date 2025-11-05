package com.object;

import java.util.List;

import com.abstracts.MovableObject;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//import javafx.scene.paint.Color;

public class Paddle extends MovableObject {
    private Image image;

    public Paddle() {
        super();
    }

    public Paddle(float x, float y, float dx, float dy, float width, float height, float speed) {
        super(x, y, width, height, dx, dy, speed);
        image = new Image("file:assets/iceburg/paddle.png");
    }

    public void moveLeft() {
        setDx(-1);
        update();
    }

    public void moveRight() {
        setDx(1);
        update();
    }

    public void fire(List<Bullet> bullets) {
        bullets.add(new Bullet(getX(), getY()));
        bullets.add(new Bullet(getX() + getWidth(), getY()));
    }

    public void update(Scene scene, int WIDTH, boolean aPressed, boolean dPressed) {
        // move with mouse
        int LEFT = 180;
        scene.setOnMouseMoved(event -> {
            float mouseX = (float) event.getSceneX() - getWidth() / 2 - LEFT;
            if (mouseX < 0) {
                setX(0);
                return;
            }
            if (mouseX > WIDTH - getWidth()) {
                setX(WIDTH - getWidth());
                return;
            }

            setX(mouseX);
        });

        // move with keyboard
        if (aPressed && getX() > 0) {
            moveLeft();
        }
        if (dPressed && getX() < WIDTH - getWidth()) {
            moveRight();
        }
    }

    public void render(GraphicsContext gc, int LEFT) {
        gc.drawImage(image, getX() + LEFT, getY(), getWidth(), getHeight());
    }

}
