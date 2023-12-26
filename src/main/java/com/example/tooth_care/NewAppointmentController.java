package com.example.tooth_care;

import com.example.tooth_care.Models.Appointment;
import com.example.tooth_care.Models.AppointmentType;
import com.example.tooth_care.Models.DataManager;
import com.example.tooth_care.Models.Patient;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewAppointmentController {
    @FXML
    public ComboBox patientComboBox;
    @FXML
    private CheckBox cleaningBox;

    @FXML
    private CheckBox whiteningBox;

    @FXML
    private CheckBox fillingBox;

    @FXML
    private CheckBox nerveFilingBox;

    @FXML
    private CheckBox rootCanalTherapyBox;

    @FXML
    private ListView<String> selectedTypesListView;
    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;
    @FXML
    private TextField totalCostField;
    @FXML
    private TextField idField;
    private ArrayList<Integer> typeIds = new ArrayList<>();
    private boolean editMode = false;


    public void initialize() {
        idField.setEditable(false);
        idField.setText(String.valueOf(DataManager.getInstance().getAppointments().size() + 1));
        typeIds.clear();
        totalCostField.setText("500");
        ObservableList<String> selectedTypes = selectedTypesListView.getItems();
        selectedTypes.add("Registration Fee     500.0");
        cleaningBox.setOnAction(event -> handleCheckBoxAction(cleaningBox));
        whiteningBox.setOnAction(event -> handleCheckBoxAction(whiteningBox));
        fillingBox.setOnAction(event -> handleCheckBoxAction(fillingBox));
        nerveFilingBox.setOnAction(event -> handleCheckBoxAction(nerveFilingBox));
        rootCanalTherapyBox.setOnAction(event -> handleCheckBoxAction(rootCanalTherapyBox));
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.getDayOfWeek() == DayOfWeek.TUESDAY ||
                        date.getDayOfWeek() == DayOfWeek.THURSDAY ||
                        date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        });
        ObservableList<String> patientNames = Patient.getNameList();
        patientComboBox.setItems(patientNames);
        timeComboBox.setDisable(true);
        datePicker.setOnAction(event -> updateAvailableTimes());

    }

    private void handleCheckBoxAction(CheckBox checkBox) {
        ArrayList<AppointmentType> types = DataManager.getInstance().getAppointmentTypes();
        AppointmentType selectedType = null;
        for (AppointmentType type : types)
        {
            if (type.getName().contains(checkBox.getText()))
            {
                selectedType = type;
            }
        }

        ObservableList<String> selectedTypes = selectedTypesListView.getItems();
        if (checkBox.isSelected()) {
            setId(selectedType.getName());
            selectedTypes.add(checkBox.getText() + "     " + selectedType.getCost());
            long total = Long.parseLong(totalCostField.getText());
            total = (long)(total + selectedType.getCost());
            totalCostField.setText(total+"");
        } else {
            removeId(selectedType.getName());
            selectedTypes.remove(checkBox.getText() + "     " + selectedType.getCost());
            long total = Long.parseLong(totalCostField.getText());
            total =(long) (total - selectedType.getCost());
            totalCostField.setText(total+"");
        }

    }

    private void updateAvailableTimes() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();
            List<String> availableTimes = getAvailableTimesForDay(dayOfWeek);
            if (!availableTimes.isEmpty()) {
                timeComboBox.getItems().setAll(availableTimes);
                timeComboBox.setDisable(false);
            } else {
                timeComboBox.getItems().clear();
                timeComboBox.setDisable(true);
            }
        }
    }

    private List<String> getAvailableTimesForDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
            case WEDNESDAY:
                return Arrays.asList("6 PM", "7 PM", "8 PM", "9 PM");
            case SATURDAY:
            case SUNDAY:
                return Arrays.asList("3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM");
            default:
                return Collections.emptyList();
        }
    }

    public void handleCreateAppointment(ActionEvent actionEvent) {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Error");
        alert1.setContentText("Please select a patient.");
        if(patientComboBox.getValue() == null)
        {
            alert1.showAndWait();
            return;
        }
        if (editMode)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registration Fee");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to edit the appointment ?");

            // Add OK and Cancel buttons to the dialog
            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("No");
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == okButton) {

                    Stage currentStage = (Stage) cleaningBox.getScene().getWindow();
                    currentStage.close();
                    try {
                        ArrayList appointments = DataManager.getInstance().getAppointments();
                        Appointment oldAppointment = Appointment.getAppointmentById(Integer.parseInt(idField.getText()));
                        int id = Integer.parseInt(idField.getText());
                        String name = patientComboBox.getValue().toString();
                        LocalDate localDate = datePicker.getValue();
                        LocalTime localTime = getLocalTime(timeComboBox.getValue());
                        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
                        double total = Double.parseDouble(totalCostField.getText());

                        Appointment appointment =new Appointment(
                                id,
                                name,
                                dateTime,
                                Patient.getPatient(name).getId(),
                                typeIds,total
                        );
                        appointments.set(appointments.indexOf(oldAppointment),appointment);
                        DataManager.getInstance().setAppointments(appointments);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"));
                        Parent root = loader.load();
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(root));
                        newStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                }

            });
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registration Fee");
            alert.setHeaderText(null);
            alert.setContentText("Please press OK if you get paid 500 for the registration fee.");

            // Add OK and Cancel buttons to the dialog
            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("No");
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == okButton) {
                    Stage currentStage = (Stage) cleaningBox.getScene().getWindow();
                    currentStage.close();
                    try {
                        ArrayList appointments = DataManager.getInstance().getAppointments();
                        int id = Integer.parseInt(idField.getText());
                        String name = patientComboBox.getValue().toString();
                        LocalDate localDate = datePicker.getValue();
                        LocalTime localTime = getLocalTime(timeComboBox.getValue());
                        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
                        double total = Double.parseDouble(totalCostField.getText());

                        appointments.add(new Appointment(
                                id,
                                name,
                                dateTime,
                                Patient.getPatient(name).getId(),
                                typeIds,total - 500
                        ));
                        DataManager.getInstance().setAppointments(appointments);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"));
                        Parent root = loader.load();
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(root));
                        newStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                }
            });
        }

    }

    private void setId(String name) {
        typeIds.add(AppointmentType.getTypesByName(name).getId());
    }
    private void removeId (String name) {

        typeIds.remove(Integer.valueOf(AppointmentType.getTypesByName(name).getId()));

    }

    private LocalTime getLocalTime (String time) {

        if (time != null && !time.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h a");
            LocalTime localTime = LocalTime.parse(time, formatter);
            return localTime;
        } else {
            return null;
        }
    }

    public void editData(Appointment appointment) {
        idField.setEditable(false);
        editMode = true;
        ObservableList<String> patientNames = Patient.getNameList();
        if (appointment != null) {
            LocalDateTime dateTime = appointment.getDateTime();
            LocalDate localDate = dateTime.toLocalDate();
            LocalTime localTime = dateTime.toLocalTime();
            String patientName = appointment.getPatientName();
            if (patientName != null && !patientName.isEmpty()) {
                int selectedIndex = -1;
                for (int i = 0; i < patientNames.size(); i++) {
                    if (patientNames.get(i).equals(patientName)) {
                        selectedIndex = i;
                        break;
                    }
                }
                if (selectedIndex >= 0) {
                    patientComboBox.getSelectionModel().select(selectedIndex);
                }
            }
            datePicker.setValue(localDate);
            updateAvailableTimes();
            idField.setText(appointment.getId()+ "");
            totalCostField.setText("0");
            ObservableList<String> selectedTypes = selectedTypesListView.getItems();
            selectedTypes.remove(0);
            ArrayList<Integer> typeIds = appointment.getTypeIds();
            String formattedTime = formatLocalTime(localTime);
            timeComboBox.setValue(formattedTime);
                if (typeIds != null) {
                    for (Integer typeId : typeIds) {
                        switch (typeId) {
                            case 1:
                                cleaningBox.setSelected(true);
                                handleCheckBoxAction(cleaningBox);
                                break;
                            case 2:
                                whiteningBox.setSelected(true);
                                handleCheckBoxAction(whiteningBox);
                                break;
                            case 3:
                                fillingBox.setSelected(true);
                                handleCheckBoxAction(fillingBox);
                                break;
                            case 4:
                                nerveFilingBox.setSelected(true);
                                handleCheckBoxAction(nerveFilingBox);
                                break;
                            case 5:
                                rootCanalTherapyBox.setSelected(true);
                                handleCheckBoxAction(rootCanalTherapyBox);
                                break;
                            default:
                                break;
                        }
                    }
                }
        }
    }
    private String formatLocalTime(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h a");
        return localTime.format(formatter);
    }
}
