package com.iti.java.medicano.homescreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iti.java.medicano.MainActivity;
import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.Medication;
import com.iti.java.medicano.homescreen.model.MedicationList;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private RecyclerView homeRecyclerView;
    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeRecyclerView = view.findViewById(R.id.homeRecycler);
        layoutManager = new LinearLayoutManager(getActivity());
        homeAdapter = new HomeAdapter(getActivity(),getMedicationList());

        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setLayoutManager(layoutManager);
    }

    private List<MedicationList> getMedicationList(){
        List<MedicationList> itemList = new ArrayList<>();

        itemList.add(new MedicationList("20:00",getNestedMedicationList()));
        itemList.add(new MedicationList("21:00",getNestedMedicationList()));
        itemList.add(new MedicationList("22:00",getNestedMedicationList()));

        return itemList;
    }

    private List<Medication> getNestedMedicationList(){
        List<Medication> medItemList = new ArrayList<>();

        medItemList.add(new Medication("Panadol","10 g, take 1 Pill(s)","capsule"));
        medItemList.add(new Medication("Panadol","10 g, take 1 Pill(s)","capsule"));
        medItemList.add(new Medication("Panadol","10 g, take 1 Pill(s)","capsule"));

        return medItemList;
    }
}