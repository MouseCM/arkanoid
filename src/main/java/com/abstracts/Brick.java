package com.abstracts;

public abstract class Brick extends GameObject {
    private int hitPoints;
    private boolean destroyed;
    private String type;
    private int scoreValue;

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

    public boolean takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
            if (hitPoints == 0) {
                destroyed = true;
            }
            return true;
        }
        return false;
    }

}
