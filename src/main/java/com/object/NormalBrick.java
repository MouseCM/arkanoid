package com.object;

import com.abstracts.Brick;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class NormalBrick extends Brick {

    public NormalBrick() {
        super();
        setHitPoints(1);
        setType("normal");
        setScoreValue(10);
    }

    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, "normal", 10);
        setImageLocation("file:assets/brick1.png");
    }

    public NormalBrick(double x, double y) {
        super(x, y, 70, 20, 1, "normal", 10);
        setImageLocation("file:assets/brick1.png");
    }

    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                Image img = new Image(getImageLocation());
                gc.drawImage(img, getX(), getY(), getWidth(), getHeight());
            }
        }
    }
}
