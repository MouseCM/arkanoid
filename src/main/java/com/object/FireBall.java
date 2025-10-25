package com.object;
import java.util.List;

import com.abstracts.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireBall extends Ball {
 
    Image img2;

    public FireBall(float x, float y, float dx, float dy, float radius, float speed, float angle) {
        super(x, y, dx, dy, radius, speed, angle);
        setImg("file:assets/ball/fireball2.png");
        setLightImage("file:assets/ball/firelight.png");
        

    }

    @Override
    public void renderTail(GraphicsContext gc, int LEFT) {
        gc.save();
        gc.translate(getX() + LEFT, getY());
        gc.rotate(Math.toDegrees(Math.atan2(getDy(), getDx()))+180);
        gc.drawImage(getLightImage(), -getRadius(), -getRadius() - 2, 100 * getRadius() / 8 , 20 * getRadius() /8);
        gc.restore();
    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        gc.drawImage(getImg(), getX() - getRadius() + LEFT, getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }

    @Override
    public void bounceBrick(Brick brick, List <BallParticle> ballParticles) {
        for (int i = 0; i < 25; i++) {
        BallParticle p = new BallParticle(getX(), getY(), (float) (Math.random() * 3 + 3), this);
        ballParticles.add(p);
        }
        return;
    }
    

    
}
