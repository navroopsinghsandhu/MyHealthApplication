package com.example.myhealth;

// user defined class for each record
public class UserRecord {

    private int id;
    private String weight;
    private String temperature;

    private String bloodPressure;

    private String note;

    public UserRecord(int id, String weight, String temperature, String bloodPressure, String note) {
        this.id = id;
        this.weight = weight;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getWeight() {
        return weight;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getBloodPressure() {
        return this.bloodPressure;
    }

    public String getNote() {
        return this.note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public void setNote(String note) {
        this.note = note;
    }
}