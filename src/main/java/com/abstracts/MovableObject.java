package com.abstracts;

public abstract class MovableObject extends GameObject {
    private float dx;
    private float dy;
    private float speed;

    public MovableObject() {
        super();
        this.dx = 0;
        this.dy = 0;
    }

    public MovableObject(float dx, float dy) {
        super();
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(float x, float y, float width, float height, float dx, float dy, float speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    public void update() {
        setX(getX() + speed * dx);
        setY(getY() + speed * dy);
    }


    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
