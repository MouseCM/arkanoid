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
    }

    public NormalBrick(double x, double y) {
        super(x, y, 30, 10, 1, "normal", 10);
    }
    
    public void render(GraphicsContext gc) {
        if (!isDestroyed()) {
            if(brickint!=0){
                Image img = new Image(getBrickType());
                gc.drawImage(img,getX(), getY(), getWidth(), getHeight());
            }   
        }
    }
}
