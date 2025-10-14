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
import com.object.Paddle;
import com.object.PowerUp;
import com.object.StrongBrick;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Renderer renderer;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 600;
    //
    final long FPS = 90;
    final long timePerFrame = 1000000000 / FPS;
    private long lasttime = 0;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean isMousePressed = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean gamePaused = false;
    private AnimationTimer gameLoop;
    private int score = 0;
    private int lives = 3;

    private List<Ball> balls;
    private Paddle paddle;
    private List<Brick> bricks;
    private int levelUpdate = 0;
    private List<PowerUp> powerUps;

    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();

        renderer = new Renderer(gc, WIDTH, HEIGHT);

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        gameCanvas.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));

        balls = new ArrayList<>();
        balls.add(new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 8, 8, 165));
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
            gameStarted = true;
        }
        if (key == KeyCode.SPACE && gameOver) {
            resetGame();
        }
        if (key == KeyCode.ESCAPE) {
            gamePaused = !gamePaused;
            if (gamePaused) {
                gameLoop.stop();
                ScreenController.getCurrentScene().setCursor(Cursor.DEFAULT);

            } else {
                gameLoop.start();
                ScreenController.getCurrentScene().setCursor(Cursor.NONE);
            }
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

    private void handleMouse(Scene scene) {
        scene.setOnMousePressed(event -> {
            isMousePressed = true;

            if (gameStarted == false) {
                gameStarted = true;
            }

            if (gameOver == true) {
                resetGame();
            }
        });

        scene.setOnMouseReleased(event -> {
            isMousePressed = false;
        });

        scene.setOnMouseEntered(e -> {
            scene.setCursor(Cursor.NONE);
        });

        scene.setOnMouseExited(e -> {
            scene.setCursor(Cursor.DEFAULT);
        });
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(ScreenController.getCurrentScene());
                render();
            }
        };
        gameLoop.start();
    }

    private void update(Scene scene) {
        if (gameOver) {
            return;
        }

        handleMouse(ScreenController.getCurrentScene());

        if (leftPressed && paddle.getX() > 0) {
            paddle.moveLeft();
        }
        if (rightPressed && paddle.getX() < WIDTH - paddle.getWidth()) {
            paddle.moveRight();
        }

        scene.setOnMouseMoved(event -> {
            float mouseX = (float) event.getSceneX() - paddle.getWidth() / 2;
            if (mouseX < 0) {
                paddle.setX(0);
                return;
            }
            if (mouseX > WIDTH - paddle.getWidth()) {
                paddle.setX(WIDTH - paddle.getWidth());
                return;
            }

            paddle.setX(mouseX);
        });

        if (!gameStarted) {
            for (Ball ball : balls) {
                ball.setX(paddle.getX() + paddle.getWidth() / 2);
                ball.setY(paddle.getY() - ball.getRadius() - 5);
            }
            return;
        }

        // bounce wall
        for (Ball ball : balls) {
            if (ball.getX() + ball.getDx() * ball.getSpeed() - ball.getRadius() <= 0) {
                ball.setAngle(-ball.getAngle());
            } else if (ball.getX() + ball.getDx() * ball.getSpeed() + ball.getRadius() >= WIDTH) {
                ball.setAngle(-ball.getAngle());
            } else if (ball.getY() + ball.getDy() * ball.getSpeed() - ball.getRadius() <= 0) {
                ball.setAngle(180 - ball.getAngle());
            }
        }

        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball ball = balls.get(i);

            if (ball.isDeath(HEIGHT)) {
                balls.remove(i);
            }

            if (balls.size() == 0) {
                lives--;
                if (lives <= 0) {
                    gameOver = true;
                } else {
                    powerUps.clear();
                    balls.clear();
                    balls.add(new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 8, 8, 165));
                    gameStarted = false;
                }
            }
        }

        for (Ball ball : balls) {
            if (ball.willCollision(paddle)) {
                ball.bouncePaddle(paddle);
            }
        }

        // bounce brick
        for (Ball ball : balls) {
            for (Brick brick : bricks) {

                // add PowerUp
                if (brick.getHasPowerUp() == 1) {
                    Random rand = new Random();

                    int x = rand.nextInt(100);
                    brick.setHasPowerUp(0);
                    if (x <= 10) {
                        powerUps.add(new PowerUp(brick.getX(), brick.getY(), "HP", 0));
                    } else if (x <= 60) {
                        powerUps.add(new PowerUp(brick.getX(), brick.getY(), "x3Ball", 0));

                    } else if (x <= 30) {
                        powerUps.add(new PowerUp(brick.getX(), brick.getY(), "FireBall", 5000));
                    }
                }

                if (!brick.isDestroyed() && ball.willCollision(brick)) {

                    // determine bounce direction
                    if (ball.getX() > brick.getX() - ball.getRadius() &&
                            ball.getX() < brick.getX() + brick.getWidth() + ball.getRadius()) {
                        ball.setAngle(180 - ball.getAngle());
                    } else {
                        ball.setAngle(-ball.getAngle());
                    }

                    if (brick.takeHit()) {
                        score += brick.getScoreValue();
                    }

                    if (bricks.stream().allMatch(b -> b == null || b.isDestroyed())) {
                        gameOver = true;
                    }
                }
            }
        }

        for (PowerUp powerUp : powerUps) {
            if (powerUp.isCollision(paddle)) {
                System.out.println(powerUp.getPUType());

                switch (powerUp.getPUType()) {
                    case "HP":
                        lives++;
                        break;
                    case "x3Ball":
                        powerUp.x3Balls(balls);
                        break;
                    case "FireBall":
                        
                        for (Ball ball : balls) {
                            ball.setImageLocation("file:assets/ball/fireball.png");
                        }

                    default:
                        System.out.println("No action");
                        break;
                }
                System.out.println(powerUps.size());
                powerUp.setIsCollected(true);
            }

            powerUp.update();
        }

        powerUps.removeIf(PowerUp::isDead);
        powerUps.removeIf(PowerUp::getIsCollected);
        
        for (Ball ball : balls) {
            ball.update();
        }

    }

    private void render() {

        // fps stabilize
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

        for (PowerUp powerUp : powerUps) {
            if (!powerUp.getIsCollected()) {
                renderer.render(powerUp);
            }
        }

        for (Ball ball : balls) {
            renderer.render(ball);
        }

        renderer.render(paddle);

        renderer.renderHUD(score, lives);

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
        balls.clear();
        balls.add(new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 10, 8, 165));
        initBricks();
    }

    private void initBricks() {
        String path = "src/main/resources/layout/normal/";
        Random rand = new Random();
        levelUpdate = rand.nextInt(19) + 1;
        path += Integer.toString(levelUpdate) + ".txt";
        File file = new File("src/main/resources/layout/normal/layout.txt");
        File index = new File(path);
        float[] x = new float[45];
        float[] y = new float[45];
        float width;
        float height;
        int size = 0;

        try (Scanner sc = new Scanner(file)) {
            bricks = new ArrayList<>();
            powerUps = new ArrayList<>();
            String type;
            int n;
            n = sc.nextInt();
            if (size == 0) {
                size = n;
            }

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
                    bricks.add(new StrongBrick(x[i], y[i], btype));
                } else if (btype == 1) {
                    bricks.add(new NormalBrick(x[i], y[i]));
                } else {
                    bricks.add(new NormalBrick(x[i], y[i]));
                    bricks.get(i).setHitPoints(0);
                    bricks.get(i).setDestroyed(true);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error loading brick layout: " + e.getMessage());
        }
    }
}
