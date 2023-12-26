package com.example.tooth_care.Models;

public class Treatment {

    private int id;
    private String name;
    private String description;
    private String treatmentType;
    private int duration; // in minutes


    public Treatment(int id, String name, String description, String treatmentType, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.treatmentType = treatmentType;
        this.duration = duration;
    }

    // Getter and Setter methods
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", treatmentType='" + treatmentType + '\'' +
                ", duration=" + duration +
                '}';
    }
}


