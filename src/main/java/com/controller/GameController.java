package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.abstracts.Brick;
import com.arkanoid.Renderer;
import com.object.Ball;
import com.object.NormalBrick;
import com.object.StrongBrick;
import com.object.Paddle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Random;
import javafx.animation.AnimationTimer;


public class GameController {
    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    Renderer renderer;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 600;
    //
    final long FPS = 60;
    final long timePerFrame = 1000000000 / FPS;
    private long lasttime = 0;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private AnimationTimer gameLoop;
    private int score = 0;
    private int lives = 3;
    // private LevelIndex levelIndex = new LevelIndex();

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private int levelUpdate = 0;

    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();

        renderer = new Renderer(gc, WIDTH, HEIGHT);

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        gameCanvas.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));

        ball = new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 10, 8, 75);
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 20, 1, 0, 100, 10, 10);

        initBricks();
 
        startGameLoop();

        gameCanvas.requestFocus();

    }

    private void handleKeyPressed(KeyCode key) {
        if (key == KeyCode.A) {
            leftPressed = true;
        }
        if (key == KeyCode.D) {
            rightPressed = true;
        }
        if (key == KeyCode.SPACE && !gameStarted) {
            ball.setAngle(75);
            gameStarted = true;
        }
        if (key == KeyCode.SPACE && gameOver) {
            resetGame();
        }
    }

    private void handleKeyReleased(KeyCode key) {
        if (key == KeyCode.A) {
            leftPressed = false;
        }
        if (key == KeyCode.D) {
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
        if (gameOver) {
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
            ball.setY(paddle.getY() - ball.getRadius() - 1);
            return;
        }

        // bounce wall
        if (ball.getX() - ball.getRadius() <= 0) {
            ball.setX(ball.getRadius());
            ball.reverseDx();
        }
        else if (ball.getX() + ball.getRadius() >= WIDTH) {
            ball.setX(WIDTH - ball.getRadius());
            ball.reverseDx();
        }
        else if (ball.getY() - ball.getRadius() <= 0) {
            ball.setY(ball.getRadius());
            ball.reverseDy();
        }

        if (ball.isDeath(HEIGHT)) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
                levelUpdate = 0;
                bricks.clear();
                initBricks();
            } else {
                ball.resetBall(paddle);
                gameStarted = false;
            }
        }

        if (ball.isCollision(paddle)) {
            ball.bouncePaddle(paddle);
        }

        // bounce brick
        for(int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);

            if (!brick.isDestroyed() && ball.isCollision(brick)) {

                float overlapLeft = Math.abs(ball.getX() + ball.getRadius() - brick.getX());
                float overlapRight = Math.abs((brick.getX() + brick.getWidth()) - (ball.getX() - ball.getRadius()));
                float overlapTop = Math.abs((ball.getY() + ball.getRadius()) - brick.getY());
                float overlapBottom = Math.abs((brick.getY() + brick.getHeight()) - (ball.getY() - ball.getRadius()));
                
                float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), 
                                            Math.min(overlapTop, overlapBottom));


                if (minOverlap == overlapLeft) {
                    ball.setX(brick.getX() - ball.getRadius());
                    ball.reverseDx();
                } else if (minOverlap == overlapRight) {
                    ball.setX(brick.getX() + brick.getWidth() + ball.getRadius());
                    ball.reverseDx();
                } else if (minOverlap == overlapTop) {
                    ball.setY(brick.getY() - ball.getRadius());
                    ball.reverseDy();
                } else if(minOverlap == overlapBottom){
                    ball.setY(brick.getY() + brick.getHeight() + ball.getRadius());
                    ball.reverseDy();
                }

                if (brick.takeHit()) {
                    score += brick.getScoreValue();
                }
                
                if (bricks.stream().allMatch(b -> b == null || b.isDestroyed())) {
                    gameOver = true;
                }
                break;
            }
        }

        ball.update();
    }

    private void render() {
        
        //fps stabilize
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
        renderer.clear();
        
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                renderer.render(brick);
            }
        }

        renderer.render(ball);
        renderer.render(paddle);

        

        renderer.renderHUD(score, lives);

        if (!gameStarted && !gameOver) {
            renderer.renderStartScreen();
        }

        if (gameOver) {
            boolean won = lives > 0;
            renderer.renderGameOver(won);
        }
    }

    private void resetGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        gameStarted = false;
        paddle.setX(WIDTH / 2 - (paddle.getWidth() / 2));
        ball.resetBall(paddle);
    }

    private void initBricks() {
        String path = "src/main/resources/layout/normal/";
        Random rand = new Random();
        levelUpdate = rand.nextInt(19) + 1;
        path += Integer.toString(levelUpdate) + ".txt";
        File file = new File("src/main/resources/layout/normal/layout.txt");
        File index = new File(path);
        float[] x= new float[45];
        float[] y= new float[45];
        float width;
        float height;
        int size=0;
        try (Scanner sc = new Scanner(file)) {
            bricks = new ArrayList<>();
            String type;
            int n;
            n = sc.nextInt();
            if(size==0) size=n;
            for (int i = 0; i < n; i++) {
                 x[i] = sc.nextFloat();
                 y[i] = sc.nextFloat();
                width = sc.nextFloat();
                height = sc.nextFloat();
                type = sc.next();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading brick layout: " + e.getMessage());
        }
        try (Scanner sc = new Scanner(index)) {
            for (int i = 0; i < size; i++) {
                int btype = sc.nextInt();
                if (btype > 1) {
                    bricks.add( new StrongBrick(x[i],y[i],btype));
                }
               else if (btype == 1) {
                bricks.add(new NormalBrick(x[i],y[i]));
            }
                else {
                bricks.add(new NormalBrick(x[i],y[i]));
                bricks.get(i).setHitPoints(0);
                bricks.get(i).setDestroyed(true);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error loading brick layout: " + e.getMessage());
        }
    }
}
