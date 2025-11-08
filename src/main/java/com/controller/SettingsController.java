package com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;

public class SettingsController{

    @FXML private Slider masterVolumeSlider;
    @FXML private Slider musicVolumeSlider;
    @FXML private Slider sfxVolumeSlider;

    @FXML private Label masterVolumeLabel;
    @FXML private Label musicVolumeLabel;
    @FXML private Label sfxVolumeLabel;

    @FXML private Button resetButton;
    @FXML private Button backButton;
    // Giá trị mặc định
    private static final double DEFAULT_MASTER = 70;
    private static final double DEFAULT_MUSIC = 50;
    private static final double DEFAULT_SFX = 60;

    private static MenuController instance = new MenuController();
    @FXML
    public void initialize() {
        bindSlider(masterVolumeSlider, masterVolumeLabel);
        bindSlider(musicVolumeSlider, musicVolumeLabel);
        bindSlider(sfxVolumeSlider, sfxVolumeLabel);
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
    private void onExitHover() {
        instance.applyHoverEffect(backButton );
    }

    @FXML
    private void onExitExit() {
        instance.removeHoverEffect(backButton);
    }

    @FXML
    private void onResetHover() {
        instance.applyHoverEffect(resetButton);
    }

    @FXML
    private void onResetExit() {
        instance.removeHoverEffect(resetButton);
    }

}
