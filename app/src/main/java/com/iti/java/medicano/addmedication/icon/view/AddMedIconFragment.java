package com.iti.java.medicano.addmedication.icon.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.java.medicano.databinding.FragmentMedicationIconBinding;

public class AddMedIconFragment extends Fragment {
    private FragmentMedicationIconBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationIconBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view.getRootView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
