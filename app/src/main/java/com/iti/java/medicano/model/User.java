package com.iti.java.medicano.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.iti.java.medicano.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String fullName;
    private String email;
    @Ignore
    private String password;
    private int gender;
    private HashMap< String , String> trackingList;
    private HashMap< String , String> invitationList;
    public User(){

    }

    public HashMap<String, String> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(HashMap<String, String> trackingList) {
        this.trackingList = trackingList;
    }

    public HashMap<String, String> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(HashMap<String, String> invitationList) {
        this.invitationList = invitationList;
    }

    public User(String fullName, String email, String password, int gender) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.invitationList = new HashMap<>();
        this.trackingList = new HashMap<>();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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
