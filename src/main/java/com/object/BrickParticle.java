package com.object;

import java.util.Random;

import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BrickParticle extends MovableObject {

    private float opacity;
    private Color brickPartColor;
    private Random rand = new Random();
    private Color[] icePallet = { Color.web("#AEE4FF"),
            Color.web("#4b98bfff"),
            Color.web("#03a4f5ff"),
            Color.web("#487fecff"),
            Color.web("#1748a9ff"),
    };

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public Color getBrickPartColor() {
        return brickPartColor;
    }

    public BrickParticle(float x, float y, float size, String type) {
        super(x, y, size, size, (float) (Math.random() - 0.5), (float) (Math.random() - 0.5), 8);
        setOpacity(1);
        brickPartColor = icePallet[rand.nextInt(4)];
    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        gc.setGlobalAlpha(getOpacity());
        gc.setFill(getBrickPartColor());
        gc.fillRect(getX() + LEFT, getY(), getWidth(), getHeight());
    }

    @Override
    public void update() {
        setX(getX() + getSpeed() * getDx());
        setY(getY() + getSpeed() * getDy());
        setOpacity(getOpacity() - (float) 0.1);
    }

}
