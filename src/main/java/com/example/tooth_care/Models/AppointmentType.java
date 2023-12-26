package com.example.tooth_care.Models;

import java.util.ArrayList;

public class AppointmentType {
    int id;
    String name;
    double cost;

    public AppointmentType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    int duration;

    public AppointmentType(int id, String name, double cost, int duration) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.duration = duration;
    }
    public static AppointmentType getTypeById(int id)
    {
        ArrayList<AppointmentType> types = DataManager.getInstance().getAppointmentTypes();
        for (AppointmentType type: types) {
            if (id == type.id)
            {
                return type;
            }
        }
        return new AppointmentType();
    }
    public static AppointmentType getTypesByName(String typeName){
        ArrayList<AppointmentType> types = DataManager.getInstance().getAppointmentTypes();
        for (AppointmentType type: types) {
            if (typeName.contains(type.getName()))
            {
                return type;
            }
        }
        return new AppointmentType();
    }
}
