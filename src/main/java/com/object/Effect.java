package com.object;
import javafx.scene.image.Image;

public class Effect {
    private long fireballEffect;
    private Image fireballIcon = new Image ("file:assets/powerup/FireBall.png");
    public  long getFireballEffect() {
        return fireballEffect;
    }
    public Image getFireballIcon() {
        return fireballIcon;
    }
    public  void setFireballEffect(long fireballEffect) {
        this.fireballEffect = fireballEffect;
    }
    
    

}
