package com.object;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends MovableObject {
    private double radius;
    private double angle;
    private boolean Reversed=false;
    public Ball() {
        super();
        this.radius = 0;
    }
    public void setReversed(boolean r){
        this.Reversed=r;
    }
    public Ball(double x, double y, double dx, double dy, double radius, double speed, double angle) {
        super(x, y, 0, 0, dx, dy, speed);
        this.radius = radius;
        setDx(getSpeed() * Math.cos(Math.toRadians(-45)));
        setDy(getSpeed() * Math.sin(Math.toRadians(-45)));
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        setDx(getSpeed() * Math.sin(Math.toRadians(angle)));
        setDy(getSpeed() * Math.cos(Math.toRadians(angle)));
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

        setDx(1);
        setDy(-1);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius()*2, getRadius()*2);
    }

    @Override
    public void update() {
        double currentSpeed = Math.sqrt(getDx() * getDx() + getDy() * getDy());
        
        if (currentSpeed > 0) {  
            setDx(getDx() / currentSpeed);
            setDy(getDy() / currentSpeed);
        }

        setX(getX() + getDx() * getSpeed());
        setY(getY() + getDy() * getSpeed());
    }

    public boolean isCollision(GameObject other) {
        return getY() + getRadius() >= other.getY() && 
            getY() - getRadius() <= other.getY() + other.getHeight() &&
            getX() >= other.getX() && 
            getX() <= other.getX() + other.getWidth();
            
    }

    public void bouncePaddle(Paddle paddle) {
        setY(paddle.getY() - getRadius());

        double hitPos = (getX() - paddle.getX()) / paddle.getWidth();
        double angle = (hitPos - 0.5) * 120; 
        setAngle(angle);
        if (!Reversed) {
            reverseDy();
            setReversed(true);
        }
    }

    public boolean isDeath(int HEIGHT) {
        return getY() - getRadius() > HEIGHT;
    }
}
