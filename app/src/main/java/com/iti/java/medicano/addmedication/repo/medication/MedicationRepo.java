package com.iti.java.medicano.addmedication.repo.medication;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Medication;

import java.util.List;

public interface MedicationRepo {
    void insertMedication(Medication medication);
    void deleteMedication(Medication medication);
    void editMedication(Medication medication);
    Medication getMedication(String medicationId);
    LiveData<List<Medication>> getUserMedications(String userId);
    void setUserId(String userId);

}