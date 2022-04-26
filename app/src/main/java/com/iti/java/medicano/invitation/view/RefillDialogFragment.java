package com.iti.java.medicano.invitation.view;

import static com.iti.java.medicano.Constants.POSITION;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iti.java.medicano.databinding.DialogFragmentRefillBinding;
import com.iti.java.medicano.databinding.FragmentInvitationsBinding;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.utils.BundleKeys;
import com.iti.java.medicano.utils.CheckValue;

public class RefillDialogFragment extends DialogFragment  {
    DialogFragmentRefillBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogFragmentRefillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        binding.btnDone.setOnClickListener((v)->{
            String value =binding.editTextRefill.getText().toString();
            int id =getArguments().getInt(POSITION,-1);
            if (!value.isEmpty() && CheckValue.isNumeric(value) && id!=-1){
                navController.getPreviousBackStackEntry().getSavedStateHandle().set(BundleKeys.REMINDER,Float.parseFloat(value));
                navController.getPreviousBackStackEntry().getSavedStateHandle().set(POSITION,id);
                navController.navigateUp();
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
