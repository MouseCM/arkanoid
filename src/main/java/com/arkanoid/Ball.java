package com.arkanoid;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends MovableObject {
    private double radius;

    public Ball() {
        super();
        this.radius = 0;
    }

    public Ball(double x, double y, double dx, double dy, double radius, double speed) {
        super(x, y, 0, 0, dx, dy, speed);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void reverseDx() {
        this.setDx(-getDx());
    }

    public void reverseDy() {
        this.setDy(-getDy());
    }

    public void resetBall(Paddle paddle) {
        setX(paddle.getX() + paddle.getWidth() / 2);
        setY(paddle.getY() - getRadius() - 5);

        setDx(3);
        setDy(-3);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius()*2, getRadius()*2);
    }

    public boolean isCollision(GameObject other) {
        return getY() + getRadius() >= other.getY() && 
            getY() - getRadius() <= other.getY() + other.getHeight() &&
            getX() >= other.getX() && 
            getX() <= other.getX() + other.getWidth();
    }

    public void bouncePaddle(Paddle paddle) {
        double hitPos = (getX() - paddle.getX()) / paddle.getWidth();
        setDx((hitPos-0.5) * 8);
        reverseDy();
    }

    public boolean isDeath(int HEIGHT) {
        return getY() - getRadius() > HEIGHT;
    }
}
