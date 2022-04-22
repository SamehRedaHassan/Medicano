package com.iti.java.medicano.mymedications.presenter;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Medication;

import java.util.List;

public interface MyMedicationsPresenter {
        LiveData<List<Medication>> getMedications();
}
