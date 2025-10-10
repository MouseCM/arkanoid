package com.abstracts;

public abstract class Brick extends GameObject {
    private int hitPoints;
    private boolean destroyed;
    private String type;
    private int scoreValue;
    private String BrickType = "file:assets/iceburg/brick1.png";
    public int brickint = 0;

    public void setBrickType(int type) {
        if (type == 1)
            this.BrickType = "file:assets/iceburg/brick1.png";
        else if (type == 2)
            this.BrickType = "file:assets/iceburg/brick2.png";
        else if (type == 3)
            this.BrickType = "file:assets/iceburg/brick3.png";
        else
            this.BrickType = "file:assets/brick1.png";
        brickint = type;
    }

    public String getBrickType() {
        return BrickType;
    }

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
                if (brickint == 1 || brickint == 0) {
                    brickint = 0;
                    destroyed = true;
                } else if (brickint >= 2) {
                    this.setBrickType(brickint - 1);
                    hitPoints = 1;
                }
            }
            return true;
        }
        return false;
    }
}
