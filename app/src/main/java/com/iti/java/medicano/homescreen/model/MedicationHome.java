package com.iti.java.medicano.homescreen.model;

public class MedicationHome {

    private String medName;
    private String currentReminder;
    private String status;
    private String leftQuantity;
    private String medDesc;
    private int medImg;

    public MedicationHome() {
    }

    public MedicationHome(String medName, String currentReminder, String status, String leftQuantity, String medDesc, int medImg) {
        this.medName = medName;
        this.currentReminder = currentReminder;
        this.status = status;
        this.leftQuantity = leftQuantity;
        this.medDesc = medDesc;
        this.medImg = medImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeftQuantity() {
        return leftQuantity;
    }

    public void setLeftQuantity(String leftQuantity) {
        this.leftQuantity = leftQuantity;
    }

    public String getCurrentReminder() {
        return currentReminder;
    }

    public void setCurrentReminder(String currentReminder) {
        this.currentReminder = currentReminder;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDesc() {
        return medDesc;
    }

    public void setMedDesc(String medDesc) {
        this.medDesc = medDesc;
    }

    public int getMedImg() {
        return medImg;
    }

    public void setMedImg(int medImg) {
        this.medImg = medImg;
    }
}
