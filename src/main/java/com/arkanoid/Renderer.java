package com.arkanoid;

import com.abstracts.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import com.object.Effect;

public class Renderer {
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
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("Score: " + score, 20, 25);
        gc.fillText("Lives: " + lives, WIDTH + LEFT*2 - 80, 25);
    }


    public void renderGameOver(boolean won) {
        gc.setFont(Font.font("Arial", 30));
        gc.setFill(Color.YELLOW);

        if (won) {
            gc.fillText("YOU WIN!", WIDTH / 2 - 70 + LEFT, HEIGHT / 2);
        } else {
            gc.fillText("GAME OVER!", WIDTH / 2 - 90 + LEFT, HEIGHT / 2);
        }
    }

    public void renderPaused() {
        gc.setFont(Font.font("Arial", 30));
        gc.setFill(Color.YELLOW);
        gc.fillText("Game Paused", WIDTH / 2 - 70 + LEFT, HEIGHT / 2);
    }

    public void renderBackground(Image image) {
        gc.drawImage(image, 0, 0, 720, 600);
    }

    public void renderEffect (Effect effect){
        long fireball = effect.getFireballEffect();
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 20));
        if (effect.getFireballEffect() >0 ){
            gc.fillText("FireBall :" + effect.getFireballEffect(), WIDTH + LEFT*2 - 120, 50);
        }
       effect.setFireballEffect(fireball - 25);
    }
}