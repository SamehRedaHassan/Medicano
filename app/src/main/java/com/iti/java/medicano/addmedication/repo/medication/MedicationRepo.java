package com.iti.java.medicano.addmedication.repo.medication;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Medication;

import java.util.List;

public interface MedicationRepo {
    void upDateDatabase();
    void insertMedication(Medication medication);
    void deleteMedication(Medication medication);
    void editMedication(Medication medication);
    Medication getMedication(String medicationId);
    LiveData<List<Medication>> getUserMedications(String userId);
    List<Medication> getAlMedications();
    void setUserId(String userId);
    LiveData<List<Medication>> getUserMedicationForDay(String uId, long dayDate, String dayCode);
    List<Medication> getAllMedicationForDay(long dayDate, String dayCode);
    void setDayAndDate(String uId, long dayDate, String dayCode);
    void requestUpdateMedicationsForCurrentUser(String userId) ;
}
