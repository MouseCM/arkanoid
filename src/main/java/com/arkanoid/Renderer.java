package com.arkanoid;

import com.abstracts.GameObject;
import com.object.Ball;
import com.object.Paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



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

    public void render(Ball obj) {
        obj.render(gc);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        gc.strokeOval(obj.getX() - obj.getRadius(), 
                     obj.getY() - obj.getRadius(),
                     obj.getRadius() * 2, 
                     obj.getRadius() * 2);
        return;
    }

    public void render(Paddle obj) {
        obj.render(gc);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        return;
    }
    

    public void renderHUD(int score, int lives) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 16));
        gc.fillText("Score: " + score, 10, 25);
        gc.fillText("Lives: " + lives, WIDTH - 80, 25);
    }

    public void renderStartScreen() {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("Press SPACE to start", WIDTH / 2 - 100, HEIGHT / 2);
        
        gc.setFont(Font.font("Arial", 14));
        gc.fillText("Use A/D to move", WIDTH / 2 - 110, HEIGHT / 2 + 30);
        gc.fillText("Click canvas to enable controls", WIDTH / 2 - 110, HEIGHT / 2 + 50);
    }

    public void renderGameOver(boolean won) {
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