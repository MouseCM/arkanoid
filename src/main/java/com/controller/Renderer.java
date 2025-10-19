package com.controller;

import com.abstracts.GameObject;
import com.object.Effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Renderer {
    private Font pixelFont20 = Font.loadFont("file:assets/fonts/Evil.ttf", 20);
    private Font pixelFont40 = Font.loadFont("file:assets/fonts/Evil.ttf", 40);

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
        gc.setFont(pixelFont20);
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
        gc.setFont(pixelFont20);
        gc.setFill(Color.WHITE);
        gc.fillText("Game Paused", WIDTH / 2 - 70 + LEFT, HEIGHT / 2);
    }

    public void renderBackground(Image image) {
        gc.drawImage(image, 180, 0, 720, 720);
    }

    public void renderEffect (Effect effect){
        long fireball = effect.getFireballEffect();
        long high = 25;
        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont20);
       if (effect.getFireballEffect() >0 ){
            gc.drawImage(effect.getFireballIcon(),WIDTH + LEFT*2 - 160, high - 20 , 30, 30);
            gc.fillText(": " + effect.getFireballEffect() , WIDTH + LEFT*2 - 125, high);
            high += 20;
        }

        
        effect.setFireballEffect(fireball - 25);
    }
}