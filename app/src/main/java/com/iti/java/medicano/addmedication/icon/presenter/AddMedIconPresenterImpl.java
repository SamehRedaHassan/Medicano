package com.iti.java.medicano.addmedication.icon.presenter;

import android.util.Log;

import com.iti.java.medicano.addmedication.icon.view.AddMedIcon;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.UserRepo;

public class AddMedIconPresenterImpl implements AddMedIconPresenter{

   private AddMedIcon view ;
   private MedicationRepo repo;
   private UserRepo userRepo;


    public AddMedIconPresenterImpl(AddMedIcon view, MedicationRepo repo,UserRepo userRepo){
       this.view = view;
       this.repo = repo;
       this.userRepo = userRepo;
   }

    @Override
    public void insertMedicationIntoDB(Medication medication) {
        medication.setUserId(userRepo.getPreferences().getId());
        repo.insertMedication(medication);
        view.navigateBack();
    }




}
