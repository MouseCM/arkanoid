package com.controller;

import java.util.List;

import com.abstracts.Brick;
import com.abstracts.GameObject;
import com.object.Ball;
import com.object.BrickParticle;
import com.object.BallParticle;
import com.object.Effect;
import com.object.Paddle;
import com.object.PowerUp;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Renderer {
    private Font pixelFont25 = Font.loadFont("file:assets/fonts/Evil.ttf", 25);
    private Font pixelFont40 = Font.loadFont("file:assets/fonts/Evil.ttf", 40);

    private final long FPS = 60;
    private long timePerFrame = 1000000000 / FPS;
    private long lasttime = 0;


    Image bg = new Image("file:assets/iceburg/background.png");
    Image leftWall = new Image("file:assets/iceburg/wall.png");
    Image rightWall = new Image("file:assets/iceburg/rightwall.png"); 
    
    private GraphicsContext gc;
    private int WIDTH;
    private int HEIGHT;
    private int LEFT = 180;



    public Renderer(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void clear() {
        gc.clearRect(0, 0, WIDTH + 360, HEIGHT);
    }

    public void render(GameObject obj) {
        obj.render(gc, LEFT);
        return;
    }
    


    public void renderHUD(int score, int lives) {
        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont25);
        gc.fillText("Score: " + score, 35, 55);
        gc.fillText("Lives: " + lives, 35, 85);
    }


    public void renderGameOver(boolean won) {
        gc.setFont(pixelFont40);
        gc.setFill(Color.WHITE);

        if (won) {
            gc.fillText("YOU WIN!", WIDTH / 2 - 70 + LEFT, HEIGHT / 2);
        } else {
            gc.fillText("GAME OVER!", WIDTH / 2 - 90 + LEFT, HEIGHT / 2);
        }
    }

    public void renderPaused() {
        gc.setFont(pixelFont25);
        gc.setFill(Color.WHITE);
        gc.fillText("Game Paused", WIDTH / 2 - 70 + LEFT, HEIGHT / 2);
    }

    public void renderBackground(Image image) {
        gc.drawImage(image, 150, 0, 900, 900);
    }

    public void renderEffect (Effect effect){
        long fireball = effect.getFireballEffect();
        long fastBall = effect.getFastBallEffect();
        long bigBall = effect.getBigBallEffect();
        long bigPaddle = effect.getBigPaddleEffect();
        long high = 25;

        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont25);
       if (fireball > 0){
            gc.drawImage (effect.getBoardImg(), WIDTH + LEFT*2 - 180, high,  180, 28);
            gc.drawImage(effect.getFireballImg(),  WIDTH + LEFT*2 - 150, high,  25, 25);
            gc.fillText(": " + fireball, WIDTH + LEFT*2 - 120, high + 25);
            high += 30;
            effect.setFireballEffect(fireball - 17);
        }

        if (fastBall > 0) {
            gc.drawImage (effect.getBoardImg(), WIDTH + LEFT*2 - 180, high,  180, 28);
            gc.drawImage(effect.getFastballImg(),  WIDTH + LEFT*2 - 150, high,  25, 25);
            gc.fillText(": " + fastBall, WIDTH + LEFT*2 - 120, high + 25);
            high += 30;
            effect.setFastBallEffect(fastBall - 17);
        }        

        if (bigBall > 0) {
            gc.drawImage (effect.getBoardImg(), WIDTH + LEFT*2 - 180, high,  180, 28);
            gc.drawImage (effect.getBigballImg(),  WIDTH + LEFT*2 - 150, high,  25, 25);
            gc.fillText(": " + bigBall, WIDTH + LEFT*2 - 120, high + 25);
            high += 30;
            effect.setBigBallEffect(bigBall - 17);
        }

        if (bigPaddle > 0) {
            gc.drawImage (effect.getBoardImg(), WIDTH + LEFT*2 - 180, high,  180, 28);
            gc.drawImage (effect.getBigPaddleImg(),  WIDTH + LEFT*2 - 150, high,  25, 25);
            gc.fillText(": " + bigPaddle, WIDTH + LEFT*2 - 120, high + 25);
            high += 30;
            effect.setBigPaddleEffect(bigPaddle - 17);
        }
    }

    private void FPS() {
        long frameDuration = System.nanoTime() - lasttime;
        if (frameDuration < timePerFrame) {
            long sleepTime = (timePerFrame - frameDuration) / 1_000_000; // ns -> ms
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        lasttime = System.nanoTime();
    }


    public void renderGame(List<Ball> balls, Paddle paddle, List<Brick> bricks, 
                            List<PowerUp> powerUps, Effect effect, boolean gameOver, boolean gameStarted, 
                            AnimationTimer gameLoop, boolean won, int score, int lives, List<BrickParticle> particles, List<BallParticle> ballParticles) {

        if(gameOver || won) {
            gameLoop.stop();
        }

        FPS();

        

        clear();

        // gc.setFill(Color.BLACK);
        // gc.fillRect(180, 0, 720, 720);

        renderBackground(bg);

        for (int i = 0; i < balls.size(); i++) {
            if(gameStarted) {
                balls.get(i).renderTail(gc, 180);
            }
            render(balls.get(i));
        }

        if(!gameStarted) {
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).renderStartLine(gc, 180);
            }
        }
        
        gc.drawImage(leftWall, 0,  0, 200, 720);
        gc.drawImage(rightWall, 880, 0, 200, 720);


        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                render(brick);
            }
        }

        for (PowerUp powerUp : powerUps) {
            if (!powerUp.getIsCollected()) {
                render(powerUp);
            }
        }
        
          for (BrickParticle p : particles) {
            p.render(gc, LEFT);
          }
          gc.setGlobalAlpha(1.0);
        
          for (BallParticle q : ballParticles) {
            q.render(gc, LEFT);
          }
          gc.setGlobalAlpha(1.0);

    
        render(paddle);

        renderHUD(score, lives);

        renderEffect(effect);

        if (gameOver || won) {
            renderGameOver(won);
        }
    }
}