package com.object;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireBall extends Ball {
    private long activeTime;
    Image img2;

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public FireBall(float x, float y, float dx, float dy, float radius, float speed, float angle, long curTime) {
        super(x, y, dx, dy, radius, speed, angle);
        setImg("file:assets/ball/fireball.png");
        setActiveTime(curTime+5000);
    }
    

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImg(), getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }


    
    

    
}
