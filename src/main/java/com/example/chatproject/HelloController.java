package com.example.chatproject;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    // Strings which hold css elements to easily re-use in the application

    @FXML
    private TextField loginId;
    @FXML
    private TextField pwdId;
    @FXML
    private Button loginButton;


    @FXML
    protected void onLogin() throws IOException {



            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            // Load the FXML file for the new view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the new scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            // Show the new stage and close the current stage
            newStage.show();
            currentStage.close();
        }
    }

