package com.object;

import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UnbreakableBrick extends  Brick {
    Image img;

    public UnbreakableBrick (){
        super ();
        setHitPoints(100); 
        setHasPowerUp(false);
        img = new Image("file:assets/iceburg/unbreakablebrick.png");
    }

    public UnbreakableBrick(float x, float y, float width, float height) {
        super(x, y, width, height, 100 , "unbreakable", 0);
        setHasPowerUp(false);
        img = new Image("file:assets/iceburg/unbreakablebrick.png");
    }

    public UnbreakableBrick (float x, float y){
        super(x, y, 40, 20, 100, "unbreakable", 0);
        setHasPowerUp(false);
        img = new Image("file:assets/iceburg/unbreakablebrick.png");
    }


    @Override
    public boolean takeHit() {
        // Unbreakable brick does not take hits
        return false;
    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                gc.drawImage(img, getX() + LEFT, getY(), getWidth(), getHeight());
            }
        }
    }
}
