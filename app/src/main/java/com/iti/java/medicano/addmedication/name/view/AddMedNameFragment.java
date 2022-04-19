package com.iti.java.medicano.addmedication.name.view;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.iti.java.medicano.addmedication.name.presenter.AddMedNamePresenter;
import com.iti.java.medicano.addmedication.name.presenter.AddMedNamePresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationNameBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.NavigationHelper;
import com.iti.java.medicano.utils.UIHelper;

public class AddMedNameFragment extends Fragment implements AddMedNameView{
    private FragmentMedicationNameBinding binding;
    private NavController navController;
    private AddMedNamePresenter addMedNamePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationNameBinding.inflate(getLayoutInflater());
        addMedNamePresenter = new AddMedNamePresenterImpl(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener((v)->{
            navController.navigateUp();
        });
        binding.btnNext.setOnClickListener((v)->{
            addMedNamePresenter.onPressNext(binding.editTextMedicationName.getText().toString());
        });
        UIHelper.removeErrorFromEditTextOnTextChange(binding.editTextMedicationName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void showError(int msg) {
        UIHelper.sendEditTextError(binding.editTextMedicationName,this.getText(msg).toString());
        //Toast.makeText(getContext(), this.getText(msg).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void next(String name) {
        Bundle bundle = new Bundle();
        Medication.Builder builder = new Medication.Builder(name);
        bundle.putParcelable(MEDICATION_BUILDER,builder);
        NavigationHelper.safeNavigateTo(navController, R.id.addMedNameFragment,R.id.action_addMedNameFragment_to_addMedFormFragment, bundle);
    }
}
