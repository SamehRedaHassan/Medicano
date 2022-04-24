package com.iti.java.medicano.model.userrepo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.User;

import java.util.HashMap;

public interface UserRepo {
    void registerToFirebase(User user, RegisterCallbackInterface callback);
    void loginToFirebase(String email, String password, LoginCallbackInterface callback);
    void insertToRoom(User user);
    void addToPreferences(User user);
    User getPreferences();
    void inviteMedFriendWithEmail(String email);
    LiveData<HashMap<String,Object>> listenToMedFriendsInvitations();
    void acceptMedFriendInvitationWithID(String id);
    void DenyMedFriendInvitationWithID(String id);
}
