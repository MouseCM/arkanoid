package com.arkanoid;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Ball ball = new Ball();
        System.out.println(ball.getDx());
    }


    public static void main(String[] args) {
        launch();
    }

}