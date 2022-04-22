package com.iti.java.medicano.addmedication.icon.presenter;

import android.util.Log;

import com.iti.java.medicano.addmedication.icon.view.AddMedIcon;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.User;

public class AddMedIconPresenterImpl implements AddMedIconPresenter{

   private AddMedIcon view ;
   private MedicationRepo repo;


    public AddMedIconPresenterImpl(AddMedIcon view, MedicationRepo repo){
       this.view = view;
       this.repo = repo;
   }

    @Override
    public void insertMedicationIntoDB(Medication medication) {
        Log.i("TAG", "insertMedication: REPOOOOOOOOOO");
        repo.insertMedication(medication);
    }



}
