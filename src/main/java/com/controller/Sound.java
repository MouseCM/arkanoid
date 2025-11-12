package com.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    private static Sound instance;
    private final BlockingQueue<Runnable> soundTasks = new LinkedBlockingQueue<>();
    private volatile boolean running = true;
    private Thread soundThread;
    
    // Menu sound
    private AudioClip clickSound;

    private volatile boolean gameSoundsLoaded = false;

    // Game sound
    private AudioClip paddleSound;
    private AudioClip wallSound;
    private AudioClip brickSound;
    private AudioClip gameOverSound;
    private AudioClip gameWonSound;
    private AudioClip deathSound;
    private Media backgroundMusic;
    private MediaPlayer mediaPlayer;

    private double masterVolume = 70 / 100.0;
    private double musicVolume = 50 / 100.0;
    private double sfxVolume = 60 / 100.0;

    public boolean getGameSoundsLoaded() {
        return gameSoundsLoaded;
    }

    public void setGameSoundsLoaded(boolean loaded) {
        this.gameSoundsLoaded = loaded;
    }
    
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
            backgroundMusic = new Media(getClass().getResource("/sound/background.mp3").toString());
            mediaPlayer = new MediaPlayer(backgroundMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(1.0 * musicVolume * masterVolume);
            mediaPlayer.play();
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

    public void ChangeMusicVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void ChangeSFXVolume(double volume) {
        if (paddleSound != null) {
            paddleSound.setVolume(volume);
        }
        if (wallSound != null) {
            wallSound.setVolume(volume);
        }
        if (brickSound != null) {
            brickSound.setVolume(volume);
        }
        if (gameOverSound != null) {
            gameOverSound.setVolume(volume);
        }
        if (gameWonSound != null) {
            gameWonSound.setVolume(volume);
        }
        if (deathSound != null) {
            deathSound.setVolume(volume);
        }
        if (clickSound != null) {
            clickSound.setVolume(volume);
        }
    }

    public double getMasterVolume() {
        return masterVolume;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setMasterVolume(double masterVolume) {
        this.masterVolume = masterVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = sfxVolume;
    }
}
