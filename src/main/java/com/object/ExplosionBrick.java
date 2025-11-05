package com.object;

import java.util.List;

import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ExplosionBrick extends Brick {
    Image img;

    public ExplosionBrick(float x, float y) {
        super(x, y, 40, 20, 1, "explosion", 0);
        setHasPowerUp(false);
        img = new Image("file:assets/iceburg/explosionbrick.png");
    }

    public void explode(List<Brick> bricks) {
        float explosionRadius = 50;

        for (int j = bricks.size() - 1; j >= 0; j--) {
            Brick otherBrick = bricks.get(j);
            if (!otherBrick.isDestroyed() && !(otherBrick instanceof UnbreakableBrick)) {

                if (otherBrick.getX() + otherBrick.getWidth() / 2 <= getX() + getWidth() / 2 + explosionRadius * 2 &&
                        otherBrick.getX() + otherBrick.getWidth() / 2 >= getX() + getWidth() / 2 - explosionRadius * 2
                        &&
                        otherBrick.getY() + otherBrick.getHeight() / 2 <= getY() + getHeight() / 2 + explosionRadius &&
                        otherBrick.getY() + otherBrick.getHeight() / 2 >= getY() + getHeight() / 2 - explosionRadius) {

                    // otherBrick.takeHit();
                    otherBrick.setHasPowerUp(true);
                    otherBrick.setDestroyed(true);
                }
            }
        }
    }

    public boolean takeHit(List<Brick> bricks) {
        if (getHitPoints() > 0) {
            setHitPoints(getHitPoints() - 1);
            if (getHitPoints() == 0) {
                setDestroyed(true);
                setHasPowerUp(true);
                explode(bricks);
            }

            return true;
        }
        return false;
    }

    public void render(GraphicsContext gc, int LEFT) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                gc.drawImage(img, getX() + LEFT, getY(), getWidth(), getHeight());
            }
        }
    }

}
