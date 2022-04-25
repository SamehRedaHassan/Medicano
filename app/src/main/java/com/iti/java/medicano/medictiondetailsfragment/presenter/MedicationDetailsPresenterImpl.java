package com.iti.java.medicano.medictiondetailsfragment.presenter;

import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.medictiondetailsfragment.view.MedicationDetailsView;
import com.iti.java.medicano.model.Medication;

public class MedicationDetailsPresenterImpl implements MedicationDetailsPresenter {


    private MedicationDetailsView view ;
    private MedicationRepo repo;

    public MedicationDetailsPresenterImpl(MedicationDetailsView view ,MedicationRepo repo){
        this.view = view;
        this.repo = repo;
    }


    @Override
    public void updateMedication(Medication med) {

    }
}
