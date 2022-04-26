package com.iti.java.medicano.addmedication.remiders.view;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;
import static com.iti.java.medicano.utils.BundleKeys.REMINDER;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.remiders.presenter.AddMedRemindersPresenter;
import com.iti.java.medicano.addmedication.remiders.presenter.AddMedRemindersPresenterImpl;
import com.iti.java.medicano.databinding.FragmentMedicationAddRemindersBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.utils.NavigationHelper;
import java.util.ArrayList;
import java.util.List;

public class AddMedRemindersFragment extends Fragment implements AddMedRemindersView {
    private FragmentMedicationAddRemindersBinding binding;
    private NavController navController;
    private AddMedRemindersPresenter addMedRemindersPresenter;
    private AddRemindersAdapter addRemindersAdapter;
    private final List<Reminder> reminders = new ArrayList<>();
    private static final String TAG = "AddMedRemindersFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationAddRemindersBinding.inflate(getLayoutInflater());
        addMedRemindersPresenter = new AddMedRemindersPresenterImpl(this);
        addRemindersAdapter = new AddRemindersAdapter(reminders);
        binding.rvAddedReminders.setHasFixedSize(true);
        binding.rvAddedReminders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvAddedReminders.setAdapter(addRemindersAdapter);
        return binding.getRoot();
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
            savedStateHandler.getLiveData(REMINDER).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(REMINDER).observe(getViewLifecycleOwner(), (reminder) -> {
                if (reminder instanceof Reminder) {
                    reminders.add((Reminder) reminder);
                    addRemindersAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void setListeners() {
        binding.fabAddReminder.setOnClickListener((v) -> {
            NavigationHelper.safeNavigateTo(navController, R.id.addMedRemindersFragment,R.id.action_addMedRemindersFragment_to_reminderPickerDialogFragment);
           // Toast.makeText(getContext(), "ddddddddddddd", Toast.LENGTH_SHORT).show();
        });
        binding.btnNext.setOnClickListener((v)->{
            addMedRemindersPresenter.onPressNext(reminders);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void next() {
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            Medication.Builder builder = mBundle.getParcelable(MEDICATION_BUILDER);
            builder.setReminderId(reminders);
            Bundle bundle = new Bundle();
            Log.e(TAG, "next: bundle"+builder);
            bundle.putParcelable(MEDICATION_BUILDER, builder);
            NavigationHelper.safeNavigateTo(navController, R.id.addMedRemindersFragment, R.id.action_addMedRemindersFragment_to_addMedDurationFragment, bundle);
        } else {
            Toast.makeText(getContext(), this.getText(R.string.please_restart_process), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendError(int msgRes) {
        Toast.makeText(getContext(), getText(msgRes), Toast.LENGTH_LONG).show();
    }
}
