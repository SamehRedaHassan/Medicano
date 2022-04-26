package com.iti.java.medicano.homescreen.presenter;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.homescreen.view.HomeViewInterface;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.UserRepo;

import java.util.List;

public class HomePresenter implements HomePresenterInterface {

    private HomeViewInterface homeView;
    private UserRepo repo;
    private MedicationRepo mediRepo;

    public HomePresenter(HomeViewInterface homeView,UserRepo repo,MedicationRepoImpl mediRepo){
        this.homeView =homeView;
        this.repo = repo;
        this.mediRepo = mediRepo;
    }

    public User getUser(){
        return repo.getPreferences();
    }

    @Override
    public void setDateChange(String uId, long dayDate, String dayCode) {
        mediRepo.setDayAndDate(uId,dayDate,dayCode);
    }

    @Override
    public LiveData<List<Medication>> getUserMedications(String uId){
        return mediRepo.getUserMedications(uId);
    }

    @Override
    public boolean isOwnerUser() {
        return repo.isOwnerUser();
    }

    @Override
    public LiveData<List<Medication>> getMyMedicationsForDay(String uId, long dayDate, String dayCode) {
        return mediRepo.getUserMedicationForDay(uId,dayDate,dayCode);
    }

}
