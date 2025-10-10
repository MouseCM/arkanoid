package com.arkanoid;

import java.io.IOException;

import com.controller.ScreenController;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        primaryStage = stage;



        stage.setTitle("Arkanoid");
        ScreenController.loadScreen("/fxml/menu.fxml");
        stage.setResizable(false);

        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    


    public static void main(String[] args) {
        launch(args);
    }

}