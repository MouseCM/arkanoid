package com.abstracts;

public abstract class MovableObject extends GameObject {
    private int dx;
    private int dy;
    private double speed;

    public MovableObject() {
        super();
        this.dx = 0;
        this.dy = 0;
    }

    public MovableObject(int dx, int dy) {
        super();
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(int x, int y, int width, int height, int dx, int dy, double speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    public void move() {
        setX((int) (getX() + speed * dx));
        setY((int) (getY() + speed * dy));
    }


    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
