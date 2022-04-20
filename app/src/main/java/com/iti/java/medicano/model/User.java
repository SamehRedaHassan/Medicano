package com.iti.java.medicano.model;

public class User {
    private String fullName;
    private String email;
    private String password;
    private int gender;


    public User(){

    }

    public User(String fullName, String email, String password, int gender) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
