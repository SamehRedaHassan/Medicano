package com.iti.java.medicano.homescreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.java.medicano.MainActivity;
import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.Medication;
import com.iti.java.medicano.homescreen.model.MedicationList;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements DatePickerListener {

    private RecyclerView homeRecyclerView;
    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;

    private FloatingActionButton addBtn,addMedicationBtn,addTrackerBtn,addDoseBtn;
    private TextView addMedicationTxt,addTrackerTxt,addDoseTxt;
    private Animation fabOpen,fabClose,mainOpen,mainClose;
    boolean isOpened;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "on create", Toast.LENGTH_LONG).show();
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

        HorizontalPicker picker = (HorizontalPicker)view.findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker
                .setListener(dateSelected -> {
                    Toast.makeText(getContext(), "day pressed", Toast.LENGTH_LONG).show();
                })
                .init();



        addBtn = view.findViewById(R.id.addBtn);
        addMedicationBtn = view.findViewById(R.id.addMedicationBtn);
        addTrackerBtn = view.findViewById(R.id.addTrackerBtn);
        addDoseBtn = view.findViewById(R.id.addDoseBtn);

        addMedicationTxt = view.findViewById(R.id.addMedicationTxt);
        addTrackerTxt = view.findViewById(R.id.addTrackerTxt);
        addDoseTxt = view.findViewById(R.id.addDoseTxt);

        fabOpen = AnimationUtils.loadAnimation(getContext(),R.anim.to_bottom);
        fabClose = AnimationUtils.loadAnimation(getContext(),R.anim.from_bottom);
        mainOpen = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open);
        mainClose = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_close);

        isOpened = false;

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedication();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), "day pressed", Toast.LENGTH_LONG).show();
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
        medItemList.add(  new Medication("Panadol","10 g, take 1 Pill(s)","capsule"));
        medItemList.add(new Medication("Panadol","10 g, take 1 Pill(s)","capsule"));

        return medItemList;
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        //Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
        Toast.makeText(getContext(), "day pressed", Toast.LENGTH_SHORT).show();
    }

    public void addMedication(){
        if(isOpened){
            mainClose.setDuration(200);
            fabClose.setDuration(200);

            addBtn.startAnimation(mainClose);

            addMedicationBtn.startAnimation(fabClose);
            addMedicationTxt.startAnimation(fabClose);
            addTrackerBtn.startAnimation(fabClose);
            addTrackerTxt.startAnimation(fabClose);
            addDoseBtn.startAnimation(fabClose);
            addDoseTxt.startAnimation(fabClose);

            addMedicationBtn.setVisibility(View.INVISIBLE);
            addMedicationTxt.setVisibility(View.INVISIBLE);
            addTrackerBtn.setVisibility(View.INVISIBLE);
            addTrackerTxt.setVisibility(View.INVISIBLE);
            addDoseBtn.setVisibility(View.INVISIBLE);
            addDoseTxt.setVisibility(View.INVISIBLE);

            isOpened = false;
        }
        else{
            mainOpen.setDuration(200);
            fabOpen.setDuration(200);

            addBtn.startAnimation(mainOpen);

            addMedicationBtn.startAnimation(fabOpen);
            addMedicationTxt.startAnimation(fabOpen);
            addTrackerBtn.startAnimation(fabOpen);
            addTrackerTxt.startAnimation(fabOpen);
            addDoseBtn.startAnimation(fabOpen);
            addDoseTxt.startAnimation(fabOpen);

            addMedicationBtn.setVisibility(View.VISIBLE);
            addMedicationTxt.setVisibility(View.VISIBLE);
            addTrackerBtn.setVisibility(View.VISIBLE);
            addTrackerTxt.setVisibility(View.VISIBLE);
            addDoseBtn.setVisibility(View.VISIBLE);
            addDoseTxt.setVisibility(View.VISIBLE);

            isOpened = true;
        }
    }


}