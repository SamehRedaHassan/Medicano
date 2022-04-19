package com.iti.java.medicano.addmedication.strength.view;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;

import android.os.Bundle;
import android.util.Log;
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
import com.iti.java.medicano.addmedication.strength.presenter.AddMedStrengthPresenter;
import com.iti.java.medicano.addmedication.strength.presenter.AddMedStrengthPresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationStrengthBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.NavigationHelper;
import com.iti.java.medicano.utils.UIHelper;

public class AddMedStrengthFragment extends Fragment implements AddMedStrengthView {
    private FragmentMedicationStrengthBinding binding;
    private NavController navController;
    private AddMedStrengthPresenter addMedStrengthPresenter;
    private static final String TAG = "AddMedStrengthFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationStrengthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        addMedStrengthPresenter = new AddMedStrengthPresenterImpl(this);
        return view.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.btnNext.setOnClickListener((v) -> {
            if (binding.editTextMedicationStrength.getText() != null)
                addMedStrengthPresenter.onPressNext(binding.editTextMedicationStrength.getText().toString());
        });
        UIHelper.removeErrorFromEditTextOnTextChange(binding.editTextMedicationStrength);
    }

    @Override
    public void sendError(int msgRes) {
        UIHelper.sendEditTextError(binding.editTextMedicationStrength, getString(msgRes));
    }

    @Override
    public void next(float value) {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setStrengthType(binding.strengthUnitTabLayout.getSelectedTabPosition());
            builder.setStrengthValue(value);
            Toast.makeText(getContext(), ""+binding.strengthUnitTabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            Log.e(TAG, "next: bundle"+builder);
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedStrengthFragment, R.id.action_addMedStrengthFragment_to_addMedReasonFragment, bundle);
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

