package com.iti.java.medicano.mymedications.view;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentMyMedicationsBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.mymedications.presenter.MyMedicationsPresenter;
import com.iti.java.medicano.mymedications.presenter.MyMedicationsPresenterImpl;
import com.iti.java.medicano.utils.MedicationStatus;

import java.util.ArrayList;
import java.util.List;

public class MyMedicationsFragment extends Fragment implements MyMedicationsView {

    private FragmentMyMedicationsBinding binding;

    RecyclerView activeMedsRecyclerView ;
    RecyclerView suspendedMedsRecyclerView;
    MyMedicationsPresenter presenter ;
    MyAdapter activeMedsAdapter ;
    MyAdapter suspendedMedsAdapter ;
    List<Medication> activeMeds = new ArrayList<>();
    List<Medication> suspendedMeds = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyMedicationsBinding.inflate(inflater, container, false);
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
        activeMedsRecyclerView = binding.rvActiveMedications;
        suspendedMedsRecyclerView = binding.rvInActiveMedications;

        activeMedsRecyclerView.setHasFixedSize(true);
        suspendedMedsRecyclerView.setHasFixedSize(true);

        activeMedsAdapter = new MyAdapter(getContext(),activeMeds);
        suspendedMedsAdapter = new MyAdapter(getContext(),suspendedMeds);

        activeMedsRecyclerView.setAdapter(activeMedsAdapter);
        suspendedMedsRecyclerView.setAdapter(suspendedMedsAdapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        activeMedsRecyclerView.setLayoutManager(manager);
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        suspendedMedsRecyclerView.setLayoutManager(manager2);
    }

    private void initData() {
        //TODO id should be replaced with userRepo . geT Current User
        presenter = new MyMedicationsPresenterImpl(this,
                MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                        FirebaseDatabase.getInstance(),
                        FirebaseAuth.getInstance().getUid(),
                        WorkManager.getInstance(getContext().getApplicationContext())),
                UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)));
        presenter.getMedications().removeObservers(getViewLifecycleOwner());
        presenter.getMedications().observe(getViewLifecycleOwner(), new Observer<List<Medication>>() {
             @Override
             public void onChanged(List<Medication> medications) {
                 suspendedMeds.clear();
                 activeMeds.clear();
                 for (Medication medication : medications){
                     if(medication.status == MedicationStatus.INACTIVE){
                         suspendedMeds.add(medication);
                     }else{
                         activeMeds.add(medication);
                     }
                 }
                 activeMedsAdapter.notifyDataSetChanged();
                 suspendedMedsAdapter.notifyDataSetChanged();
             }
         });
    }
}