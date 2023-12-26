package com.example.tooth_care;

import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceController {
    public Label idLabel;
    public Label patientNameLabel;
    public Label dateLabel;
    public Label chargeLabel;
    public Label treatmentLabel;

    public void initializeInvoice(int id, String patientName, LocalDateTime dateTime, double charge, String treatments) {
        idLabel.setText("ID: " + id);
        patientNameLabel.setText("Patient Name: " + patientName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dateLabel.setText("Date: " + dateTime.format(formatter));

        chargeLabel.setText("Treatment Charge: " + charge);
        treatmentLabel.setText("Treatment Types: \n" + treatments);
    }

}
