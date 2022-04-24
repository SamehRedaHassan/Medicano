package com.iti.java.medicano.addmedfriend.presenter;

import com.iti.java.medicano.addmedfriend.view.AddMedFriend;
import com.iti.java.medicano.model.userrepo.UserRepo;

public class AddMedFriendPresenterImpl implements AddMedFriendPresenter{
    private AddMedFriend view ;
    private UserRepo repo;


    public AddMedFriendPresenterImpl(AddMedFriend view, UserRepo repo){
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void addMedFriendWithEmail(String email) {
        repo.inviteMedFriendWithEmail(email);
    }
}
