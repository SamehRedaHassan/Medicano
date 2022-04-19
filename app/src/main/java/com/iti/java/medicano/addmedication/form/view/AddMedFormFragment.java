package com.iti.java.medicano.addmedication.form.view;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;

import android.annotation.SuppressLint;
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
import com.iti.java.medicano.databinding.FragmentMedicationFormBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.MedicationForms;
import com.iti.java.medicano.utils.NavigationHelper;

public class AddMedFormFragment extends Fragment implements AddMedFormView, View.OnClickListener {
    private FragmentMedicationFormBinding binding;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.btnSkip.setOnClickListener((v) -> navController.navigateUp());
        binding.textViewPill.setOnClickListener(this);
        binding.textViewSolution.setOnClickListener(this);
        binding.textViewInjection.setOnClickListener(this);
        binding.textViewPowder.setOnClickListener(this);
        binding.textViewDrops.setOnClickListener(this);
        binding.textViewInhaler.setOnClickListener(this);
        binding.textViewOther.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewPill:
                selectForm(MedicationForms.PILL);
                break;
            case R.id.textViewSolution:
                selectForm(MedicationForms.SOLUTION);
                break;
            case R.id.textViewInjection:
                selectForm(MedicationForms.INJECTION);
                break;
            case R.id.textViewPowder:
                selectForm(MedicationForms.POWDER);
                break;
            case R.id.textViewDrops:
                selectForm(MedicationForms.DROPS);
                break;
            case R.id.textViewInhaler:
                selectForm(MedicationForms.IN_HALER);
                break;
            case R.id.textViewOther:
                selectForm(MedicationForms.OTHER);
                break;
        }
    }

    private void selectForm(int form) {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setFormOfMedication(form);
            Bundle bundle = new Bundle();
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedFormFragment, R.id.action_addMedFormFragment_to_addMedStrengthFragment, bundle);
        } else {
            Toast.makeText(getContext(), this.getText(R.string.please_restart_process), Toast.LENGTH_SHORT).show();
        }
    }
}
