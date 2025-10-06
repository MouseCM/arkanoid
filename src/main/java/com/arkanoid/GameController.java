package com.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private AnimationTimer gameLoop;
    private int score = 0;
    private int lives = 3;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 600;
    private Ball ball;
    private Paddle paddle;

    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        gameCanvas.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));

        ball = new Ball(WIDTH / 2, HEIGHT - 30, 3, -3, 10, 5.0, 45);
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 20, 1, 0, 100, 10, 10);

        startGameLoop();

        gameCanvas.requestFocus();
    }

    private void handleKeyPressed(KeyCode key) {
        if (key == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (key == KeyCode.RIGHT) {
            rightPressed = true;
        }
        if (key == KeyCode.SPACE && !gameStarted) {
            gameStarted = true;
        }
        if (key == KeyCode.R && gameOver) {
            resetGame();
        }
    }
    
    private void handleKeyReleased(KeyCode key) {
        if (key == KeyCode.LEFT) {
            leftPressed = false;
        }
        if (key == KeyCode.RIGHT) {
            rightPressed = false;
        }
    }
    
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {
        if(gameOver) {
            return;
        }

        if (leftPressed && paddle.getX() > 0) {
            paddle.moveLeft();
        }
        if (rightPressed && paddle.getX() < WIDTH - paddle.getWidth()) {
            paddle.moveRight();
        }


        if (!gameStarted) {
            ball.setX(paddle.getX() + paddle.getWidth() / 2);
            ball.setY(paddle.getY() - ball.getRadius() - 5);
            return;
        }


        // bounce wall
        if (ball.getX() - ball.getRadius() <= 0 || 
            ball.getX() + ball.getRadius() >= WIDTH) {
            if(ball.getX() > WIDTH / 2) {
                ball.setX(WIDTH - ball.getRadius());
            }
            else {
                ball.setX(ball.getRadius());
            }
            ball.reverseDx();
        }
        if (ball.getY() - ball.getRadius() <= 0) {
            ball.setY(ball.getRadius());
            ball.reverseDy();
        }

        
        
        if (ball.isDeath(HEIGHT)) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                ball.resetBall(paddle);
                gameStarted = false;
            }
        }

        
        if (ball.isCollision(paddle)) {
            ball.bouncePaddle(paddle);
        }

        ball.update();
    }

    private void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        

        paddle.render(gc);
        ball.render(gc);
        

  
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("Score: " + score, 10, 25);
        gc.fillText("Lives: " + lives, WIDTH - 80, 25);
    }

    private void resetGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        gameStarted = false;
        paddle.setX(WIDTH / 2 - (paddle.getWidth() / 2));
    }
}
