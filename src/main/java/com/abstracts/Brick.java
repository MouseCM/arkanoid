package com.abstracts;

import java.util.List;
import java.util.Random;

import com.object.PowerUp;

public abstract class Brick extends GameObject {
    private int hitPoints;
    private boolean destroyed;
    private String type;
    private int scoreValue;
    private boolean hasPowerUp;

    public Brick() {
        super();
        this.hitPoints = 0;
        this.destroyed = false;
        this.type = "normal";
        this.scoreValue = 0;
    }

    public Brick(float x, float y, float width, float height, int hitPoints, String type, int scoreValue) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.destroyed = false;
        this.type = type;
        this.scoreValue = scoreValue;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public boolean  hasPowerUp(){
        return hasPowerUp;
    }

    public void setHasPowerUp(boolean hasPowerUp){
        this.hasPowerUp = hasPowerUp;
    }

    public boolean takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
            if (hitPoints == 0) {
                destroyed = true;
                setHasPowerUp(true);
            }
            return true;
        }
        return false;
    }

    public void addPowerUp(List<PowerUp> powerUps) {
        setHasPowerUp(false);

        // add PowerUp
        Random rand = new Random();
        int num = rand.nextInt(100);

        if (num <= 10) {
            powerUps.add(new PowerUp(getX(), getY(), "HP"));
        }
        else if(num <= 20) {
            powerUps.add(new PowerUp(getX(), getY(), "FireBall"));
        }
        else if (num <= 80) {
            powerUps.add(new PowerUp(getX(), getY(), "x3Ball"));
        }
        else if (num <= 100) {
            powerUps.add(new PowerUp(getX(), getY(), "FastBall"));
        }
    }


}
