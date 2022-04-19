package com.iti.java.medicano.addmedication.refill.view;

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
import com.iti.java.medicano.addmedication.refill.presenter.AddMedRefillPresenter;
import com.iti.java.medicano.addmedication.refill.presenter.AddMedRefillPresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationRefillReminderBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.RefillReminder;
import com.iti.java.medicano.utils.NavigationHelper;

public class AddMedRefillFragment extends Fragment implements AddMedRefillView{
    private FragmentMedicationRefillReminderBinding binding;
    private NavController navController;
    private AddMedRefillPresenter addMedRefillPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationRefillReminderBinding.inflate(getLayoutInflater());
        addMedRefillPresenter = new AddMedRefillPresenterImpl(this);
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
            if(binding.editTextCurrentNumOfPills.getText()!=null && binding.editTextRemindMeWhenReach.getText()!=null)
                addMedRefillPresenter.onPressNext(binding.editTextCurrentNumOfPills.getText().toString(),binding.editTextRemindMeWhenReach.getText().toString());
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void sendError(int msgRes) {
        Toast.makeText(getContext(), getText(msgRes), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void next(int valCurrNumOfPills, int valWhenReach) {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setRefillReminder(new RefillReminder(valCurrNumOfPills,valWhenReach));
            Bundle bundle = new Bundle();
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedRefillFragment, R.id.action_addMedRefillFragment_to_addMedIconFragment, bundle);
        } else {
            Toast.makeText(getContext(), this.getText(R.string.please_restart_process), Toast.LENGTH_SHORT).show();
        }
    }
}
