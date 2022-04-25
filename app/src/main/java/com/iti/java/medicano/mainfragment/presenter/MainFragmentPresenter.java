package com.iti.java.medicano.mainfragment.presenter;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.User;

import java.util.List;

public interface MainFragmentPresenter {
    LiveData<List<User>> syncUsers();
    void switchUser(User user);

    User getCurrentUser();
}
