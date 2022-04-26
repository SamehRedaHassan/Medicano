package com.iti.java.medicano.homescreen.presenter;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.User;

import java.util.List;

public interface HomePresenterInterface {
    User getUser();
    void setDateChange(String uId, long dayDate, String dayCode);
    LiveData<List<Medication>> getMyMedicationsForDay(String uId, long dayDate, String dayCode);
    LiveData<List<Medication>> getUserMedications(String uId);
    boolean isOwnerUser();
}
