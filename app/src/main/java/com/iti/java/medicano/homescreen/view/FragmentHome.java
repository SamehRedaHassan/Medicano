package com.iti.java.medicano.homescreen.view;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentHomeBinding;
import com.iti.java.medicano.homescreen.model.Medication;
import com.iti.java.medicano.homescreen.model.MedicationList;
import com.iti.java.medicano.homescreen.presenter.HomePresenter;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements HomeViewInterface,DatePickerListener {

    private FragmentHomeBinding binding;
    private RecyclerView homeRecyclerView;
    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private NavController outerNavController;
    private HomePresenter presenter;
    private User user;

    private FloatingActionButton addBtn,addMedicationBtn,addTrackerBtn,addDoseBtn;
    private TextView addMedicationTxt,addTrackerTxt,addDoseTxt;
    private Animation fabOpen,fabClose,mainOpen,mainClose;
    private HorizontalPicker picker;
    boolean isOpened;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        presenter = new HomePresenter(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)));
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();

        user = presenter.getUser();
        Toast.makeText(getContext(), user.getFullName(), Toast.LENGTH_SHORT).show();
        picker.setListener(dateSelected -> {
            Toast.makeText(getContext(), "day pressed", Toast.LENGTH_LONG).show();
        }).init();

        addBtn.setOnClickListener(view12 -> floatingButtonUI());

        addMedicationBtn.setOnClickListener(view1 -> {
            if (outerNavController.getCurrentDestination().getId() == R.id.mainFragment){
                outerNavController.navigate(R.id.action_mainFragment_to_navigation);
            }
        });

        addTrackerBtn.setOnClickListener(view1 -> {
            if (outerNavController.getCurrentDestination().getId() == R.id.mainFragment){
                outerNavController.navigate(R.id.addMedFriendImpl);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getContext(), "day pressed", Toast.LENGTH_LONG).show();
    }

    private void initUI(){
        outerNavController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        //outerNavController.navigate(R.id.action_mainFragment_to_editMedicationFragment);

        homeRecyclerView = binding.homeRecycler;
        layoutManager = new LinearLayoutManager(getActivity());
        homeAdapter = new HomeAdapter(getActivity(),getMedicationList());
        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setLayoutManager(layoutManager);

        picker = (HorizontalPicker)binding.datePicker;

        addBtn = binding.addBtn;
        addMedicationBtn = binding.addMedicationBtn;
        addTrackerBtn = binding.addTrackerBtn;
        addDoseBtn = binding.addDoseBtn;

        addMedicationTxt = binding.addMedicationTxt;
        addTrackerTxt = binding.addTrackerTxt;
        addDoseTxt = binding.addDoseTxt;

        fabOpen = AnimationUtils.loadAnimation(getContext(),R.anim.to_bottom);
        fabClose = AnimationUtils.loadAnimation(getContext(),R.anim.from_bottom);
        mainOpen = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open);
        mainClose = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_close);

        isOpened = false;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        Toast.makeText(getContext(), user.getFullName(), Toast.LENGTH_SHORT).show();
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

        medItemList.add(new Medication("Panadol","10 g, take 1 Pill(s)","ic__03_capsules"));
        medItemList.add(  new Medication("Panadol","10 g, take 1 Pill(s)","ic__03_capsules"));
        medItemList.add(new Medication("Panadol","10 g, take 1 Pill(s)","ic__03_capsules"));

        return medItemList;
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        //Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
        Toast.makeText(getContext(), "day pressed", Toast.LENGTH_SHORT).show();
    }

    public void floatingButtonUI(){
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