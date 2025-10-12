package com.controller;

import java.io.IOException;

import com.arkanoid.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ScreenController {
    private static Scene currentScene;
    
    public static void loadScreen(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlFile));
            System.out.println("finsihed");
            Parent root = loader.load();
            currentScene = new Scene(root);
            currentScene.setCursor(Cursor.NONE);
            App.getPrimaryStage().setScene(currentScene);

            System.out.println("Screen loaded: " + fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }
}