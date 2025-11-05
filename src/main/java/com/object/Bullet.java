package com.object;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends MovableObject {
    private float radius;
    private Image img;

    public Bullet() {
        img = new Image("file:assets/ball/normalball.png");
        radius = 5;
        setX(0);
        setY(0);
        setDx(0);
        setDy(-1);
        setSpeed(10);
    }

    public Bullet(float x, float y) {
        img = new Image("file:assets/ball/normalball.png");
        radius = 5;
        setX(x);
        setY(y);
        setDx(0);
        setDy(-1);
        setSpeed(10);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean willCollision(GameObject other) {
        return getY() + getRadius() + getDy() * getSpeed() >= other.getY() &&
                getY() - getRadius() + getDy() * getSpeed() <= other.getY() + other.getHeight() &&
                getX() + getRadius() + getDx() * getSpeed() >= other.getX() &&
                getX() - getRadius() + getDx() * getSpeed() <= other.getX() + other.getWidth();
    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        gc.drawImage(img, getX() - getRadius() + LEFT, getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }
}
