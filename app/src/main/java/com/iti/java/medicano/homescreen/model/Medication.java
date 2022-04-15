package com.iti.java.medicano.homescreen.model;

public class Medication {

    private String medName;
    private String medDesc;
    private String medImg;

    public Medication() {
    }

    public Medication(String medName, String medDesc, String medImg) {
        this.medName = medName;
        this.medDesc = medDesc;
        this.medImg = medImg;
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

    public String getMedImg() {
        return medImg;
    }

    public void setMedImg(String medImg) {
        this.medImg = medImg;
    }
}
