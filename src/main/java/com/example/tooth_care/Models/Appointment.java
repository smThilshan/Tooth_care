package com.example.tooth_care.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Appointment {
    private int id;
    private String patientName;
    private LocalDateTime dateTime;
    private int patientId;
    private ArrayList<Integer> typeIds;
    private double charge;

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }



    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        ArrayList<Appointment> appointments = DataManager.getInstance().getAppointments();
        appointmentsList.addAll(appointments);
        return appointmentsList;
    }

    public ArrayList<AppointmentType> getType() {
        ArrayList<AppointmentType> returnTypes = new ArrayList<>();
        ArrayList<AppointmentType> types = DataManager.getInstance().getAppointmentTypes();
        for (AppointmentType listType: types) {
            for (Integer typeId:this.typeIds) {
                if (typeId == listType.getId())
                {
                    returnTypes.add(listType);
                }
            }
        }
        return returnTypes;
    }


    public int getId() {
        return id;
    }



    public Appointment(int id, String patientName, LocalDateTime dateTime, int patientId, ArrayList typeIds, double charge) {
        this.id = id;
        this.patientName = patientName;
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.typeIds = typeIds;
        this.charge = charge;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public ArrayList<Integer> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(ArrayList typeId) {
        this.typeIds = typeId;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public static Appointment getAppointmentById(int id){
        ArrayList<Appointment> appointments = DataManager.getInstance().getAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null;
    }


}
