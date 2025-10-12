package com.object;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PowerUp extends MovableObject {
    private String PUType;
    private boolean isCollected;

    public boolean isDead() {
        return getY() > 720;
    }

    public boolean getisCollected() {
        return isCollected;
    }

    public void setisCollected(boolean isCollected) {
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

    public PowerUp(float x, float y, float dx, float dy, String PUType) {
        super(x, y, 20, 20, dx, dy, 50);
        this.PUType = PUType;
        isCollected = false;
    }

    public PowerUp(float x, float y, float dx, float dy, float speed, String PUType) {
        super(x, y, 20, 20, dx, dy, speed);
        this.PUType = PUType;
        isCollected = false;
    }

    public PowerUp(float x, float y, String PUType) {
        super(x, y, 20, 20, 0, 1, 50);
        this.PUType = PUType;
        isCollected = false;
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


    @Override
    public void update() {
        setX(getX() + getDx());
        setY(getY() + getDy());

    }

}
