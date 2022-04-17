package com.iti.java.medicano.addmedication.remiders.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.java.medicano.databinding.FragmentMedicationAddRemindersBinding;

public class AddMedRemindersFragment extends Fragment {
    private FragmentMedicationAddRemindersBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationAddRemindersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
