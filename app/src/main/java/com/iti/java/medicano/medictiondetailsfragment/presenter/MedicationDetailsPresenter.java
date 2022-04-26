package com.iti.java.medicano.medictiondetailsfragment.presenter;

import com.iti.java.medicano.model.Medication;

public interface MedicationDetailsPresenter {
    void updateMedication(Medication med);

    void deleteMedication(Medication med);
    void  editMedication(Medication medication);
}
