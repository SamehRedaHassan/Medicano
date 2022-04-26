package com.iti.java.medicano.invitation.view;

import static android.content.Context.MODE_PRIVATE;
import static com.iti.java.medicano.Constants.POSITION;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_ID;
import static com.iti.java.medicano.utils.BundleKeys.REMINDER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentInvitationsBinding;
import com.iti.java.medicano.invitation.presenter.InvitationsPresenter;
import com.iti.java.medicano.invitation.presenter.InvitationsPresenterImpl;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.utils.OnNotifyDataChanged;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvitationsFragment extends Fragment implements InvitationsView, AcceptDenyCallback, OnNotifyDataChanged {

    private FragmentInvitationsBinding binding;
    RecyclerView invitationsView;
    InvitationsAdapter invitationsAdapter;
    InvitationsPresenter presenter;
    HashMap<String, Object> invitations = new HashMap<>();
    RecyclerView refillRecycler;
    RefillReminderAdapter refillAdapter;
    List<Medication> medsNeedToRefill = new ArrayList<>();
    private NavController navController;
    private float lastFetchedValue;

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
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        configureUI();
        initData();
        setObservers();
    }

    private void setObservers() {
        if (navController.getCurrentBackStackEntry() != null) {
            SavedStateHandle savedStateHandler = navController.getCurrentBackStackEntry().getSavedStateHandle();
            savedStateHandler.getLiveData(REMINDER).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(REMINDER).observe(getViewLifecycleOwner(), (valueOfRefill) -> {
                lastFetchedValue = (float) valueOfRefill;
            });
            savedStateHandler.getLiveData(POSITION).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(POSITION).observe(getViewLifecycleOwner(), (position) -> {
                int pos = (Integer) position;
                if (medsNeedToRefill.size() > 0) {
                    Medication medication = medsNeedToRefill.get(pos);
                    medsNeedToRefill.remove(medication);
                    refillAdapter.notifyDataSetChanged();
                    new Thread(() -> {
                        medication.getRefillReminder().currentNumOfPills += lastFetchedValue;
                        medication.needsToRefill = medication.getRefillReminder().currentNumOfPills <= medication.getRefillReminder().countToReminderWhenReach;
                        presenter.editMedication(medication);
                        //presenter.getMedicationsNeedToRefill();
                    }).start();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureUI() {
        invitationsView = binding.invitationsRecycler;
        refillRecycler = binding.rvRefillReminder;
        invitationsView.setHasFixedSize(true);
        refillRecycler.setHasFixedSize(true);
    }

    private void initData() {
        presenter = new InvitationsPresenterImpl(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(), getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE)),
                MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                        FirebaseDatabase.getInstance(),
                        FirebaseAuth.getInstance().getUid(),
                        WorkManager.getInstance(getContext().getApplicationContext())));

        presenter.getInvitations().removeObservers(getViewLifecycleOwner());
        presenter.getInvitations().observe(getViewLifecycleOwner(), invitors -> {
            if (invitations != null) {
                invitations.clear();
            }
            invitations = invitors;
            //  Log.i("TAG", "onChanged: " + invitations.size());
            //ABDOO
            invitationsAdapter = new InvitationsAdapter(getContext(), invitations, InvitationsFragment.this);
            invitationsView.setAdapter(invitationsAdapter);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            invitationsView.setLayoutManager(manager);
            invitationsAdapter.notifyDataSetChanged();
        });
        presenter.getMedicationsNeedToRefill().removeObservers(getViewLifecycleOwner());
        presenter.getMedicationsNeedToRefill().observe(getViewLifecycleOwner(), medications -> {
            if (medsNeedToRefill != null) {
                medsNeedToRefill.clear();
            }
            medsNeedToRefill = medications;
            Log.i("TAG", "onChanged:" + medications.size());
            refillAdapter = new RefillReminderAdapter(navController, medsNeedToRefill);
            refillRecycler.setAdapter(refillAdapter);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            refillRecycler.setLayoutManager(manager);
            refillAdapter.notifyDataSetChanged();
        });

    }


    @Override
    public void didPressAcceptWithID(String id, String name) {
        presenter.acceptMedFriendWithID(id, name);

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
