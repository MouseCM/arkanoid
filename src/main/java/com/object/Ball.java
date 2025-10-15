package com.object;


import com.abstracts.Brick;
import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
        setImageLocation("file:assets/ball/normalball.png");
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


    @Override
    public void render(GraphicsContext gc) {
        // Image image = new Image(getImageLocation());
        // gc.drawImage(image,getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
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


    public void bounceWall(int WIDTH) {
        if (getX() + getDx() * getSpeed() - getRadius() <= 0 || 
            getX() + getDx() * getSpeed() + getRadius() >= WIDTH) {
            setAngle(-getAngle());
        } else if (getY() + getDy() * getSpeed() - getRadius() <= 0) {
            setAngle(180 - getAngle());
        }
    }

    public void followPaddle(Paddle paddle) {
        setX(paddle.getX() + paddle.getWidth() / 2);
        setY(paddle.getY() - getRadius() - 5);
    }

    public void bouncePaddle(Paddle paddle) {
        if (getX() > paddle.getX() - getRadius() && getX() < paddle.getX() + paddle.getWidth() + getRadius()) {
            float hitPos = (getX() - paddle.getX()) / paddle.getWidth();
            float angle = (float) ((1 - hitPos) * 120) + 120; 
            
            setAngle(angle);
        }
        else {
            setDx(-getDx());
        }
    }

    public void bounceBrick(Brick brick) {
        // determine bounce direction
        if (getX() > brick.getX() - getRadius() &&
                getX() < brick.getX() + brick.getWidth() + getRadius()) {
            // reverse vertical direction
            setAngle(180 - getAngle());
        } else {
            // reverse horizontal direction
            setAngle(-getAngle());
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
