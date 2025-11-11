package com.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.text.Font;

public class ScoreboardController {

    @FXML
    private Canvas scoreboardCanvas;

    @FXML
    private VBox scoreList;

    private  File scoreFile = new File("assets/Scoreboard.txt");

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

    // 1. Cập nhật điểm hiện có (nếu có)
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

    // 2. Thêm điểm mới (nếu tên chưa tồn tại)
    if (!found) {
        leaderBoard.add(new Pair<>(name, score));
    }

    // 3. Sắp xếp lại Leaderboard (điểm cao nhất lên đầu)
    leaderBoard.sort((a, b) -> b.getValue().compareTo(a.getValue()));

    // 4. SỬA LỖI QUAN TRỌNG: Giới hạn danh sách an toàn về 5 phần tử
    if (leaderBoard.size() > 5) {
        // Tạo một ArrayList mới, lấy 5 phần tử đầu tiên từ danh sách đã sắp xếp
        leaderBoard = new ArrayList<>(leaderBoard.subList(0, 5)); 
    }

    // 5. Lưu Leaderboard vào File
    try {
        // Sử dụng try-with-resources để tự động đóng PrintWriter
        try (PrintWriter writer = new PrintWriter(scoreFile)) { 
            for (Pair<String, Integer> entry : leaderBoard) {
                // Đảm bảo có khoảng trắng
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
    // 1. Phát âm thanh
    Sound.getInstance().playClick();

    // 2. Xóa dữ liệu file Scoreboard.txt (đảm bảo file sạch)
    try (PrintWriter fileWriter = new PrintWriter(scoreFile)) {
        // Ghi nội dung rỗng để xóa sạch
        fileWriter.write("");
        System.out.println("✅ Đã xóa sạch dữ liệu trong file Scoreboard.");
    } catch (FileNotFoundException e) {
        System.err.println("⚠ Lỗi: Không tìm thấy file Scoreboard để xóa.");
        e.printStackTrace();
    }
    
    // 3. Khôi phục LeaderBoard về trạng thái mặc định trong bộ nhớ
    // Dữ liệu mẫu đã được định dạng đúng (có khoảng trắng)
    leaderBoard.clear();
    leaderBoard.add(new Pair<>("Kin", 99999));
    leaderBoard.add(new Pair<>("Esa", 9999));
    leaderBoard.add(new Pair<>("Mouse", 999));
    leaderBoard.add(new Pair<>("Ala", 99));
    leaderBoard.add(new Pair<>("Din", 9));

    // 4. Ghi lại dữ liệu mặc định đã khôi phục vào file
    try (PrintWriter fileWriter = new PrintWriter(scoreFile)) {
        for (Pair<String, Integer> entry : leaderBoard) {
            // Ghi với khoảng trắng phân tách để Scanner đọc đúng
            fileWriter.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("✅ Đã khôi phục LeaderBoard về mặc định và ghi vào file.");
    } catch (FileNotFoundException e) {
        System.err.println("⚠ Lỗi khi ghi lại dữ liệu mặc định vào Scoreboard.");
        e.printStackTrace();
    }

    // 5. Yêu cầu Renderer vẽ lại bảng điểm mới
    // (Giả định renderer.renderScoreboard() sử dụng dữ liệu từ file hoặc từ leaderBoard)
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
        button.setStyle(
                "-fx-background-image: url('file:assets/iceburg/ClickButton.png'); -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");

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
        button.setStyle(
                "-fx-background-image: url('file:assets/iceburg/NormalButton.png'); -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");

        scaleTransition.play();
    }

}
