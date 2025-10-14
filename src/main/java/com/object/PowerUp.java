package com.object;

import java.security.NoSuchAlgorithmException;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class PowerUp extends MovableObject {
    private String PUType;
    private boolean isCollected;
    private long TimeLimit = 0;
    private long EndTime = 0;
    public void setTimeLimit (long TimeLimit ) {
        this.TimeLimit = TimeLimit;
    }
    public long getTimeLimit () {
        return TimeLimit;
    }
    public boolean isExpired (long time){
        if (getTimeLimit() > 0 )
        return time >= EndTime;
        return false;
    }
    public boolean isDead() {
        return getY() > 720;
    }

    public boolean getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public PowerUp() {
        super();
        isCollected = false;
        PUType = "";
    }

    public String getPUType() {
        return PUType;
    }

    public void setPUType(String PUType) {
        this.PUType = PUType;
    }

    public PowerUp(String PUType) {
        super();
        this.PUType = PUType;
        isCollected = false;
    }

    public PowerUp(float x, float y, float dx, float dy, String PUType, long TimeLimit) {
        super(x, y, 20, 20, dx, dy, 50);
        this.PUType = PUType;
        isCollected = false;
        this.TimeLimit = TimeLimit;
        this.EndTime = TimeLimit + System.currentTimeMillis();
        
    }

    public PowerUp(float x, float y, float dx, float dy, float speed, String PUType, long TimeLimit) {
        super(x, y, 20, 20, dx, dy, speed);
        this.PUType = PUType;
        isCollected = false;
        this.TimeLimit = TimeLimit;
        this.EndTime = TimeLimit + System.currentTimeMillis();
    }

    public PowerUp(float x, float y, String PUType, long TimeLimit) {
        super(x, y, 20, 20, 0, 1, 5);
        this.PUType = PUType;
        isCollected = false;
        this.TimeLimit = TimeLimit;
        this.EndTime = TimeLimit + System.currentTimeMillis();
    }

    public boolean isCollision(GameObject other) {
        return getY() + getHeight() >= other.getY() &&
                getY() <= other.getY() + other.getHeight() &&
                getX() + getWidth() >= other.getX() &&
                getX() <= other.getX() + other.getWidth();
    }

    @Override
    public void render(GraphicsContext gc) {
        String type = "file:assets/powerup/" + PUType + ".png";
        Image image = new Image(type);
        gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
    }


    public void x3Balls(List<Ball> balls) {
        int n = balls.size();
        for (int i = 0; i < n; i++) {
            Ball nextBall = balls.get(i).copy();
            System.out.println(nextBall.getAngle());
            nextBall.setAngle(nextBall.getAngle() + 20);
            System.out.println(nextBall.getAngle());
            balls.add(nextBall.copy());
            nextBall.setAngle(nextBall.getAngle() - 40);
            System.out.println(nextBall.getAngle());
            balls.add(nextBall.copy());
        }
    }

}
