package com.arkanoid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abstracts.Brick;
import com.object.Ball;
import com.object.NormalBrick;
import com.object.Paddle;
import javafx.scene.image.Image;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Random;

public class Controller {
    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    Renderer renderer;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 600;
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

        ball = new Ball(WIDTH / 2, HEIGHT - 30, 3, -3, 10, 5.0, 45);
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
            ball.setReversed(false);
            Random rand = new Random();
            int n = rand.nextInt(30);
            ball.setAngle(HEIGHT - 75);
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
            ball.setY(paddle.getY() - ball.getRadius() - 5);
            return;
        }

        // bounce wall
        if (ball.getX() - ball.getRadius() <= 0 ||
                ball.getX() + ball.getRadius() >= WIDTH) {
            if (ball.getX() > WIDTH / 2) {
                ball.setX(WIDTH - ball.getRadius());
            } else {
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
        // if (levelUpdate == 0) {
        //     Random rand = new Random();
        //     levelUpdate = rand.nextInt(20);
        //     for (int i = 0; i < bricks.size(); i++) {
        //         if (levelIndex.levels[levelUpdate][i] > 0)
        //             bricks.get(i).setBrickType(levelIndex.levels[levelUpdate][i]);
        //         else
        //             bricks.get(i).setDestroyed(true);
        //     }
        // }
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed()) {
                continue;
            }
            Brick brick = bricks.get(i);
            ball.setReversed(false);
            if (!brick.isDestroyed() && ball.isCollision(brick)) {

                if (brick.takeHit()) {
                    score += brick.getScoreValue();
                }

                double overlapLeft = (ball.getX() + ball.getRadius()) - brick.getX();
                double overlapRight = (brick.getX() + brick.getWidth()) - (ball.getX() - ball.getRadius());
                double overlapTop = (ball.getY() + ball.getRadius()) - brick.getY();
                double overlapBottom = (brick.getY() + brick.getHeight()) - (ball.getY() - ball.getRadius());

                double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
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
                } else {
                    ball.setY(brick.getY() + brick.getHeight() + ball.getRadius());
                    ball.reverseDy();
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
        renderer.clear();
        
        // Image background = new Image("file:assets/iceburg/background.png");
        // gc.drawImage(background, 0, 0, WIDTH, HEIGHT);
        renderer.render(ball);
        renderer.render(paddle);

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                renderer.render(brick);
            }
        }

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
    }

    private void initBricks() {
        String path = "src/main/resources/layout/normal/";
        Random rand = new Random();
        levelUpdate = rand.nextInt(19) + 1;
        path += Integer.toString(levelUpdate) + ".txt";
        File file = new File("src/main/resources/layout/normal/layout.txt");
        File index = new File(path);

        try (Scanner sc = new Scanner(file)) {
            bricks = new ArrayList<>();
            int n;
            double width;
            double height;
            String type;
            n = sc.nextInt();

            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                width = sc.nextDouble();
                height = sc.nextDouble();
                type = sc.next();

                if (type.equals("normal")) {
                    bricks.add(new NormalBrick(x, y, width, height));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading brick layout: " + e.getMessage());
        }
        try (Scanner sc = new Scanner(index)) {
            for (int i = 0; i < bricks.size(); i++) {
                int btype = sc.nextInt();
                if (btype > 0)
                    bricks.get(i).setBrickType(btype);
                else
                    bricks.get(i).setDestroyed(true);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error loading brick layout: " + e.getMessage());
        }
    }   
}
