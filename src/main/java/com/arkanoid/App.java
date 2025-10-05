package com.arkanoid;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        
        Scene scene = new Scene(root);

        stage.setTitle("Arkanoid");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}