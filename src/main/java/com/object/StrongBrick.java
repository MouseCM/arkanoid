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
        super(x, y, 70, 20, hitPoints, "strong", 10);
        setHasPowerUp(false);
        setImage(hitPoints);
    }

    public void setImage(int hitPoints) {
        if (hitPoints == 1) {
            setImageLocation("file:assets/iceburg/brick1.png");
            img = new Image(getImageLocation());
        }
        else if (hitPoints == 2) {
            setImageLocation("file:assets/iceburg/brick2.png");
            img = new Image(getImageLocation());
        }
        else if (hitPoints == 3) {
            setImageLocation("file:assets/iceburg/brick3.png");
            img = new Image(getImageLocation());
        }
        else {
            setImageLocation("file:assets/iceburg/brick1.png");
            img = new Image(getImageLocation());
        }
    }


    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                gc.drawImage(img, getX(), getY(), getWidth(), getHeight());
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
