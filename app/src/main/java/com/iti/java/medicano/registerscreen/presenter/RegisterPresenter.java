package com.iti.java.medicano.registerscreen.presenter;

import static android.content.ContentValues.TAG;
import android.util.Log;

import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.RegisterCallbackInterface;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.registerscreen.view.RegisterViewInterface;

public class RegisterPresenter implements RegisterPresenterInterface, RegisterCallbackInterface {

    private RegisterViewInterface registerView;
    private UserRepo repo;

    public RegisterPresenter(RegisterViewInterface view,UserRepo repo){
        this.registerView = view;
        this.repo = repo;
    }

    @Override
    public void registerUser(User user){
        repo.registerToFirebase(user,this);
    }

    @Override
    public void registeredSuccessfully() {
        Log.i(TAG, "registered successfully");
        registerView.navigateToHomeScreen();
    }

    @Override
    public void registeredFailed() {
        Log.i(TAG, "registered failed");
        registerView.showErrorMsg();
    }
}
