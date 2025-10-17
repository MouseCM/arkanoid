package com.object;

import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
public class StrongBrick extends Brick {
    Image img;

    public StrongBrick (){
        super ();
        setHitPoints(1); 
        setHasPowerUp(false);
        setImage(getHitPoints());
    }
    public StrongBrick(float x, float y, float width, float height, int hitPoints) {
        super(x, y, width, height, hitPoints , "strong", 10);
        setHasPowerUp(false);
        setImage(hitPoints);
    }
    
    public StrongBrick (float x, float y, int hitPoints){
        super(x, y, 50, 25, hitPoints, "strong", 10);
        setHasPowerUp(false);
        setImage(hitPoints);
    }

    public void setImage(int hitPoints) {
        if (hitPoints == 1) {
            img = new Image("file:assets/iceburg/brick1.png");
        }
        else if (hitPoints == 2) {
            img = new Image("file:assets/iceburg/brick2.png");
        }
        else if (hitPoints == 3) {
            img = new Image("file:assets/iceburg/brick3.png");
        }
        else {
            img = new Image("file:assets/iceburg/brick1.png");
        }
    }


    public void render(GraphicsContext gc, int left) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                gc.drawImage(img, getX() + left, getY(), getWidth(), getHeight());
            }
        }
    }
    
    public boolean takeHit() {
        int hp = getHitPoints();

        if (hp > 0) {
            hp--;
            setHitPoints(hp);
            if (hp == 0) {
                setDestroyed(true);
                setHasPowerUp(true);
            }
            else {
                setImage(hp);
            }
            return true;
        }
        return false;
    }
}
