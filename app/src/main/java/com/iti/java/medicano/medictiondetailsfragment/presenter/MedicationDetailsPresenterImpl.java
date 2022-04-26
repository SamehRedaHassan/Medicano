package com.iti.java.medicano.medictiondetailsfragment.presenter;

import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.medictiondetailsfragment.view.MedicationDetailsView;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.userrepo.UserRepo;

public class MedicationDetailsPresenterImpl implements MedicationDetailsPresenter {


    private MedicationDetailsView view ;
    private MedicationRepo repo;
    private UserRepo userRepo;

    public MedicationDetailsPresenterImpl(MedicationDetailsView view , MedicationRepo repo, UserRepo userRepo){
        this.view = view;
        this.repo = repo;
        this.userRepo = userRepo;
    }


    @Override
    public void updateMedication(Medication med) {
        repo.editMedication(med);
    }

    @Override
    public void deleteMedication(Medication med) {
        repo.deleteMedication(med);
    }

    @Override
    public void editMedication(Medication medication) {
        repo.editMedication(medication);
    }
  
    public boolean isOwnerUser() {
        return userRepo.isOwnerUser();
    }
}
