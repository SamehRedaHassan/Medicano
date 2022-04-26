package com.iti.java.medicano.editmedictation.view;

import static com.iti.java.medicano.utils.BundleKeys.DATE_TYPE;
import static com.iti.java.medicano.utils.BundleKeys.END_DATE;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;
import static com.iti.java.medicano.utils.BundleKeys.REMINDER;
import static com.iti.java.medicano.utils.BundleKeys.START_DATE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.remiders.view.AddRemindersAdapter;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentEditMedicationBinding;
import com.iti.java.medicano.model.MedPreInstructions;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.RefillReminder;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.utils.MyDateUtils;
import com.iti.java.medicano.utils.NavigationHelper;
import com.iti.java.medicano.utils.UIHelper;
import com.iti.java.medicano.work.WorkersHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditMedicationFragment extends Fragment implements EditMedicationView {
    private FragmentEditMedicationBinding binding;
    private NavController navController;
    private final List<Reminder> reminders = new ArrayList<>();
    private AddRemindersAdapter addRemindersAdapter;
    private Date startDate;
    private Date endDate;
    private Medication medication;
    private MedicationRepo medRepo;
    private static final String TAG = "EditMedicationFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditMedicationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        medication = (Medication) getArguments().getParcelable(MEDICATION_BUILDER);
        setupLayout();
        setListeners();
        setObservers();

        medRepo = MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(), FirebaseDatabase.getInstance(),medication.getUserId() ,WorkManager.getInstance(getActivity().getApplicationContext()));
    }

    private void setupLayout() {
        addRemindersAdapter = new AddRemindersAdapter(reminders);
        binding.rvReminders.setHasFixedSize(true);
        binding.rvReminders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvReminders.setAdapter(addRemindersAdapter);
    }

    private void setObservers() {
        if (navController.getCurrentBackStackEntry() != null) {
            SavedStateHandle savedStateHandler = navController.getCurrentBackStackEntry().getSavedStateHandle();
            savedStateHandler.getLiveData(REMINDER).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(REMINDER).observe(getViewLifecycleOwner(), (reminder) -> {
                if (reminder instanceof Reminder) {
                    reminders.add((Reminder) reminder);
                    addRemindersAdapter.notifyDataSetChanged();
                }
            });
            savedStateHandler.getLiveData(START_DATE).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(START_DATE).observe(getViewLifecycleOwner(), (date) -> {
                if (date instanceof Calendar) {
                    startDate = ((Calendar) date).getTime();
                    startDate = MyDateUtils.truncateToDate(startDate);
                    binding.editTextStartDate.setText(startDate.toString());
                    endDate = null;
                    binding.editTextEndDate.setText("");
                }
            });
            savedStateHandler.getLiveData(END_DATE).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(END_DATE).observe(getViewLifecycleOwner(), (date) -> {
                if (date instanceof Calendar) {
                    endDate = ((Calendar) date).getTime();
                    endDate = MyDateUtils.getLastTime(endDate);
                    binding.editTextEndDate.setText(endDate.toString());
                }
            });
        }

    }

    private void setListeners() {
        binding.btnDone.setOnClickListener((v) -> {
            Log.e(TAG, "setListeners: binding.btnDone.setOnClickListener((v)");
            Medication.Builder builder = new Medication
                    .Builder(binding.editTextMedicationName.getText().toString().isEmpty() ? medication.getName() : binding.editTextMedicationName.getText().toString())
                    .setReminderId(reminders.size() == 0 ? medication.getRemindersID() : reminders)
                    .setStartDate(startDate == null ? medication.getStartDate() : startDate)
                    .setEndDate(endDate == null ? medication.getEndDate() : endDate)
                    .setIcon(UIHelper.getIconFromPosition(binding.iconTabLayout.getSelectedTabPosition()))
                    .setReasonForMedication(binding.editTextMedicationReason.getText().toString().isEmpty() ? medication.getName() : binding.editTextMedicationReason.getText().toString())
                    .setStrengthType(binding.strengthUnitTabLayout.getSelectedTabPosition())
                    .setStrengthValue(binding.editTextMedicationStrength.getText().toString().isEmpty() ? medication.getStrengthValue() : Float.parseFloat(binding.editTextMedicationStrength.getText().toString()))
                    .setTreatmentTime(getTimeConstantFromID(binding.radioGroup.getCheckedRadioButtonId()))
                    .setInstruction(binding.editTextMedicationInstructions.getText().toString().isEmpty() ? medication.getInstruction() : binding.editTextMedicationInstructions.getText().toString())
                    .setRefillReminder((binding.editTextNotifyMeWhenReach.getText().toString().isEmpty() || binding.editTextNotifyMeWhenReach.getText().toString().isEmpty()) ? medication.getRefillReminder() : new RefillReminder(Integer.parseInt(binding.editTextCurrentAmount.getText().toString()), Integer.parseInt(binding.editTextNotifyMeWhenReach.getText().toString())));
            Medication med =builder.build();
            med.status = medication.status;
            med.setDays(medication.getDays());
            med.setId(medication.getId());
            med.setUserId(medication.getUserId());
            med.needsToRefill = medication.needsToRefill;

            WorkManager.getInstance(getContext().getApplicationContext()).cancelAllWorkByTag(medication.getId());
            new Thread(()-> WorkersHandler.loopOnMedicationReminders(med,WorkManager.getInstance(getContext().getApplicationContext()))).start();
            medRepo.editMedication(med);
            NavigationHelper.safeNavigateTo(navController,R.id.editMedicationFragment,R.id.action_editMedicationFragment_to_mainFragment);
        });
        binding.editTextEndDate.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString(DATE_TYPE, END_DATE);
            if (startDate != null) {
                bundle.putLong(START_DATE, startDate.getTime());
                NavigationHelper.safeNavigateTo(navController, R.id.editMedicationFragment, R.id.action_editMedicationFragment_to_datePickerDialogFragment2, bundle);
            } else
                Toast.makeText(getContext(), "Set start date first", Toast.LENGTH_SHORT).show();
        });
        binding.editTextStartDate.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString(DATE_TYPE, START_DATE);
            NavigationHelper.safeNavigateTo(navController, R.id.editMedicationFragment, R.id.action_editMedicationFragment_to_datePickerDialogFragment2, bundle);

        });
        binding.btnExpandCollapseInstruction.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseInstruction, binding.instructionView);
        });
        binding.btnExpandCollapseReason.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseReason, binding.reasonView);
        });
        binding.btnExpandCollapseRefill.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseRefill, binding.refillReminderView);
        });
        binding.btnExpandCollapseReminders.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseReminders, binding.reminderView);
        });
        binding.btnExpandCollapseSchedule.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseSchedule, binding.scheduleView);
        });
        binding.btnExpandIcon.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandIcon, binding.iconView);
        });
        binding.btnExpandCollapseStrength.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseStrength, binding.strengthView);
        });
        binding.btnExpandCollapseName.setOnClickListener((v) -> {
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseName, binding.emailTxtview);
        });

        binding.btnAddReminder.setOnClickListener((v) -> {
            NavigationHelper.safeNavigateTo(navController, R.id.editMedicationFragment, R.id.action_editMedicationFragment_to_reminderPickerDialogFragment2);
        });
    }

    private int getTimeConstantFromID(int checkedRadioButtonId) {
        if (checkedRadioButtonId == binding.rbAfterEating.getId())
            return MedPreInstructions.AFTER_EATING;
        else if (checkedRadioButtonId == binding.rbBeforeEating.getId())
            return MedPreInstructions.BEFORE_EATING;
        else if (checkedRadioButtonId == binding.rbDoesNotMatter.getId())
            return MedPreInstructions.DOES_NOT_MATTER;
        else
            return MedPreInstructions.WHILE_EATING;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
