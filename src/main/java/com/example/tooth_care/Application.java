package com.example.tooth_care;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @FXML
    private Button loginBtn;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();

        // Access the controller and set the action for loginBtn
        HomeController controller = fxmlLoader.getController();
        controller.initLoginButton();

        stage.setTitle("ToothCare Nugegoda, Login here!");
        stage.setScene(new Scene(root, 350, 250));
        stage.show();
    }



}
