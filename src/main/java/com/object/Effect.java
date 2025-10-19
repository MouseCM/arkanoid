package com.object;
import javafx.scene.image.Image;

public class Effect {
    private long fireballEffect;
    private long fastBallEffect;
    private long BigBallEffect;
    private Image fireballIcon = new Image("file:assets/icons/fireball_icon.png");

    public long getBigBallEffect() {
        return BigBallEffect;
    }



    public void setBigBallEffect(long bigBallEffect) {
        BigBallEffect = bigBallEffect;
    }



    public Effect() {
        fireballEffect = 0;
        fastBallEffect = 0;
    }

    

    public long getFastBallEffect() {
        return fastBallEffect;
    }

    public void setFastBallEffect(long fastBallEffect) {
        this.fastBallEffect = fastBallEffect;
    }

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
