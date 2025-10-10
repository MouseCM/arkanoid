package com.object;

import com.abstracts.Brick;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
public class StrongBrick extends Brick {
   // public int brickint = 0;
    private String BrickImageLink = "file:assets/iceburg/brick1.png";

    public StrongBrick (){
        super ();
        setHitPoints(1); 
    }
    public StrongBrick(float x, float y, float width, float height, int hitPoints) {
        super(x, y, width, height, hitPoints , "strong", 10);
        setBrickImageLink(getHitPoints());
        setImageLocation(getBrickImageLink());
    }
    public StrongBrick (float x, float y,int hitPoints){
         super(x, y, 70, 20, hitPoints, "strong", 10);
         setBrickImageLink(getHitPoints());
         setImageLocation(getBrickImageLink());
    }
    public void setBrickImageLink(int hitp) {
        if (hitp == 1)
            this.BrickImageLink = "file:assets/iceburg/brick1.png";
        else if (hitp == 2)
            this.BrickImageLink = "file:assets/iceburg/brick2.png";
        else if (hitp == 3)
            this.BrickImageLink = "file:assets/iceburg/brick3.png";
        else
            this.BrickImageLink = "file:assets/brick1.png";
    }
    public String getBrickImageLink(){
        return this.BrickImageLink;
    }
    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            if (getHitPoints() != 0) {
                Image img = new Image(getImageLocation());
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
            }
            else {
         setBrickImageLink(getHitPoints());
         setImageLocation(getBrickImageLink());
            }
            return true;
        }
        return false;
    }
}
