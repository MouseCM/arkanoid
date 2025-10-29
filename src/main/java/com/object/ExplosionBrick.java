package com.object;

import com.abstracts.Brick;

import javafx.scene.image.Image;

public class ExplosionBrick extends Brick {
    Image img;
    
    public ExplosionBrick (float x, float y){
        super(x, y, 40, 20, 100, "explosion", 0);
        setHasPowerUp(false);
        img = new Image("file:assets/iceburg/explosionbrick.png");
    }

    // public void explode() {

    // }


    @Override
    public boolean takeHit() {
        if (getHitPoints() > 0) {
            setHitPoints(getHitPoints()-1);
            if (getHitPoints() == 0) {
                setDestroyed(true);
                setHasPowerUp(true);
                // explode();
            }
            return true;
        }
        return false;
    }


}
