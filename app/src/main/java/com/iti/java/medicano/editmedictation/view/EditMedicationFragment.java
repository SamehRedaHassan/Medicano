package com.iti.java.medicano.editmedictation.view;

import static com.iti.java.medicano.utils.BundleKeys.REMINDER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.remiders.view.AddRemindersAdapter;
import com.iti.java.medicano.databinding.FragmentEditMedicationBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.utils.NavigationHelper;
import com.iti.java.medicano.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class EditMedicationFragment extends Fragment implements EditMedicationView {
    private FragmentEditMedicationBinding binding;
    private NavController navController;
    private final List<Reminder> reminders = new ArrayList<>();
    private AddRemindersAdapter addRemindersAdapter;

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
        setupLayout();
        setListeners();
        setObservers();
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
        }
    }

    private void setListeners() {
        binding.btnExpandCollapseInstruction.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseInstruction,binding.instructionView);
        });
        binding.btnExpandCollapseReason.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseReason,binding.reasonView);
        });
        binding.btnExpandCollapseRefill.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseRefill,binding.refillReminderView);
        });
        binding.btnExpandCollapseReminders.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseReminders,binding.reminderView);
        });
        binding.btnExpandCollapseSchedule.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseSchedule,binding.scheduleView);
        });
        binding.btnExpandIcon.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandIcon,binding.iconView);
        });
        binding.btnExpandCollapseStrength.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseStrength,binding.strengthView);
        });
        binding.btnExpandCollapseName.setOnClickListener((v)->{
            UIHelper.flipBtnAndChangeLayoutVisibility(binding.btnExpandCollapseName,binding.emailTxtview);
        });
        binding.btnDone.setOnClickListener((v)->{
            Medication.Builder builder = new Medication.Builder(binding.editTextMedicationName.getText().toString());
        });
        binding.btnAddReminder.setOnClickListener((v)->{
            NavigationHelper.safeNavigateTo(navController, R.id.editMedicationFragment,R.id.action_editMedicationFragment_to_reminderPickerDialogFragment2);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
