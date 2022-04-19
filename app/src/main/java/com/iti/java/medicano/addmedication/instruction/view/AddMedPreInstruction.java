package com.iti.java.medicano.addmedication.instruction.view;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentMedicationPreInstructionBinding;
import com.iti.java.medicano.model.MedPreInstructions;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.NavigationHelper;

public class AddMedPreInstruction extends Fragment {
    private FragmentMedicationPreInstructionBinding binding;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationPreInstructionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view.getRootView();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.textViewAfterEating.setOnClickListener((v)->setPreInstruction(MedPreInstructions.AFTER_EATING));
        binding.textViewBeforeEating.setOnClickListener((v)->setPreInstruction(MedPreInstructions.BEFORE_EATING));
        binding.textViewDoseNotMatter.setOnClickListener((v)->setPreInstruction(MedPreInstructions.DOES_NOT_MATTER));
        binding.textViewWhileEating.setOnClickListener((v)->setPreInstruction(MedPreInstructions.WHILE_EATING));
    }

    private void setPreInstruction(int treatmentTime) {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setTreatmentTime(treatmentTime);
            Bundle bundle = new Bundle();
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedPreInstruction, R.id.action_addMedPreInstruction_to_addMedInstOptionalFragment, bundle);
        } else {
            Toast.makeText(getContext(), this.getText(R.string.please_restart_process), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
