package com.iti.java.medicano.homescreen.model;

import java.util.List;

public class MedicationList {
    private String time;
    private List<Medication> medications;

    public MedicationList() {

    }

    public MedicationList(String time, List<Medication> medications) {
        this.time = time;
        this.medications = medications;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
