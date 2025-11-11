package com.controller;

import java.io.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.DropShadow;  
import javafx.scene.paint.Color;    
import javafx.util.Duration;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;  

public class ScoreboardController {
    
    @FXML
    private Canvas scoreboardCanvas;

    @FXML
    private VBox scoreList;

    private final File scoreFile = new File("src/main/resources/layout/Scoreboard.txt");

    private GraphicsContext gc;

    private Renderer renderer;

    private static ScoreboardController instance;

    public static ScoreboardController getInstance() {
        if (instance == null) {
            instance = new ScoreboardController();
        }

        return instance;
    }
    private List<Pair<String, Integer>> leaderBoard = new ArrayList<>();

    public List<Pair<String, Integer>> getLeaderBoard() {
        return leaderBoard;
    }

    public void addScore(String name, int score) {
        leaderBoard.add(new Pair<>(name, score));
        leaderBoard.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        if (leaderBoard.size() > 5) {
            leaderBoard = leaderBoard.subList(0, 5);
        }
    }

    @FXML
    private Button backButton;

    @FXML
    private Button resetButton;

    @FXML
    public void initialize() {
        gc = scoreboardCanvas.getGraphicsContext2D();
        renderer = new Renderer(gc, 1080, 720);
        renderer.renderScoreboard();
    }

    @FXML
    private void onResetClicked() {
        Sound.getInstance().playClick();
        try {
            PrintWriter writer = new PrintWriter(scoreFile);
            leaderBoard.clear();
            leaderBoard.add(new Pair<>("Kin", 99999));
            leaderBoard.add(new Pair<>("Esa", 9999));
            leaderBoard.add(new Pair<>("Mouse", 999));
            leaderBoard.add(new Pair<>("Ala", 99));
            leaderBoard.add(new Pair<>("Din", 9));
            writer.println("Kin 99999");
            writer.println("Esa 9999");
            writer.println("Mouse 999");
            writer.println("Ala 99");
            writer.println("Din 9");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        renderer.renderScoreboard();
    }

    @FXML
    private void onBackClicked() {
        Sound.getInstance().playClick();
        ScreenController.loadScreen("/fxml/menu.fxml");
    }

    @FXML
    private void onBackHover() {
        applyHoverEffect(backButton);
    }

    @FXML
    private void onBackExit() {
        removeHoverEffect(backButton);
    }

    @FXML
    private void onResetHover() {
        applyHoverEffect(resetButton);
    }

    @FXML
    private void onResetExit() {    
        removeHoverEffect(resetButton);
    }

    // Apply hover animation
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
    
    // Remove hover animation
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

    
}
