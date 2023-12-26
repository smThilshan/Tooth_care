module com.example.tooth_care {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tooth_care to javafx.fxml;
    exports com.example.tooth_care;
    exports com.example.tooth_care.Models;
    opens com.example.tooth_care.Models to javafx.fxml;
}