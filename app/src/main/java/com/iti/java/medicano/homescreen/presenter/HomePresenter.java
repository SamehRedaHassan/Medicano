package com.iti.java.medicano.homescreen.presenter;

import com.iti.java.medicano.homescreen.view.HomeViewInterface;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.UserRepo;

public class HomePresenter implements HomePresenterInterface {

    private HomeViewInterface homeView;
    private UserRepo repo;

    public HomePresenter(HomeViewInterface homeView,UserRepo repo){
        this.homeView =homeView;
        this.repo = repo;
    }

    public User getUser(){
        return repo.getPreferences();
    }

}
