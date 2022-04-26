package com.iti.java.medicano.invitation.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.invitation.view.InvitationsView;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.mymedications.view.MyMedicationsView;

import java.util.HashMap;
import java.util.List;

public class InvitationsPresenterImpl implements InvitationsPresenter {
    private InvitationsView view ;
    private UserRepo repo;
    private MedicationRepo mediRepo;

    public InvitationsPresenterImpl(InvitationsView view, UserRepo repo,MedicationRepo mediRepo){
        this.view = view;
        this.repo = repo;
        this.mediRepo = mediRepo;
    }

    @Override
    public LiveData<HashMap<String, Object>> getInvitations() {
        LiveData<HashMap<String, Object>> result = repo.listenToMedFriendsInvitations();
        Log.i("TAG", "getInvitationssss: " + result.toString());
        return result ;
    }

    @Override
    public void acceptMedFriendWithID(String id, String name) {
        repo.acceptMedFriendInvitationWithID(id , name);

    }

    @Override
    public void denyMedFriendWithID(String id) {
        repo.DenyMedFriendInvitationWithID(id);
    }

    @Override
    public void requestUserInvitations() {
        repo.requestUserInvitations();
    }

    @Override
    public LiveData<List<Medication>> getMedicationsNeedToRefill() {
        return mediRepo.getMedicationsNeedsToRefill(repo.getPreferences().getId());
    }
}
