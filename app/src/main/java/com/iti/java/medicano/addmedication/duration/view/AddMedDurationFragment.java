package com.iti.java.medicano.addmedication.duration.view;

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

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.duration.presenter.AddMedDurationPresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationDurationBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.utils.NavigationHelper;

import java.util.Calendar;
import java.util.Date;

public class AddMedDurationFragment extends Fragment implements AddMedDurationView {
    private FragmentMedicationDurationBinding binding;
    private NavController navController;
    private AddMedDurationPresenterImpl addMedDurationPresenter;
    private Date startDate;
    private Date endDate;
    private static final String TAG = "AddMedDurationFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationDurationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        addMedDurationPresenter = new AddMedDurationPresenterImpl(this);
        return view.getRootView();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
        setObservers();
    }

    private void setObservers() {
        if (navController.getCurrentBackStackEntry() != null) {
            SavedStateHandle savedStateHandler = navController.getCurrentBackStackEntry().getSavedStateHandle();
            savedStateHandler.getLiveData(START_DATE).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(START_DATE).observe(getViewLifecycleOwner(), (date) -> {
                if (date instanceof Calendar) {
                    startDate = ((Calendar) date).getTime();
                    binding.editTextStartDate.setText(startDate.toString());

                }
            });
            savedStateHandler.getLiveData(END_DATE).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(END_DATE).observe(getViewLifecycleOwner(), (date) -> {
                if (date instanceof Calendar) {
                    endDate = ((Calendar) date).getTime();
                    binding.editTextEndDate.setText(endDate.toString());

                }
            });
        }
    }

    private void setListeners() {
        binding.editTextEndDate.setOnClickListener((v)->{
            Bundle bundle = new Bundle();
            bundle.putString(DATE_TYPE,END_DATE);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedDurationFragment,R.id.action_addMedDurationFragment_to_datePickerDialogFragment, bundle);
        });
        binding.editTextStartDate.setOnClickListener((v)->{
            Bundle bundle = new Bundle();
            bundle.putString(DATE_TYPE,START_DATE);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedDurationFragment,R.id.action_addMedDurationFragment_to_datePickerDialogFragment,bundle);
        });
        binding.btnNext.setOnClickListener((v)->{
            addMedDurationPresenter.onPressNext(startDate, endDate);
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void sendError(int msgRes) {
        Toast.makeText(getContext(), getText(msgRes) , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void next() {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setEndDate(endDate);
            builder.setStartDate(startDate);
            Bundle bundle = new Bundle();
            Log.e(TAG, "next: bundle"+builder);
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedDurationFragment,R.id.action_addMedDurationFragment_to_addMedPreInstruction,bundle);
        } else {
            Toast.makeText(getContext(), this.getText(R.string.please_restart_process), Toast.LENGTH_SHORT).show();
        }
    }
}
