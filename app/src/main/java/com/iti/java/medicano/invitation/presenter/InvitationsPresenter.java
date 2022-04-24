package com.iti.java.medicano.invitation.presenter;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Medication;

import java.util.HashMap;
import java.util.List;

public interface InvitationsPresenter {
    LiveData<HashMap<String,Object>> getInvitations();
    void acceptMedFriendWithID(String id , String name);
    void denyMedFriendWithID(String id);

}
