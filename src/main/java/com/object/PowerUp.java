package com.object;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class PowerUp extends MovableObject {
    private String type;
    private boolean isCollected;
    private long timeLimit = 0;
    private long endTime = 0;
    Image image;

    public PowerUp() {
        super();
        isCollected = false;
        type = "";
    }

    public PowerUp(float x, float y, float dx, float dy, String type, long timeLimit) {
        super(x, y, 20, 20, dx, dy, 50);
        this.type = type;
        isCollected = false;
        this.timeLimit = timeLimit;
        this.endTime = timeLimit + System.currentTimeMillis();
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }

    public PowerUp(float x, float y, float dx, float dy, float speed, String type, long timeLimit) {
        super(x, y, 20, 20, dx, dy, speed);
        this.type = type;
        isCollected = false;
        this.timeLimit = timeLimit;
        this.endTime = timeLimit + System.currentTimeMillis();
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }

    public PowerUp(float x, float y, String type, long timeLimit) {
        super(x, y, 20, 20, 0, 1, 5);
        this.type = type;
        isCollected = false;
        this.timeLimit = timeLimit;
        this.endTime = timeLimit + System.currentTimeMillis();
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }

    public PowerUp(float x, float y, String type) {
        super(x, y, 20, 20, 0, 1, 5);
        this.type = type;
        isCollected = false;
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }

    public void setTimeLimit (long timeLimit ) {
        this.timeLimit = timeLimit;
    }

    public long getTimeLimit () {
        return timeLimit;
    }

    public boolean isExpired (long time){
        if (getTimeLimit() > 0 )
        return time >= endTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        switch (getType()) {
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
