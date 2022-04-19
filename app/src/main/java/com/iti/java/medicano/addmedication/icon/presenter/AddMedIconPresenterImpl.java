package com.iti.java.medicano.addmedication.icon.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.iti.java.medicano.addmedication.icon.view.AddMedIcon;
import com.iti.java.medicano.addmedication.repo.AddMedicationRepo;
import com.iti.java.medicano.model.Medication;

public class AddMedIconPresenterImpl implements AddMedIconPresenter{

   private AddMedIcon view ;
   private AddMedicationRepo repo;


    public AddMedIconPresenterImpl(AddMedIcon view, AddMedicationRepo repo){
       this.view = view;
       this.repo = repo;
   }

    @Override
    public void insertMedicationIntoDB(Medication medication) {
        Log.i("TAG", "insertMedication: REPOOOOOOOOOO");
        repo.insertMedication(medication);
    }
}
