package com.object;

import java.util.List;

import com.abstracts.GameObject;
import com.abstracts.MovableObject;
import com.controller.GameController;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PowerUp extends MovableObject {
    private String type;
    private boolean isCollected;
    Image image;

    public PowerUp() {
        super();
        isCollected = false;
        type = "";
    }

    public PowerUp(float x, float y, float dx, float dy, String type, long timeLimit) {
        super(x, y, 20, 20, dx, dy, 50);
        this.type = type;
        isCollected = false;
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }

    public PowerUp(float x, float y, float dx, float dy, float speed, String type) {
        super(x, y, 20, 20, dx, dy, speed);
        this.type = type;
        isCollected = false;
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }

    public PowerUp(float x, float y, String type) {
        super(x, y, 20, 20, 0, 1, 5);
        this.type = type;
        isCollected = false;
        image = new Image("file:assets/powerup/" + getType() + ".png");
    }




    public boolean isDead() {
        return getY() > 720;
    }

    public boolean getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public boolean isCollision(GameObject other) {
        return getY() + getHeight() >= other.getY() &&
                getY() <= other.getY() + other.getHeight() &&
                getX() + getWidth() >= other.getX() &&
                getX() <= other.getX() + other.getWidth();
    }

    @Override
    public void render(GraphicsContext gc, int LEFT) {
        gc.drawImage(image, getX() + LEFT, getY(), getWidth(), getHeight());
    }


    public void x3Balls(List<Ball> balls) {
        int n = balls.size();
        for (int i = 0; i < n; i++) {
            Ball nextBall = balls.get(i).copy();

            nextBall.setAngle(nextBall.getAngle() + 20);
            
            balls.add(nextBall.copy());


            nextBall.setAngle(nextBall.getAngle() - 40);
            balls.add(nextBall.copy());

            
        }
    }

    public void active(int lives, List<Ball> balls, Effect effect, Paddle paddle) {
        switch (getType()) {
            case "HP":
                GameController.getInstance().setLive(Math.min(3, lives + 1));
                break;
            case "x3Ball":
                x3Balls(balls);
                break;
            case "FireBall":
                for (int i = 0; i < balls.size(); i++){
                    if (!(balls.get(i) instanceof FireBall )){
                            balls.set(i, new FireBall(balls.get(i).getX(), balls.get(i).getY(), 
                            balls.get(i).getDx(), balls.get(i).getDy(),balls.get(i).getRadius(),
                            balls.get(i).getSpeed(),balls.get(i).getAngle()));
                    }
                }
                effect.setFireballEffect(5000);

                break;
            case "FastBall":
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).setSpeed(12);
                }
                effect.setFastBallEffect(5000);

                break;
            case "BigBall":
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).setRadius(16);
                }
                effect.setBigBallEffect(5000);

                break;

            case "BigPaddle":
                if(paddle.getWidth() == 100) {
                    paddle.setX(Math.max(0, paddle.getX() - 50));
                }

                paddle.setWidth(200);
                effect.setBigPaddleEffect(5000);

                break;
            case "Shooting":
                effect.setShootingEffect(5000);
                effect.setFire(5000);

                break;
            default:
                break;
        }

        setIsCollected(true);
    }



}