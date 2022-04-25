package com.iti.java.medicano.medictiondetailsfragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentMedicationInfoBinding;
import com.iti.java.medicano.utils.NavigationHelper;

public class MedicationDetailsFragment extends Fragment implements MedicationDetailsView {

    private FragmentMedicationInfoBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMedicationInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setListeners();
    }

    private void setListeners() {
        binding.btnEdit.setOnClickListener((v)->{
            NavigationHelper.safeNavigateTo(navController, R.id.medicationDetailsFragment,R.id.action_medicationDetailsFragment_to_editMedicationFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
