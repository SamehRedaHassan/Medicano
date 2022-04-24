package com.iti.java.medicano.loginscreen.presenter;

import android.util.Log;
import android.widget.Toast;

import com.iti.java.medicano.loginscreen.view.LoginViewInterface;
import com.iti.java.medicano.model.userrepo.LoginCallbackInterface;
import com.iti.java.medicano.model.userrepo.UserRepo;

public class LoginPresenter implements LoginPresenterInterface, LoginCallbackInterface {

    public static final String TAG = "TAG";
    private LoginViewInterface loginView;
    private UserRepo repo;


    public LoginPresenter(LoginViewInterface view,UserRepo repo){
        this.loginView = view;
        this.repo = repo;
    }

    @Override
    public void loginUser(String email, String password) {
        repo.loginToFirebase(email,password,this);
    }


    @Override
    public void logedinSuccessfully() {
        Log.i(TAG, "loged in successfully");
        loginView.navigateToHomeScreen();
        repo.listenToMedFriendsInvitations();
    }

    @Override
    public void logedinFailed() {
        Log.i(TAG, "loged in failed");
        loginView.showErrorMsg();
    }

}
