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
