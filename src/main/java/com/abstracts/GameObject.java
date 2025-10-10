package com.abstracts;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    private float x;
    private float y;
    private float width;
    private float height;
    private String imageLocation;

    public void setImageLocation(String il) {
        this.imageLocation = il;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public GameObject() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
    }

    public void render(GraphicsContext gc) {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
