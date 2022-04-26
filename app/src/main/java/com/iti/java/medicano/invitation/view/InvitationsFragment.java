package com.iti.java.medicano.invitation.view;

import static android.content.Context.MODE_PRIVATE;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentInvitationsBinding;
import com.iti.java.medicano.invitation.presenter.InvitationsPresenter;
import com.iti.java.medicano.invitation.presenter.InvitationsPresenterImpl;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.utils.OnNotifyDataChanged;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvitationsFragment  extends Fragment implements InvitationsView , AcceptDenyCallback, OnNotifyDataChanged {

    private FragmentInvitationsBinding binding ;
    RecyclerView invitationsView ;
    InvitationsAdapter invitationsAdapter;
    InvitationsPresenter presenter ;
    HashMap<String,Object> invitations = new HashMap<>();
    RecyclerView refillRecycler;
    RefillReminderAdapter refillAdapter;
    List<Medication> medsNeedToRefill = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvitationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureUI(){
        invitationsView = binding.invitationsRecycler;
        refillRecycler = binding.rvRefillReminder;
        invitationsView.setHasFixedSize(true);
        refillRecycler.setHasFixedSize(true);
    }

    private void initData() {
        presenter = new InvitationsPresenterImpl(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)),
                MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                        FirebaseDatabase.getInstance(),
                        FirebaseAuth.getInstance().getUid(),
                        WorkManager.getInstance(getContext().getApplicationContext())));

        presenter.getInvitations().removeObservers(getViewLifecycleOwner());
        presenter.getInvitations().observe(getViewLifecycleOwner(), invitors -> {
            if(invitations != null){
                invitations.clear();
            }
            invitations = invitors ;
          //  Log.i("TAG", "onChanged: " + invitations.size());
            //ABDOO
            invitationsAdapter = new InvitationsAdapter(getContext(),invitations,InvitationsFragment.this);
            invitationsView.setAdapter(invitationsAdapter);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            invitationsView.setLayoutManager(manager);
            invitationsAdapter.notifyDataSetChanged();
        });

        presenter.getMedicationsNeedToRefill().observe(getViewLifecycleOwner(), new Observer<List<Medication>>() {
            @Override
            public void onChanged(List<Medication> medications) {
                if(medsNeedToRefill != null){
                    medsNeedToRefill.clear();
                }
                medsNeedToRefill = medications;
                Log.i("TAG", "onChanged:"+medications.size());
                refillAdapter = new RefillReminderAdapter(getContext(),medsNeedToRefill);
                refillRecycler.setAdapter(refillAdapter);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                refillRecycler.setLayoutManager(manager);
                refillAdapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    public void didPressAcceptWithID(String id, String name) {
        presenter.acceptMedFriendWithID(id,name);

    }

    @Override
    public void didPressDenyWithID(String id) {
        Log.i("TAG", "onClick: DENYYY" + id);
        presenter.denyMedFriendWithID(id);
    }

    @Override
    public void notifyDataChanged() {
        presenter.requestUserInvitations();
    }
}
