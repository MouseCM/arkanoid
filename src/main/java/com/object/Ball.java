package com.object;


import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Ball extends MovableObject {
    private float radius;
    private float angle;

    public Ball() {
        super();
        this.radius = 0;
    }

    public Ball(float x, float y, float dx, float dy, float radius, float speed, float angle) {
        super(x, y, 0, 0, dx, dy, speed);
        this.radius = radius;

        setAngle(angle);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        setDx((float) Math.sin(Math.toRadians(angle)));
        setDy((float) Math.cos(Math.toRadians(angle)));
    }

    public void reverseDx() {
        this.setDx(-getDx());
    }

    public void reverseDy() {
        this.setDy(-getDy());
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }

    public boolean willCollision(GameObject other) {
        return getY() + getRadius() + getDy() * getSpeed() >= other.getY() && 
            getY() - getRadius() + getDy() * getSpeed() <= other.getY() + other.getHeight() &&
            getX() + getRadius() + getDx() * getSpeed() >= other.getX() && 
            getX() - getRadius() + getDx() * getSpeed() <= other.getX() + other.getWidth();
    }

    public boolean isCollision(GameObject other) {
        return getY() + getRadius() >= other.getY() && 
            getY() - getRadius() <= other.getY() + other.getHeight() &&
            getX() + getRadius() >= other.getX() && 
            getX() - getRadius() <= other.getX() + other.getWidth();
    }



    public void bouncePaddle(Paddle paddle) {
        if (getX() > paddle.getX() - getRadius() && getX() < paddle.getX() + paddle.getWidth() + getRadius()) {
            float hitPos = (getX() - paddle.getX()) / paddle.getWidth();
            float angle = (float) ((1 - hitPos) * 120) + 120; 
            
            setAngle(angle);
        }
        else {
            reverseDx();
        }
    }

    public boolean isDeath(int HEIGHT) {
        return getY() + getRadius() >= HEIGHT;
    }

    public static Ball copy(Ball ball) {
        return new Ball(ball.getX(), ball.getY(), ball.getDx(), ball.getDy(), ball.getRadius(), ball.getSpeed(), ball.getAngle());
    }

    public Ball copy() {
        return new Ball(getX(), getY(), getDx(), getDy(), getRadius(), getSpeed(), getAngle());
    }
}
