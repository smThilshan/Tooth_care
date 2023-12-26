package com.example.tooth_care;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Toothcare Nugegoda ");
    }
    @FXML
    private Button loginBtn;

    @FXML
    private Button appointmentBtn;

    @FXML
    private Button patientBtn;

    @FXML
    private Button createAppointBtn;

    @FXML
    private Button timeSlotBtn;

    @FXML
    void handlePatient(ActionEvent event) {
        loadFXML("patients.fxml");

    }

    @FXML
    void handleCreateAppointment(ActionEvent event) {
        loadFXML("new-appointment.fxml");
    }

    @FXML
    void handleTimeSlot(ActionEvent event) {
        loadFXML("time-slot.fxml");
    }

    public void initialize() {
    }

    public void initLoginButton() {
        loginBtn.setOnAction(this::handleButtonClick);
    }

    private void handleButtonClick(ActionEvent actionEvent) {
        if (validateCredentials()) {
            loadHomeScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Entered");
            alert.setContentText("Please type Username and Password correctly");
            alert.showAndWait();
        }
    }
    private boolean validateCredentials() {
        String enteredUsername = usernameTxt.getText();
        String enteredPassword = passwordTxt.getText();
        return enteredUsername.equals("thilshan") && enteredPassword.equals("123456");
    }


    private void loadHomeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameTxt.getScene().getWindow();
            stage.setTitle("ToothCare Menu Nugegoda");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadFXML(String fxmlFileName) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appointments(ActionEvent actionEvent) {
        loadFXML("appointment.fxml");
    }
}