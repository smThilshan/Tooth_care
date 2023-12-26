package com.example.tooth_care.Models;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DataManager {
    private static DataManager instance = null;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Stage primaryStage;

    private ArrayList<Appointment> appointments;

    private ArrayList<AppointmentType> appointmentTypes;
    private ArrayList<Patient> patients;

    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    private ArrayList<TimeSlot> timeSlots;

    private DataManager() {
        appointments = new ArrayList<>();
        appointmentTypes = new ArrayList<>();
        appointmentTypes.add(new AppointmentType(1,"Cleaning",1000,1));
        appointmentTypes.add(new AppointmentType(2,"Whitening",2000,1));
        appointmentTypes.add(new AppointmentType(3,"Filling",5000,2));
        appointmentTypes.add(new AppointmentType(4,"Nerve Filling",10000,3));
        appointmentTypes.add(new AppointmentType(5,"Root Canal Therapy",5000,4));
        patients = new ArrayList<>();
        patients.add(new Patient(1, "Kamal", "Fernando", "077542131", 38, "Muthuwel Street, Negombo", "kamalnf@egmail.com"));
        timeSlots = new ArrayList<>();
        primaryStage = new Stage();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }


    public void setAppointments(ArrayList appointments) {
        this.appointments = appointments;
    }

    public ArrayList getAppointments() {
        return appointments;
    }
    public ArrayList<AppointmentType> getAppointmentTypes() {
        return appointmentTypes;
    }

    public void setAppointmentTypes(ArrayList<AppointmentType> appointmentTypes) {
        this.appointmentTypes = appointmentTypes;
    }
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }
}
