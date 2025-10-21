package com.object;
import javafx.scene.image.Image;

public class Effect {
    private long fireballEffect;
    private long fastBallEffect;
    private long BigBallEffect;     
    private static Image fireballImg = new Image("file:assets/powerup/FireBall.png");
    private static Image fastballImg = new Image("file:assets/powerup/FastBall.png");
    private static Image bigballImg = new Image("file:assets/powerup/BigBall.png");
    private static Image boardImg = new Image("file:assets/powerup/Board.png");
    public Effect() {
        fireballEffect = 0;
        fastBallEffect = 0;
        BigBallEffect = 0;
    }

    public long getFireballEffect() {
        return fireballEffect;
    }

    public void setFireballEffect(long fireballEffect) {
        this.fireballEffect = fireballEffect;
    }

    public long getFastBallEffect() {
        return fastBallEffect;
    }

    public void setFastBallEffect(long fastBallEffect) {
        this.fastBallEffect = fastBallEffect;
    }

    public long getBigBallEffect() {
        return BigBallEffect;
    }

    public void setBigBallEffect(long bigBallEffect) {
        BigBallEffect = bigBallEffect;
    }

    public static Image getFireballImg() {
        return fireballImg;
    }

    public static Image getFastballImg() {
        return fastballImg;
    }

    public static Image getBigballImg() {
        return bigballImg;
    }

    public static Image getBoardImg() {
        return boardImg;
    }


    
}
