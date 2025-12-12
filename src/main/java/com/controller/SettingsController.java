package com.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;
public class SettingsController{

    @FXML private Slider masterVolumeSlider;
    @FXML private Slider musicVolumeSlider;
    @FXML private Slider sfxVolumeSlider;

    @FXML private Label masterVolumeLabel;
    @FXML private Label musicVolumeLabel;
    @FXML private Label sfxVolumeLabel;

    @FXML private Button resetButton;
    @FXML private Button backButton;
    @FXML private Button setUsernameButton;
    
    private static final double DEFAULT_MASTER = 70;
    private static final double DEFAULT_MUSIC = 50;
    private static final double DEFAULT_SFX = 60;

    @FXML
    public void initialize() {
        masterVolumeSlider.setValue(Sound.getInstance().getMasterVolume() * 100);
        musicVolumeSlider.setValue(Sound.getInstance().getMusicVolume() * 100);
        sfxVolumeSlider.setValue(Sound.getInstance().getSfxVolume() * 100);

        bindSlider(masterVolumeSlider, masterVolumeLabel);
        bindSlider(musicVolumeSlider, musicVolumeLabel);
        bindSlider(sfxVolumeSlider, sfxVolumeLabel);
        
        masterVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Sound.getInstance().setMasterVolume(newVal.doubleValue() / 100.0);
            Sound.getInstance().ChangeMusicVolume(Sound.getInstance().getMasterVolume() * Sound.getInstance().getMusicVolume());
            Sound.getInstance().ChangeSFXVolume(Sound.getInstance().getMasterVolume() * Sound.getInstance().getSfxVolume());
            System.out.println("Master Volume: " + Sound.getInstance().getMasterVolume());
            System.out.println("Music Volume: " + Sound.getInstance().getMusicVolume());
            System.out.println("SFX Volume: " + Sound.getInstance().getSfxVolume());
        });

        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Sound.getInstance().setMusicVolume(newVal.doubleValue() / 100.0);
            Sound.getInstance().ChangeMusicVolume(Sound.getInstance().getMasterVolume() * Sound.getInstance().getMusicVolume());
        });

        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Sound.getInstance().setSfxVolume(newVal.doubleValue() / 100.0);
            Sound.getInstance().ChangeSFXVolume(Sound.getInstance().getMasterVolume() * Sound.getInstance().getSfxVolume());
        });

    }

    private void bindSlider(Slider slider, Label label) {
        label.setText((int) slider.getValue() + "%");
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            label.setText(String.format("%d%%", newVal.intValue()));
        });
    }

    @FXML
    private void onResetClicked() {
        masterVolumeSlider.setValue(DEFAULT_MASTER);
        musicVolumeSlider.setValue(DEFAULT_MUSIC);
        sfxVolumeSlider.setValue(DEFAULT_SFX);
    }

    @FXML
    private void onBackClicked() {
        Sound.getInstance().playClick();
        ScreenController.loadScreen("/fxml/menu.fxml");
    }

    @FXML
    private void onSetUsernameClicked(){
        Sound.getInstance().playClick();
        ScreenController.loadScreen("/fxml/nameInput.fxml");
    }

    @FXML 
    private void onExitHover() {
        applyHoverEffect(backButton);
    }

    @FXML
    private void onExitExit() {
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

    @FXML
    private void onSetUsernameHover(){
        applyHoverEffect(setUsernameButton);
    }

    @FXML
    private void onSetUsernameExit(){
        removeHoverEffect(setUsernameButton);
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
}

