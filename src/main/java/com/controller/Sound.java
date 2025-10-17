package com.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;


public class Sound {
    private static Sound instance;
    private final BlockingQueue<Runnable> soundTasks = new LinkedBlockingQueue<>();
    private volatile boolean running = true;
    private Thread soundThread;
    
    // Menu sound
    private AudioClip clickSound;


    // Game sound
    private AudioClip paddleSound;
    private AudioClip wallSound;
    private AudioClip brickSound;
    private AudioClip gameOverSound;
    private AudioClip gameWonSound;
    private AudioClip deathSound;


    public static Sound getInstance() {
        if (instance == null) {
            synchronized (Sound.class) {
                if (instance == null) {
                    instance = new Sound();
                }
            }
        }

        return instance;
    }
    
    public void start() {        
        soundThread = new Thread(() -> {
            while (running) {
                try {
                    // Wait for sound task
                    Runnable task = soundTasks.poll(100, TimeUnit.MILLISECONDS);
                    
                    if (task != null) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "SoundThread");
        
        soundThread.setDaemon(true);
        soundThread.start();
    }
    
    public void loadGameSounds() {
        try {
            paddleSound = new AudioClip(getClass().getResource("/sound/paddle.wav").toString());
            wallSound = new AudioClip(getClass().getResource("/sound/brick.wav").toString());
            brickSound = new AudioClip(getClass().getResource("/sound/brick.wav").toString());
            gameOverSound = new AudioClip(getClass().getResource("/sound/gameOver.wav").toString());
            gameWonSound = new AudioClip(getClass().getResource("/sound/gameWon.wav").toString());
            deathSound = new AudioClip(getClass().getResource("/sound/death.wav").toString());
        } catch (Exception e) {
            System.err.println("Failed to load sounds: " + e.getMessage());
        }
    }

    

    public void loadMenuSounds() {
        clickSound = new AudioClip(getClass().getResource("/sound/click.wav").toString());
    }
    
    // Direct method calls - just queue the task
    public void playPaddleHit() {
        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (paddleSound != null) {
                    paddleSound.play();
                } 
            });
        });
    }
    
    public void playWallHit() {
        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (wallSound != null) {
                    wallSound.play();
                }
            });
        });
    }
    
    public void playBrickHit() {
        // brickSound.play();

        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (brickSound != null) {
                    brickSound.play();
                } 
            });
        });
    }
    
    public void playGameOver() {
        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (gameOverSound != null) {
                    gameOverSound.play();
                }
            });
        });
    }

    public void playGameWon() {
        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (gameWonSound != null) {
                    gameWonSound.play();
                }
            });
        });
    }

    public void playDeath() {
        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (deathSound != null) {
                    deathSound.play();
                }
            });
        });
    }

    public void playClick() {
        soundTasks.offer(() -> {
            Platform.runLater(() -> {
                if (clickSound != null) {
                    clickSound.play();
                } 
            });
        });
    }
    
    public void stop() {
        running = false;
        if (soundThread != null) {
            soundThread.interrupt();
        }
    }
}
    
