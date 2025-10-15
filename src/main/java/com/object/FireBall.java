package com.object;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireBall extends Ball {
    private long activeTime;

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public FireBall(float x, float y, float dx, float dy, float radius, float speed, float angle, long curTime) {
        super(x, y, dx, dy, radius, speed, angle);
        setImageLocation("file:assets/ball/fireball.png");
        setImg(getImageLocation());
        System.out.println(getImageLocation());
        setActiveTime(curTime+5000);
    }
    

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImg(), getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }


    
    

    
}
