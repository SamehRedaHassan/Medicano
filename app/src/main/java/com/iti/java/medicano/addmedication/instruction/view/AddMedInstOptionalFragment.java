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
import com.iti.java.medicano.addmedication.instruction.presenter.AddMedInstOptionalPresenter;
import com.iti.java.medicano.addmedication.instruction.presenter.AddMedInstOptionalPresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationOptionalInstructionsBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.NavigationHelper;
import com.iti.java.medicano.utils.UIHelper;

public class AddMedInstOptionalFragment extends Fragment implements  AddMedInstOptionalView{
    private FragmentMedicationOptionalInstructionsBinding binding;
    private NavController navController;
    private AddMedInstOptionalPresenter addMedInstOptionalPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationOptionalInstructionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        addMedInstOptionalPresenter = new AddMedInstOptionalPresenterImpl(this);
        return view.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        UIHelper.removeErrorFromEditTextOnTextChange(binding.editTextMedicationOptionalInstructions);
        binding.btnNext.setOnClickListener((v)->{
//            if (binding.editTextMedicationOptionalInstructions.getText()!=null)
//                addMedInstOptionalPresenter.onPressNext(binding.editTextMedicationOptionalInstructions.getText().toString());
//
            if (getArguments() != null) {
                Bundle mBundle = getArguments();
                Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
                builder.setInstruction(binding.editTextMedicationOptionalInstructions.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putParcelable(MEDICATION_BUILDER, builder);
                NavigationHelper.safeNavigateTo(navController, R.id.addMedInstOptionalFragment
                        , R.id.action_addMedInstOptionalFragment_to_addMedRefillFragment, bundle);
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
