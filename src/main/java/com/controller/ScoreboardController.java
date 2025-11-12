package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

public class ScoreboardController {
    @FXML
    private Canvas scoreboardCanvas;

    @FXML
    private VBox scoreList;

    private File scoreFile = new File("assets/Scoreboard.txt");

    private GraphicsContext gc;

    private Renderer renderer;

    private boolean found = false;
    private static ScoreboardController instance;

    public static ScoreboardController getInstance() {
        if (instance == null) {
            instance = new ScoreboardController();
        }

        return instance;
    }

    public File getScoreFile() {
        return scoreFile;
    }

    private List<Pair<String, Integer>> leaderBoard = new ArrayList<>();

    public List<Pair<String, Integer>> getLeaderBoard() {
        if (leaderBoard.size() == 5)
            return leaderBoard;
        else {
            addScore("Kin", 99999);
            addScore("Esa", 9999);
            addScore("Mouse", 999);
            addScore("Ala", 99);
            addScore("Din", 9);
        }
        return leaderBoard;
    }

    public void addScore(String name, int score) {
        found = false;

        for (int i = 0; i < leaderBoard.size(); i++) {
            Pair<String, Integer> entry = leaderBoard.get(i);
            if (entry.getKey().equals(name)) {
                found = true;
                if (score > entry.getValue()) {
                    leaderBoard.set(i, new Pair<>(name, score));
                }
                break;
            }
        }

        if (!found) {
            leaderBoard.add(new Pair<>(name, score));
        }

        leaderBoard.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        if (leaderBoard.size() > 5) {

            leaderBoard = new ArrayList<>(leaderBoard.subList(0, 5));
        }

        try {
            try (PrintWriter writer = new PrintWriter(scoreFile)) {
                for (Pair<String, Integer> entry : leaderBoard) {
                    writer.println(entry.getKey() + " " + entry.getValue());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button backButton;

    @FXML
    private Button resetButton;

    @FXML
    public void initialize() {
        Font.loadFont(getClass().getResourceAsStream("/assets/fonts/Arcade.ttf"), 24);
        gc = scoreboardCanvas.getGraphicsContext2D();
        renderer = new Renderer(gc, 1080, 720);
        renderer.renderScoreboard();
    }

    @FXML
    private void onResetClicked() {
        Sound.getInstance().playClick();

        try (PrintWriter fileWriter = new PrintWriter(scoreFile)) {
            fileWriter.write("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        leaderBoard.clear();
        leaderBoard.add(new Pair<>("Kin", 99999));
        leaderBoard.add(new Pair<>("Esa", 9999));
        leaderBoard.add(new Pair<>("Mouse", 999));
        leaderBoard.add(new Pair<>("Ala", 99));
        leaderBoard.add(new Pair<>("Din", 9));

        try (PrintWriter fileWriter = new PrintWriter(scoreFile)) {
            for (Pair<String, Integer> entry : leaderBoard) {
                fileWriter.println(entry.getKey() + " " + entry.getValue());
            }
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

    public void applyHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(46, 213, 255, 0.8));
        glow.setRadius(20);
        glow.setSpread(0.5);
        button.setEffect(glow);

        button.setStyle(
                "-fx-background-image: url('file:assets/iceburg/ClickButton.png'); -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");

        scaleTransition.play();
    }

    public void removeHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        button.setEffect(null);

        button.setStyle(
                "-fx-background-image: url('file:assets/iceburg/NormalButton.png'); -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");

        scaleTransition.play();
    }

}
