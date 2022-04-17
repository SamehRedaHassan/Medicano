package com.iti.java.medicano.addmedication.reason.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.java.medicano.databinding.FragmentMedicationNameBinding;
import com.iti.java.medicano.databinding.FragmentMedicationReasonBinding;

public class AddMedReasonFragment extends Fragment {
    private FragmentMedicationReasonBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationReasonBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
