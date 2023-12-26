package com.example.tooth_care;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TimeSlotController {
    @FXML
    private DatePicker datePicker;

    @FXML
    private GridPane timeSlotsGrid;

    @FXML
    private void initialize() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.getDayOfWeek() == DayOfWeek.TUESDAY ||
                        date.getDayOfWeek() == DayOfWeek.THURSDAY ||
                        date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Change color for disabled dates
                }

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;"); // Change color for disabled dates
                }
            }
        });
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateTimeSlots(newValue));
    }

    private void updateTimeSlots(LocalDate selectedDate) {
        // Clear existing time slots (if any)
        timeSlotsGrid.getChildren().clear();

        // Fetch available time slots based on selectedDate and populate the GridPane
        // Simulated example - populate 10 time slots for demonstration
        for (int i = 0; i < 10; i++) {
            Button timeSlotBtn = new Button("Time Slot " + i);
            if (i % 2 == 0) {
                timeSlotBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                timeSlotBtn.setOnAction(event -> handleTimeSlotSelection(timeSlotBtn.getText()));
            } else {
                timeSlotBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                timeSlotBtn.setDisable(true);
            }
            timeSlotsGrid.add(timeSlotBtn, i, 0);
        }
    }

    private void handleTimeSlotSelection(String selectedTimeSlot) {
        // Logic to handle selection of available time slot
        System.out.println("Selected time slot: " + selectedTimeSlot);
    }

    @FXML
    private void confirmAppointment() {
        // Logic to confirm the appointment based on the selected time slot
        // You can retrieve the selected date and time slot to proceed with the appointment confirmation
    }
}
