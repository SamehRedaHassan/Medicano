package com.iti.java.medicano.model.userrepo;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserRepo {
    void registerToFirebase(User user, RegisterCallbackInterface callback);
    void loginToFirebase(String email, String password, LoginCallbackInterface callback);
    void insertToRoom(User user);
    void addToPreferences(User user);
    User getPreferences();
    void inviteMedFriendWithEmail(String email);
    LiveData<HashMap<String,Object>> listenToMedFriendsInvitations();
    void acceptMedFriendInvitationWithID(String id ,String name);
    void DenyMedFriendInvitationWithID(String id);
    void getUserFromFirebaseAndSaveToRoom(String id);
    LiveData<List<User>> getLocalUsersFromRoom();
    void addOwnerUserToPreferences(User user);
    User getOwnerUserPreferences();
}
