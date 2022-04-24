package com.iti.java.medicano.homescreen.model;

import java.util.List;

public class MedicationList {
    private String time;
    private List<MedicationHome> medicationHomes;

    public MedicationList() {

    }

    public MedicationList(String time, List<MedicationHome> medicationHomes) {
        this.time = time;
        this.medicationHomes = medicationHomes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<MedicationHome> getMedications() {
        return medicationHomes;
    }

    public void setMedications(List<MedicationHome> medicationHomes) {
        this.medicationHomes = medicationHomes;
    }
}
