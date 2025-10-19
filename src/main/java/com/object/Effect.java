package com.object;


public class Effect {
    private long fireballEffect;
    private long fastBallEffect;
    private long BigBallEffect;


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

    public  void setFireballEffect(long fireballEffect) {
        this.fireballEffect = fireballEffect;
    }
    
    

}
