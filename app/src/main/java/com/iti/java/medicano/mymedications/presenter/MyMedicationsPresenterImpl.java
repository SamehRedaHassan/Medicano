package com.iti.java.medicano.mymedications.presenter;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.addmedication.icon.view.AddMedIcon;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.mymedications.view.MyMedicationsView;

import java.util.List;

public class MyMedicationsPresenterImpl implements MyMedicationsPresenter {

    private MyMedicationsView view ;
    private MedicationRepo repo;


    public MyMedicationsPresenterImpl(MyMedicationsView view, MedicationRepo repo){
        this.view = view;
        this.repo = repo;
    }
    @Override
    public LiveData<List<Medication>> getMedications() {
        return repo.getUserMedications("usbxpr7L0GfhZJlhPxYyYlFx2Wq2");
    }
}
