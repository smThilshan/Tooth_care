package com.example.tooth_care;

import com.example.tooth_care.Models.Appointment;
import com.example.tooth_care.Models.AppointmentType;
import com.example.tooth_care.Models.DataManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentController {
    @FXML
    public TableColumn idColumn;
    @FXML
    public TextField idFilterTextField;
    @FXML
    public DatePicker dateFilterDatePicker;
    @FXML
    public Button acceptPaymentButton;
    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, String> patientNameColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Appointment, Double> chargeColumn;

    private ObservableList<Appointment> appointmentsList = Appointment.getAppointments();

    @FXML
    private TableColumn<Appointment, Appointment> actionsColumn;
    private List<Appointment> originalAppointmentsList = DataManager.getInstance().getAppointments();


    @FXML
    public void initialize() {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), null,
                c -> {
                    if (c.getControlNewText().matches("\\d*")) {
                        return c;
                    }
                    return null;
                });
        idFilterTextField.setTextFormatter(formatter);
        idFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });
        dateFilterDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        chargeColumn.setCellValueFactory(new PropertyValueFactory<>("charge"));
        appointmentsTable.setItems(appointmentsList);


    }
    @FXML
    private void handleAcceptPayment() {
        try {
            Appointment appointment = appointmentsTable.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invoice.fxml"));
            Parent root = fxmlLoader.load();

            InvoiceController invoiceController = fxmlLoader.getController();


            int id = appointment.getId();
            String patientName = appointment.getPatientName();
            LocalDateTime dateTime = appointment.getDateTime();
            String treatments = getTreatments(appointment.getTypeIds());
            double charge = appointment.getCharge();

            // Initialize the invoice view with details
            invoiceController.initializeInvoice(id, patientName, dateTime, charge,treatments);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Invoice");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTreatments(ArrayList<Integer> typeIds) {
        StringBuilder treatment = new StringBuilder();
        for (Integer typeId: typeIds)
        {
            AppointmentType type = AppointmentType.getTypeById(typeId);
            treatment.append(type.getName() + "      " + type.getCost() +"\n");
        }
        return treatment.toString();
    }

    @FXML
    public void editAppointment(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new-appointment.fxml"));
        Parent root = loader.load();
        NewAppointmentController newAppointmentController = loader.getController();
        newAppointmentController.editData(selectedAppointment);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
    private void applyFilters() {
        List<Appointment> filteredList = originalAppointmentsList.stream()
                .filter(appointment -> {
                    LocalDate localDate = appointment.getDateTime().toLocalDate();
                    return String.valueOf(appointment.getId()).contains(idFilterTextField.getText().toLowerCase())
                            && (dateFilterDatePicker.getValue() == null ||
                            localDate.equals(dateFilterDatePicker.getValue()));
                })
                .collect(Collectors.toList());

        updateTableView(filteredList);

    }

    private void updateTableView(List<Appointment> appointments) {
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(FXCollections.observableArrayList(appointments));
        SortedList<Appointment> sortedList = new SortedList<>(filteredAppointments);
        sortedList.comparatorProperty().bind(appointmentsTable.comparatorProperty());

        appointmentsTable.setItems(sortedList);
}
}
