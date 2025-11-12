package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.abstracts.Brick;
import com.object.Ball;
import com.object.BallParticle;
import com.object.BrickParticle;
import com.object.Bullet;
import com.object.Effect;
import com.object.ExplosionBrick;
import com.object.NormalBrick;
import com.object.Paddle;
import com.object.PowerUp;
import com.object.StrongBrick;
import com.object.UnbreakableBrick;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameController {
    private static GameController instance;

    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Renderer renderer;
    private Sound sound;
    private String playerName = "Player";
    private static final int WIDTH = 720;
    private static final int HEIGHT = 720;
    private static int curLevels = 1;
    private static final int finalLevels = 11;

    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean won = false;
    private boolean gamePaused = false;
    private AnimationTimer gameLoop;
    private int score = 0;
    private static int lives = 3;
    private List<Ball> balls;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private Effect effect;
    private List<BrickParticle> brickParticles;
    private List<BallParticle> ballParticles;
    private List<Bullet> bullets;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public static int getScore() {
        return getInstance().score;
    }

    @FXML
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        renderer = new Renderer(gc, WIDTH, HEIGHT);
        sound = Sound.getInstance();

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        gameCanvas.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));

        balls = new ArrayList<>();
        balls.add(new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 8, 8, 120));
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 30, 1, 0, 100, 15, 10);
        effect = new Effect();
        brickParticles = new ArrayList<>();
        ballParticles = new ArrayList<>();
        bullets = new ArrayList<>();
        initBricks();

        startGameLoop();

        gameCanvas.requestFocus();
    }

    private void handleKeyPressed(KeyCode key) {
        if (key == KeyCode.A) {
            aPressed = true;
        }
        if (key == KeyCode.D) {
            dPressed = true;
        }
        if (key == KeyCode.SPACE && !gameStarted) {
            gameStarted = true;
        }
        if (key == KeyCode.SPACE && gameOver) {
            gameLoop.start();
            hardResetGame();
        }
        if (key == KeyCode.SPACE && won) {
            gameLoop.start();
            nextLevel();
        }
        if (key == KeyCode.R) {
            gameLoop.start();
            hardResetGame();
        }
        if (key == KeyCode.Q) {
            gameLoop.stop();
            ScreenController.getCurrentScene().setCursor(Cursor.DEFAULT);
            ScreenController.loadScreen("/fxml/menu.fxml");
        }

        if (key == KeyCode.ESCAPE) {
            gamePaused = !gamePaused;
            if (gamePaused) {
                gameLoop.stop();
                ScreenController.getCurrentScene().setCursor(Cursor.DEFAULT);
                renderer.renderPaused();
            } else {
                gameLoop.start();
                ScreenController.getCurrentScene().setCursor(Cursor.NONE);
            }
        }
    }

    private void handleKeyReleased(KeyCode key) {
        if (key == KeyCode.A) {
            aPressed = false;
        }

        if (key == KeyCode.D) {
            dPressed = false;
        }
    }

    private void handleMouse(Scene scene) {
        scene.setOnMousePressed(event -> {

            gameStarted = true;

            if (gameOver == true) {
                gameLoop.start();
                hardResetGame();
            }

            if (won) {
                gameLoop.start();
                nextLevel();
            }
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
        if (gameOver || won) {
            return;
        }

        if (curLevels >= finalLevels) {
            gameLoop.stop();
            ScreenController.getCurrentScene().setCursor(Cursor.DEFAULT);
            ScreenController.loadScreen("/fxml/win.fxml");
        }

        // handle mouse events
        handleMouse(ScreenController.getCurrentScene());

        // handle paddle movement with mouse and keyboard
        paddle.update(scene, WIDTH, aPressed, dPressed);

        // ball follow paddle
        if (!gameStarted) {
            for (Ball ball : balls) {
                ball.followPaddle(paddle);
                ball.setAngle(ball.getAngle() + ball.getAngleRotate());
                if (ball.getAngle() >= 240) {
                    ball.setAngleRotate(-2);
                }

                if (ball.getAngle() <= 120) {
                    ball.setAngleRotate(2);
                }
            }
            return;
        }

        // handle ball event
        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball ball = balls.get(i);

            // bounce wall
            ball.bounceWall(WIDTH, ballParticles);

            if (ball.isDeath(HEIGHT)) {
                balls.remove(i);
            }

            // handle death
            if (balls.size() == 0) {
                lives--;
                if (lives <= 0) {
                    gameOver = true;
                    sound.playGameOver();
                    return;
                } else {
                    sound.playDeath();
                    resetGame();
                    return;
                }
            }

            // bounce paddle
            if (ball.willCollision(paddle)) {
                sound.playPaddleHit();
                ball.bouncePaddle(paddle, ballParticles);
            }

            // bounce brick
            for (int j = bricks.size() - 1; j >= 0; j--) {
                Brick brick = bricks.get(j);

                if (!brick.isDestroyed() && ball.willCollision(brick)) {

                    sound.playBrickHit();
                    ball.bounceBrick(brick, ballParticles);

                    if (brick instanceof ExplosionBrick) {
                        // explode nearby bricks
                        brick.takeHit(bricks);
                        score += brick.getScoreValue();
                    }

                    if (brick.takeHit()) {
                        score += brick.getScoreValue();
                    }
                }

                // add PowerUp and break effect
                if (brick.hasPowerUp() == true) {
                    brick.addPowerUp(powerUps, brickParticles);
                }

                if (brick.isDestroyed()) {
                    bricks.remove(j);
                }
            }

        }

        // update ball
        for (Ball ball : balls) {
            ball.update();
        }

        for (int i = powerUps.size() - 1; i >= 0; i--) {
            if (powerUps.get(i).isCollision(paddle)) {
                powerUps.get(i).active(lives, balls, effect, paddle);
            }

            powerUps.get(i).update();

            if (powerUps.get(i).isDead() || powerUps.get(i).getIsCollected()) {
                powerUps.remove(i);
            }
        }

        effect.deActive(balls, paddle, bullets);

        // handle bullet
        for (int i = bullets.size() - 1; i >= 0; i--) {
            for (int j = bricks.size() - 1; j >= 0; j--) {
                Brick brick = bricks.get(j);
                if (bullets.get(i).willCollision(brick)) {
                    if (brick instanceof ExplosionBrick) {
                        // explode nearby bricks
                        brick.takeHit(bricks);
                        score += brick.getScoreValue();
                    } else {
                        brick.takeHit();
                    }

                    if (brick.hasPowerUp() == true) {
                        brick.addPowerUp(powerUps, brickParticles);
                    }

                    if (brick.isDestroyed()) {
                        bricks.remove(j);
                    }

                    bullets.remove(i);
                }
            }

            bullets.get(i).update();

            if (bullets.get(i).getY() <= 0) {
                bullets.remove(i);
            }
        }

        for (int i = ballParticles.size() - 1; i >= 0; i--) {
            ballParticles.get(i).update();

            if (ballParticles.get(i).getOpacity() <= 0) {
                ballParticles.remove(i);
            }
        }

        for (int i = brickParticles.size() - 1; i >= 0; i--) {
            brickParticles.get(i).update();

            if (brickParticles.get(i).getOpacity() <= 0) {
                brickParticles.remove(i);
            }
        }

        // check win
        boolean pass = true;
        for (Brick brick : bricks) {
            if (!(brick instanceof UnbreakableBrick)) {
                pass = false;
                break;
            }
        }

        // handle win
        if (pass == true) {
            won = true;
            sound.playGameWon();

            try (FileWriter writer = new FileWriter("src/main/resources/layout/level.txt")) {
                writer.write(Integer.toString(curLevels + 1));
            } catch (IOException e) {
                System.err.println("cant found file");
            }
        }
    }

    private void render() {
        renderer.renderGame(balls, paddle, bricks,
                powerUps, effect, gameOver, gameStarted,
                gameLoop, won, score, lives, brickParticles,
                ballParticles, bullets, curLevels);
    }

    private void resetGame() {
        powerUps.clear();
        balls.clear();
        balls.add(new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 8, 8, 165));
        effect = new Effect();
        brickParticles.clear();
        ballParticles.clear();
        bullets.clear();
        gameStarted = false;
    }

    private void hardResetGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        gameStarted = false;
        paddle.setX(WIDTH / 2 - (paddle.getWidth() / 2));
        balls.clear();
        balls.add(new Ball(WIDTH / 2, HEIGHT - 30, 1, -1, 8, 8, 165));
        effect = new Effect();
        brickParticles.clear();
        bullets.clear();
        ballParticles.clear();
        initBricks();
    }

    private void nextLevel() {
        curLevels++;
        won = false;
        int preScore = score;
        hardResetGame();
        score = preScore;
    }

    private void initBricks() {
        String path = "src/main/resources/layout/normal/";
        path += Integer.toString(curLevels) + ".txt";
        File levels = new File(path);

        try (Scanner sc = new Scanner(levels)) {
            bricks = new ArrayList<>();
            powerUps = new ArrayList<>();
            String type;
            int n;
            int x;
            int y;
            int hitPoints;

            n = sc.nextInt();

            for (int i = 0; i < n; i++) {
                x = sc.nextInt();
                y = sc.nextInt();
                hitPoints = sc.nextInt();
                type = sc.next();
                type.strip();

                if (type.equals("normal")) {
                    bricks.add(new NormalBrick(x, y));
                } else if (type.equals("strong")) {
                    bricks.add(new StrongBrick(x, y, hitPoints));
                } else if (type.equals("unbreakable")) {
                    bricks.add(new UnbreakableBrick(x, y));
                } else if (type.equals("explosion")) {
                    bricks.add(new ExplosionBrick(x, y));
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading brick layout: " + e.getMessage());
        }
    }

    public static void setCurLevel(int level) {
        curLevels = level;
    }

    public static void setLive(int live) {
        lives = live;
    }
}
