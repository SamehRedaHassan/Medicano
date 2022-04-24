package com.iti.java.medicano.mymedications.presenter;

import androidx.lifecycle.LiveData;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.mymedications.view.MyMedicationsView;
import java.util.List;

public class MyMedicationsPresenterImpl implements MyMedicationsPresenter {

    private final MyMedicationsView view ;
    private final MedicationRepo repo;
    private final UserRepo userRepo ;


    public MyMedicationsPresenterImpl(MyMedicationsView view, MedicationRepo repo , UserRepo userRepo){
        this.view = view;
        this.repo = repo;
        this.userRepo = userRepo;

    }

    @Override
    public LiveData<List<Medication>> getMedications() {
        return repo.getUserMedications(userRepo.getPreferences().getId());
    }

    @Override
    public void requestUpdateMedicationsForCurrentUser() {
        repo.requestUpdateMedicationsForCurrentUser(userRepo.getPreferences().getId());
    }
}
