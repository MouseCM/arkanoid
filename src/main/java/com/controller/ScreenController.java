package com.controller;

import java.io.IOException;

import com.arkanoid.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ScreenController {
    
    public static void loadScreen(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(fxmlFile));
            System.out.println("finsihed");
            Parent root = loader.load();
            Scene scene = new Scene(root);
            App.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}