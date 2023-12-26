package com.example.tooth_care.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private String address;
    private String email;
    public Patient(int id, String firstName, String lastName, String phoneNumber, int age,
                   String address, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.address = address;
        this.email = email;
    }

    public static ObservableList<String> getNameList() {
        ObservableList<String> nameList = FXCollections.observableArrayList();

        ArrayList<Patient> patients = DataManager.getInstance().getPatients();
        for (Patient patient : patients) {
            String name = patient.getFirstName() + " " + patient.getLastName();
            nameList.add(name);
        }

        return nameList;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Patient getPatient (String name)
    {
        String first = name.split(" ")[0];
        String last = name.split(" ")[1];
        ArrayList<Patient> patients = DataManager.getInstance().getPatients();

        for (Patient patient : patients){
            if (first.contains(patient.firstName) && last.contains(patient.lastName))
            {
                return patient;
            }
        }
        return null;

    }
    public static ObservableList<Patient> getPatientsList()
    {
        ObservableList<Patient> patientsList = FXCollections.observableArrayList();
        ArrayList<Patient> patients = DataManager.getInstance().getPatients();
        patientsList.addAll(patients);
        return patientsList;
    }
    public static Patient findPatientByID(int idToFind) {
        ArrayList<Patient> patientList = DataManager.getInstance().getPatients();
        for (Patient patient : patientList) {
            if (patient.getId() == idToFind) {
                return patient;
            }
        }
        return null;
    }
}

