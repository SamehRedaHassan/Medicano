package com.iti.java.medicano.editmedictation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iti.java.medicano.databinding.FragmentEditMedicationBinding;
import com.iti.java.medicano.utils.UIHelper;

public class EditMedicationFragment extends Fragment implements EditMedicationView {
    private FragmentEditMedicationBinding binding;
    private NavController navController;

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
        setListeners();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
