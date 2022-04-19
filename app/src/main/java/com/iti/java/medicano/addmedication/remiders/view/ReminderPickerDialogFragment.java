package com.iti.java.medicano.addmedication.remiders.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.iti.java.medicano.addmedication.remiders.presenter.ReminderPickerDialogPresenter;
import com.iti.java.medicano.addmedication.remiders.presenter.ReminderPickerDialogPresenterImpl;
import com.iti.java.medicano.databinding.DialogFragmentReminderPickerBinding;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.utils.BundleKeys;
import com.iti.java.medicano.utils.UIHelper;

public class ReminderPickerDialogFragment extends DialogFragment implements  ReminderPickerDialogView {

    private DialogFragmentReminderPickerBinding binding;
    private NavController navController;
    private ReminderPickerDialogPresenter reminderPickerDialogPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogFragmentReminderPickerBinding.inflate(getLayoutInflater());
        reminderPickerDialogPresenter = new ReminderPickerDialogPresenterImpl(this);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.btnSave.setOnClickListener((v)->{
            if (binding.editTextCurrentNumOfPills.getText() != null)
                reminderPickerDialogPresenter.onPressSave(binding.editTextCurrentNumOfPills.getText().toString());
        });
        UIHelper.removeErrorFromEditTextOnTextChange(binding.editTextCurrentNumOfPills);
    }

    @Override
    public void sendError(int msgRes) {
        UIHelper.sendEditTextError(binding.editTextCurrentNumOfPills, getString(msgRes));
    }

    @Override
    public void next(float quantity) {
        navController.getPreviousBackStackEntry().getSavedStateHandle().set(BundleKeys.REMINDER,new Reminder(binding.timePicker.getHour(),binding.timePicker.getMinute(), quantity));
        navController.navigateUp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}
