package com.iti.java.medicano.addmedication.reason.view;

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
import com.iti.java.medicano.databinding.FragmentMedicationReasonBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.NavigationHelper;

public class AddMedReasonFragment extends Fragment {
    private FragmentMedicationReasonBinding binding;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationReasonBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.btnNext.setOnClickListener((v)->{
            if (getArguments() != null && binding.editTextMedicationName.getText()!=null) {
                Bundle mBundle = getArguments();
                Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
                builder.setReasonForMedication(binding.editTextMedicationName.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putParcelable(MEDICATION_BUILDER, builder);
                NavigationHelper.safeNavigateTo(navController, R.id.addMedReasonFragment, R.id.action_addMedReasonFragment_to_addMedDaysFragment, bundle);
            } else {
                Toast.makeText(getContext(), this.getText(R.string.please_restart_process), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
