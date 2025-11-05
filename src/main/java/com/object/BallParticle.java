package com.object;

import java.util.Random;

import com.abstracts.MovableObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BallParticle extends MovableObject {
    private float opacity; 
    private Color ballPartColor;
    private Random rand = new Random();
    private Color[] normalPallet = { Color.web("#5ddae3ff"),
            Color.web("#18effeff"),
            Color.web("#149099ff"),
            Color.web("#0d6d74ff"),
            Color.web("#053c40ff"),
    };
    private Color[] firePallet = { Color.web("#ffd607ff"),
            Color.web("#ffa600ff"),
            Color.web("#d38c66ff"),
            Color.web("#dd560cff"),
            Color.web("#ff0000ff"),
    };
    

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public Color getBallPartColor() {
        return ballPartColor;
    }

    public BallParticle(float x, float y, float size, Ball ball) {
        super(x, y, size, size, (float) (Math.random()) * ball.getDx(), (float) (Math.random()) * ball.getDy(), 8);
        setOpacity(1);
        ballPartColor = normalPallet[rand.nextInt(4)];
        if (ball instanceof FireBall) {
            ballPartColor = firePallet[rand.nextInt(4)];
        }

    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        gc.setGlobalAlpha(getOpacity());
        gc.setFill(getBallPartColor());
        gc.fillRect(getX() + LEFT, getY(), getWidth(), getHeight());
    }

    @Override
    public void update() {
        setX(getX() + getSpeed() * getDx());
        setY(getY() + getSpeed() * getDy());
        setOpacity(getOpacity() - (float) 0.1);
    }

}
