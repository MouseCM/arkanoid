package com.object;
import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireBall extends Ball {
 
    Image img2;

    public FireBall(float x, float y, float dx, float dy, float radius, float speed, float angle) {
        super(x, y, dx, dy, radius, speed, angle);
        System.out.println(x);
        setImg("file:assets/ball/fireball2.png");
    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        gc.drawImage(getImg(), getX() - getRadius() + LEFT, getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }

    @Override
    public void bounceBrick(Brick brick) {
        return;
    }
    

    
}
