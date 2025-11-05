package com.object;
import javafx.scene.image.Image;

public class Effect {
    private long fireballEffect;
    private long fastBallEffect;
    private long bigBallEffect; 
    private long bigPaddleEffect;  
    private long shootingEffect;
    private long fire;
    
    private static Image fireballImg = new Image("file:assets/powerup/FireBall.png");
    private static Image fastballImg = new Image("file:assets/powerup/FastBall.png");
    private static Image bigballImg = new Image("file:assets/powerup/BigBall.png");
    private static Image boardImg = new Image("file:assets/powerup/Board.png");
    private static Image bigPaddleImg = new Image("file:assets/powerup/BigPaddle.png");
    private static Image shootingImg = new Image("file:assets/powerup/Shooting.png");

    public Effect() {
        fireballEffect = 0;
        fastBallEffect = 0;
        bigBallEffect = 0;
        shootingEffect = 0;
        fire = 0;
    }
    
    
    

    public long getFire() {
        return fire;
    }


    public void setFire(long fire) {
        this.fire = fire;
    }




    public long getShootingEffect() {
        return shootingEffect;
    }



    public void setShootingEffect(long shootingEffect) {
        this.shootingEffect = shootingEffect;
    }



    public long getBigPaddleEffect() {
        return bigPaddleEffect;
    }



    public void setBigPaddleEffect(long bigPaddleEffect) {
        this.bigPaddleEffect = bigPaddleEffect;
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
        return bigBallEffect;
    }

    public void setBigBallEffect(long bigBallEffect) {
        this.bigBallEffect = bigBallEffect;
    }

    public static Image getFireballImg() {
        return fireballImg;
    }

    public static Image getBigPaddleImg() {
        return bigPaddleImg;
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




    public static Image getShootingImg() {
        return shootingImg;
    }

    


    
}
