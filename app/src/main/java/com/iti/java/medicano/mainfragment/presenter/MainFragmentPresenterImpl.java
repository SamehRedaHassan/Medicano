package com.iti.java.medicano.mainfragment.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.mainfragment.view.MainFragmentView;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.UserRepo;

import java.util.List;

public class MainFragmentPresenterImpl implements  MainFragmentPresenter{

    MainFragmentView view;
    private UserRepo userRepo ;
    MedicationRepo medRepo ;

    public MainFragmentPresenterImpl(MainFragmentView view, UserRepo userRepo, MedicationRepo medRepo){
        this.view = view;
        this.userRepo = userRepo;
        this.medRepo = medRepo;
    }


    @Override
    public  LiveData<List<User>> syncUsers() {
      return   userRepo.getLocalUsersFromRoom();
    }

    @Override
    public void switchUser(User user) {
        userRepo.addToPreferences(user);
        medRepo.setUserId(user.getId());
        //swich setUserId
    }

    @Override
    public User getCurrentUser() {
        return userRepo.getPreferences();
    }


}
