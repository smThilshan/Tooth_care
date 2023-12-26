package com.example.tooth_care;

import com.example.tooth_care.Models.DataManager;
import com.example.tooth_care.Models.Patient;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class PatientController {
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField ageField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField idField;
    private boolean editMode = false;
    @FXML
    public Button newPatientButton;
    @FXML
    private TableView<Patient> patientsTable;

    @FXML
    private TableColumn<Patient, Integer> idColumn;

    @FXML
    private TableColumn<Patient, String> firstNameColumn;

    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> phoneNumberColumn;
    @FXML
    private TableColumn<Patient, String> ageColumn;
    @FXML
    private TableColumn<Patient, String> addressColumn;
    @FXML
    private TableColumn<Patient,String> emailColumn;
    private ObservableList<Patient> patientsList = Patient.getPatientsList();
    private PatientController patientController;
    private SimpleObjectProperty<ObservableList<Patient>> patientsProperty = new SimpleObjectProperty<>();
    Stage primaryStage;

    @FXML
    private void initialize() {
        if (ageField !=  null)
        {
            idField.setEditable(false);
            idField.setText(String.valueOf(DataManager.getInstance().getPatients().size() + 1));
            TextFormatter<Integer> formatter1 = new TextFormatter<>(new IntegerStringConverter(), null,
                    c -> {
                        if (c.getControlNewText().matches("\\d*")) {
                            return c;
                        }
                        return null;
                    });
            ageField.setTextFormatter(formatter1);
        }
        else
        {
            patientsList.addListener((ListChangeListener<? super Patient>) change -> {
                while (change.next()) {
                    if (change.wasUpdated() || change.wasAdded() || change.wasRemoved()) {
                    }
                }
            });
            patientsTable.itemsProperty().bind(patientsProperty);
            // Set up columns to display patient information
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            setPatientsList(Patient.getPatientsList());
        }

    }

    public PatientController() {
    }

    public void handleNewPatientButtonAction(ActionEvent actionEvent) throws IOException {
        Node source = (Node) actionEvent.getSource();
        DataManager.getInstance().setPrimaryStage((Stage) source.getScene().getWindow())  ;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-patient.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEditPatientButtonAction(ActionEvent actionEvent) {
        // Logic to handle editing a selected patient from the TableView
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            openEditScreen(selectedPatient);
            System.out.println("Editing patient: " + selectedPatient);
        } else {
            System.out.println("Please select a patient to edit.");
        }
    }

    private void openEditScreen(Patient selectedPatient) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new-patient.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        initData(selectedPatient);
        Stage editStage = new Stage();
        editStage.setTitle("Edit Patient");
        editStage.setScene(new Scene(root));
        editStage.show();
    }

    public void handleDeletePatientButtonAction(ActionEvent actionEvent) {
    }

    public void setPatientsList(ObservableList<Patient> patientsList) {
        patientsProperty.set(FXCollections.observableArrayList(patientsList));
    }


    // Assuming you have a reference to the PatientController
    public void setPatientController(PatientController controller) {
        this.patientController = controller;
    }

    // Method where you update the patient list
    public void updatePatientList() {
        ObservableList<Patient> updatedList = Patient.getPatientsList();
        patientController.setPatientsList(updatedList);
    }

    public void initData(Patient patient){
        editMode = true;
        idField.setEditable(false);
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        phoneField.setText(patient.getPhoneNumber());
        ageField.setText(String.valueOf(patient.getAge()));
        addressField.setText(patient.getAddress());
        emailField.setText(patient.getEmail());
        idField.setText(String.valueOf(patient.getId()));

    }
    public void NewPatientButtonAction(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(idField.getText());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneField.getText();
        int age = Integer.parseInt(ageField.getText());
        String address = addressField.getText();
        String email = emailField.getText();
        ArrayList<Patient> patients = DataManager.getInstance().getPatients();
        if (editMode)
        {
            Patient oldPatient = Patient.findPatientByID(id);
            Patient patient = new Patient(id,firstName,lastName,phoneNumber,age,address,email);
            patients.set(patients.indexOf(oldPatient), patient);
        }
        else
        {
            patients.add(new Patient(id,firstName,lastName,phoneNumber,age,address,email));
        }
        DataManager.getInstance().setPatients(patients);
        closeCurrentStage(actionEvent);
        triggerRefresh();

    }
    private void closeCurrentStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void triggerRefresh() throws IOException {
    DataManager.getInstance().getPrimaryStage().close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patients.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
}
