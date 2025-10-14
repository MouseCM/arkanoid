package com.object;

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
    Image image;

    public PowerUp() {
        super();
        isCollected = false;
        PUType = "";
    }

    public PowerUp(float x, float y, float dx, float dy, String PUType, long TimeLimit) {
        super(x, y, 20, 20, dx, dy, 50);
        this.PUType = PUType;
        isCollected = false;
        this.TimeLimit = TimeLimit;
        this.EndTime = TimeLimit + System.currentTimeMillis();
        image = new Image("file:assets/powerup/" + getPUType() + ".png");
    }

    public PowerUp(float x, float y, float dx, float dy, float speed, String PUType, long TimeLimit) {
        super(x, y, 20, 20, dx, dy, speed);
        this.PUType = PUType;
        isCollected = false;
        this.TimeLimit = TimeLimit;
        this.EndTime = TimeLimit + System.currentTimeMillis();
        image = new Image("file:assets/powerup/" + getPUType() + ".png");
    }

    public PowerUp(float x, float y, String PUType, long TimeLimit) {
        super(x, y, 20, 20, 0, 1, 5);
        this.PUType = PUType;
        isCollected = false;
        this.TimeLimit = TimeLimit;
        this.EndTime = TimeLimit + System.currentTimeMillis();
        image = new Image("file:assets/powerup/" + getPUType() + ".png");
    }

    public PowerUp(float x, float y, String PUType) {
        super(x, y, 20, 20, 0, 1, 5);
        this.PUType = PUType;
        isCollected = false;
        image = new Image("file:assets/powerup/" + getPUType() + ".png");
    }

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

    public String getPUType() {
        return PUType;
    }

    public void setPUType(String PUType) {
        this.PUType = PUType;
    }



    public boolean isCollision(GameObject other) {
        return getY() + getHeight() >= other.getY() &&
                getY() <= other.getY() + other.getHeight() &&
                getX() + getWidth() >= other.getX() &&
                getX() <= other.getX() + other.getWidth();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
    }


    public void x3Balls(List<Ball> balls) {
        int n = balls.size();
        for (int i = 0; i < n; i++) {
            Ball nextBall = balls.get(i).copy();

            nextBall.setAngle(nextBall.getAngle() + 20);
            balls.add(nextBall.copy());

            nextBall.setAngle(nextBall.getAngle() - 40);
            balls.add(nextBall.copy());
        }
    }

    public void active(int lives, List<Ball> balls) {
        switch (getPUType()) {
            case "HP":
                lives++;
                break;
            case "x3Ball":
                x3Balls(balls);
                break;
            default:
                System.out.println("No action");
                break;
        }

        setIsCollected(true);
    }


}
