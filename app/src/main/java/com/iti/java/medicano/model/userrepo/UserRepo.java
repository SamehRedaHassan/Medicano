package com.iti.java.medicano.model.userrepo;

import android.content.Context;

import com.iti.java.medicano.model.User;

public interface UserRepo {
    void registerToFirebase(User user, RegisterCallbackInterface callback);
    void loginToFirebase(String email, String password, LoginCallbackInterface callback);
    void insertToRoom(User user);
    void addToPreferences(User user);
    User getPreferences();
}
