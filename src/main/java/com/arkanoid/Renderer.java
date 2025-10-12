package com.arkanoid;

import com.abstracts.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Renderer {
    private GraphicsContext gc;
    private int WIDTH;
    private int HEIGHT;

    public Renderer(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void clear() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
    }

    public void render(GameObject obj) {
        obj.render(gc);
        return;
    }


    public void renderHUD(int score, int lives) {

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 16));
        gc.fillText("Score: " + score, 10, 25);
        gc.fillText("Lives: " + lives, WIDTH - 80, 25);
    }


    public void renderGameOver(boolean won) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 30));
        gc.setFill(Color.YELLOW);

        if (won) {
            gc.fillText("YOU WIN!", WIDTH / 2 - 70, HEIGHT / 2);
        } else {
            gc.fillText("GAME OVER!", WIDTH / 2 - 90, HEIGHT / 2);
        }

        gc.setFont(Font.font("Arial", 16));
        gc.setFill(Color.WHITE);
        gc.fillText("Press SPACE to restart", WIDTH / 2 - 70, HEIGHT / 2 + 40);
    }

}