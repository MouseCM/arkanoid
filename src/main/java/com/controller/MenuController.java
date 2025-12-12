package com.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MenuController {

    @FXML
    private Canvas menuCanvas;

    @FXML
    private Button newGameButton;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button settingsButton;
    
    @FXML
    private Button exitButton;

    @FXML
    private Button scoreBoardButton;

    private static MenuController instance;

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }
    
    public GraphicsContext getGc() {
        return menuCanvas.getGraphicsContext2D();
    }

    // Initialize method
    @FXML
    public void initialize() {
        Sound.getInstance().start();
        if (!Sound.getInstance().getGameSoundsLoaded()) {
            Sound.getInstance().loadGameSounds();
            Sound.getInstance().setGameSoundsLoaded(true);
        }
       // Renderer.getInstance().renderUsername();
    }
    
    // Hover effect for NewGame button
    @FXML
    private void onNewGameHover() {
        applyHoverEffect(newGameButton);
    }
    
    @FXML
    private void onNewGameExit() {
        removeHoverEffect(newGameButton);
    }
    
    // Hover effect for Exit button
    @FXML
    private void onExitHover() {
        applyHoverEffect(exitButton);
    }
    
    @FXML
    private void onExitExit() {
        removeHoverEffect(exitButton);
    }
    
    // Hover effect for Continue button
    @FXML
    private void onContinueHover() {
        applyHoverEffect(continueButton);
    }
    
    @FXML
    private void onContinueExit() {
        removeHoverEffect(continueButton);
    }
    
    // Hover effect for Theme button
    @FXML
    private void onSettingsHover() {
        applyHoverEffect(settingsButton);
    }
    
    @FXML
    private void onSettingsExit() {
        removeHoverEffect(settingsButton);
    }
    
    public void applyHoverEffect(Button button) {
        // Scale animation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        
        // Apply glow effect
        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(46, 213, 255, 0.8));
        glow.setRadius(20);
        glow.setSpread(0.5);
        button.setEffect(glow);
        
        // Change background color
        button.setStyle("-fx-background-image: url('file:assets/iceburg/ClickButton.png'); -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");
        
        scaleTransition.play();
    }
    
    public void removeHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        
        // Remove effect
        button.setEffect(null);
        
        // Restore original background
        button.setStyle("-fx-background-image: url('file:assets/iceburg/NormalButton.png'); -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");
        
        scaleTransition.play();
    }
    
    // Button click handlers
    @FXML
    private void onNewGameClicked() {
        Sound.getInstance().playClick();
        GameController.setCurLevel(1);
        try(FileWriter writer = new FileWriter("src/main/resources/layout/level.txt")) {
            writer.write("1");
        }
        catch(IOException e) {
            System.err.println("cant found file");
        }
        if (GameController.getInstance().getPlayerName().equals("Player")) {
            ScreenController.loadScreen("/fxml/nameInput.fxml");
            return;
        }
        ScreenController.loadScreen("/fxml/game.fxml");
    }
    
    @FXML
    private void onContinueClicked() {
        Sound.getInstance().playClick();
        File file = new File("src/main/resources/layout/level.txt");

        try(Scanner sc = new Scanner(file)) {
            GameController.getInstance().setCurLevel(sc.nextInt());
        }
        catch(Exception e) {
            System.err.println("cant found file");
        }

        ScreenController.loadScreen("/fxml/game.fxml");
    }
    
    @FXML
    private void onSettingsClicked() {
        Sound.getInstance().playClick();
        ScreenController.loadScreen("/fxml/settings.fxml");

    }

    @FXML
    private void onExitClicked() {
        Sound.getInstance().playClick();
        System.exit(0);
    }

    @FXML
    private void onScoreboardClicked() {
        Sound.getInstance().playClick();
        ScreenController.loadScreen("/fxml/scoreboard.fxml");
    }

    @FXML
    private void onScoreboardHover() {
        applyHoverEffect(scoreBoardButton);
    }

    @FXML
    private void onScoreboardExit() {
        removeHoverEffect(scoreBoardButton);
    }

}