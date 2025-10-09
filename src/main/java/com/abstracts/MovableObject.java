package com.abstracts;

public abstract class MovableObject extends GameObject {
    private double dx;
    private double dy;
    private double speed;

    public MovableObject() {
        super();
        this.dx = 0;
        this.dy = 0;
    }

    public MovableObject(double dx, double dy) {
        super();
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(double x, double y, double width, double height, double dx, double dy, double speed) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    public void update() {
        setX(getX() + speed * dx);
        setY(getY() + speed * dy);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
