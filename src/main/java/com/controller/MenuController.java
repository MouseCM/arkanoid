package com.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MenuController {
    @FXML
    private Button newGameButton;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button themeButton;
    
    // Hover effect for NewGame button
    @FXML
    private void onNewGameHover() {
        applyHoverEffect(newGameButton);
    }
    
    @FXML
    private void onNewGameExit() {
        removeHoverEffect(newGameButton);
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
    private void onThemeHover() {
        applyHoverEffect(themeButton);
    }
    
    @FXML
    private void onThemeExit() {
        removeHoverEffect(themeButton);
    }
    
    // Apply hover animation
    private void applyHoverEffect(Button button) {
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
        button.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");
        
        scaleTransition.play();
    }
    
    // Remove hover animation
    private void removeHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        
        // Remove effect
        button.setEffect(null);
        
        // Restore original background
        button.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");
        
        scaleTransition.play();
    }
    
    // Button click handlers
    @FXML
    private void onNewGameClicked() {
        ScreenController.loadScreen("/fxml/game.fxml");

    }
    
    @FXML
    private void onContinueClicked() {
        

    }
    
    @FXML
    private void onThemeClicked() {
        

    }
}