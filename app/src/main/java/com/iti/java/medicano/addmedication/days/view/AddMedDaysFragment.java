package com.iti.java.medicano.addmedication.days.view;

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
import com.iti.java.medicano.addmedication.days.presenter.AddMedDaysPresenter;
import com.iti.java.medicano.addmedication.days.presenter.AddMedDaysPresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationDaysBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.DaysConst;
import com.iti.java.medicano.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.List;

public class AddMedDaysFragment extends Fragment implements AddMedDaysView {
    private FragmentMedicationDaysBinding binding;
    private NavController navController;
    private List<Integer> days = new ArrayList<>();
    private AddMedDaysPresenter addMedDaysPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationDaysBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        addMedDaysPresenter = new AddMedDaysPresenterImpl(this);
        return view.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.btnNext.setOnClickListener((v) -> addMedDaysPresenter.onPressNext(days));

        binding.saturday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.SATURDAY);
            else days.remove((Object) DaysConst.SATURDAY);
        });
        binding.sunday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.SUNDAY);
            else days.remove((Object) DaysConst.SUNDAY);
        });
        binding.monday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.MONDAY);
            else days.remove((Object) DaysConst.MONDAY);
        });
        binding.tuesday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.TUESDAY);
            else days.remove((Object) DaysConst.TUESDAY);
        });
        binding.wednesday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.WEDNESDAY);
            else days.remove((Object) DaysConst.WEDNESDAY);
        });
        binding.thursday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.THURSDAY);
            else days.remove((Object) DaysConst.THURSDAY);
        });
        binding.friday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) days.add(DaysConst.FRIDAY);
            else days.remove((Object) DaysConst.FRIDAY);
        });
    }


    @Override
    public void sendError(int msgRes) {
        Toast.makeText(getContext(), getText(msgRes), Toast.LENGTH_LONG).show();
    }

    @Override
    public void next() {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setDays(days);
            Bundle bundle = new Bundle();
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedDaysFragment, R.id.action_addMedDaysFragment2_to_addMedRemindersFragment, bundle);
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
