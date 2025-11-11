package com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
public class NameInputController {

    @FXML
    private TextField usernameField;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onSaveClicked(ActionEvent event) {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            usernameField.setPromptText("Please enter a name!");
            return;
        }

        GameController.getInstance().setPlayerName(username);

         ScreenController.loadScreen("/fxml/menu.fxml");
    }
}   
