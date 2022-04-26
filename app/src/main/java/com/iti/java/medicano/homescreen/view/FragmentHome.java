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
import androidx.work.WorkManager;

import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentHomeBinding;
import com.iti.java.medicano.homescreen.model.MedicationHome;
import com.iti.java.medicano.homescreen.presenter.HomePresenter;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.utils.Converters;
import com.iti.java.medicano.utils.MyDateUtils;

import com.iti.java.medicano.utils.OnNotifyDataChanged;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FragmentHome extends Fragment implements HomeViewInterface, DatePickerListener, OnNotifyDataChanged {
    private static final String TAG = "FragmentHome";
    private FragmentHomeBinding binding;
    private RecyclerView homeRecyclerView;
    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private NavController outerNavController;
    private HomePresenter presenter;
    private User user;
    private HashMap<String, List<MedicationHome>> mediList = new HashMap<String, List<MedicationHome>>();
    private long selectedDay;
    private int dayOfWeek;
    private Date reminderDate;

    private FloatingActionButton addBtn, addMedicationBtn, addTrackerBtn;
    private TextView addMedicationTxt, addTrackerTxt;
    private Animation fabOpen, fabClose, mainOpen, mainClose;
    private HorizontalPicker picker;
    boolean isOpened;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new HomePresenter(this,
                UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),
                        getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE)),
                MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                        FirebaseDatabase.getInstance(),
                        FirebaseAuth.getInstance().getUid(),
                        WorkManager.getInstance(getContext().getApplicationContext())));
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();

        user = presenter.getUser();

        picker.setListener(dateSelected -> {

            //Toast.makeText(getContext(), "day pressed", Toast.LENGTH_SHORT).show();
            reminderDate = dateSelected.toDate();

        picker.setListener(dateSelected -> {
            reminderDate = MyDateUtils.truncateToDate(dateSelected.toDate());

            Log.i("TAG", "day selected " + dateSelected);
            selectedDay = Converters.dateToTimestamp(dateSelected.toDate());
            Log.i("TAG", selectedDay + "");

            Calendar c = Calendar.getInstance();
            c.setTime(dateSelected.toDate());
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            presenter.setDateChange(user.getId(), selectedDay, dayOfWeek + "");

        }).showTodayButton(true).init();
        picker.setDate(DateTime.now());

        getMedicationsForToday();

        addBtn.setOnClickListener(view12 -> floatingButtonUI());

        addMedicationBtn.setOnClickListener(view1 -> {
            if (outerNavController.getCurrentDestination().getId() == R.id.mainFragment) {
                outerNavController.navigate(R.id.action_mainFragment_to_navigation);
            }
        });

        addTrackerBtn.setOnClickListener(view1 -> {
            if (outerNavController.getCurrentDestination().getId() == R.id.mainFragment) {
                outerNavController.navigate(R.id.addMedFriendImpl);
            }
        });

    }

    private void initUI() {
        outerNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        homeRecyclerView = binding.homeRecycler;
        layoutManager = new LinearLayoutManager(getActivity());

        picker = (HorizontalPicker) binding.datePicker;

        addBtn = binding.addBtn;
        addMedicationBtn = binding.addMedicationBtn;
        addTrackerBtn = binding.addTrackerBtn;

        addMedicationTxt = binding.addMedicationTxt;
        addTrackerTxt = binding.addTrackerTxt;

        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom);
        mainOpen = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open);
        mainClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close);

        isOpened = false;
        if(!presenter.isOwnerUser()){
        binding.addBtn.setVisibility(View.GONE);}
    }

    void getMedicationsForToday() {
        presenter
                .getMyMedicationsForDay(user.getId(), selectedDay, dayOfWeek + "")
                .observe(getViewLifecycleOwner(), medications -> {
                    mediList.clear();
                    Log.i("USERRRR", "onChanged: " + medications.size() + " " + user.getId());

                    for (Medication medication : medications) {
                        Log.i("TAG", Converters.dateToTimestamp(medication.getStartDate()).toString());
                        Log.i("TAG", Converters.dateToTimestamp(medication.getEndDate()).toString());
                        for (Reminder r : medication.getRemindersID()) {

                            String remTime = r.hours + ":" + r.minutes;
                            Log.i("TAG", r.hours + ":" + r.minutes);

                            MedicationHome medicationHome = new MedicationHome(medication.getName(),
                                    r.hours + ":" + r.minutes + ", ",
                                    r.status + "",
                                    medication.getRefillReminder().currentNumOfPills + "",
                                    medication.getStrengthValue() + " g, take " + r.drugQuantity + " pill(s)",
                                    medication.getIcon());
                            Log.i("TAG", medication.getName());

                            if (mediList.get(remTime) == null) {
                                List<MedicationHome> mediItemList = new ArrayList<>();
                                mediItemList.add(medicationHome);
                                mediList.put(remTime, mediItemList);
                            } else {
                                mediList.get(remTime).add(medicationHome);
                            }
                        }
                    }
                    homeAdapter = new HomeAdapter(getActivity(),reminderDate);
                    homeAdapter.setMedicationArray(mediList);
                    homeRecyclerView.setAdapter(homeAdapter);
                    homeRecyclerView.setLayoutManager(layoutManager);
                    homeAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

    }

    public void floatingButtonUI() {
        if (isOpened) {
            mainClose.setDuration(200);
            fabClose.setDuration(200);

            addBtn.startAnimation(mainClose);

            addMedicationBtn.startAnimation(fabClose);
            addMedicationTxt.startAnimation(fabClose);
            addTrackerBtn.startAnimation(fabClose);
            addTrackerTxt.startAnimation(fabClose);

            addMedicationBtn.setVisibility(View.INVISIBLE);
            addMedicationTxt.setVisibility(View.INVISIBLE);
            addTrackerBtn.setVisibility(View.INVISIBLE);
            addTrackerTxt.setVisibility(View.INVISIBLE);

            isOpened = false;
        } else {
            mainOpen.setDuration(200);
            fabOpen.setDuration(200);

            addBtn.startAnimation(mainOpen);

            addMedicationBtn.startAnimation(fabOpen);
            addMedicationTxt.startAnimation(fabOpen);
            addTrackerBtn.startAnimation(fabOpen);
            addTrackerTxt.startAnimation(fabOpen);

            addMedicationBtn.setVisibility(View.VISIBLE);
            addMedicationTxt.setVisibility(View.VISIBLE);
            addTrackerBtn.setVisibility(View.VISIBLE);
            addTrackerTxt.setVisibility(View.VISIBLE);

            isOpened = true;
        }
    }

    @Override
    public void notifyDataChanged() {
        user = presenter.getUser();
        presenter.setDateChange(user.getId(), selectedDay, dayOfWeek + "");
        picker.setDate(DateTime.now());
        Log.e(TAG, "notifyDataChanged: ");
    }
}