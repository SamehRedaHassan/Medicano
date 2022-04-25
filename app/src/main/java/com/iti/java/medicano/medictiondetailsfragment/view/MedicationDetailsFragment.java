package com.iti.java.medicano.medictiondetailsfragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentMedicationInfoBinding;
import com.iti.java.medicano.medictiondetailsfragment.presenter.MedicationDetailsPresenter;
import com.iti.java.medicano.model.Medication;

public class MedicationDetailsFragment extends Fragment implements MedicationDetailsView {

    private FragmentMedicationInfoBinding binding;
    private NavController navController;
    private Medication med;
    private MedicationDetailsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMedicationInfoBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        med = getArguments().getParcelable("MEDICATION");
        configureUI();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureUI() {
        binding.textViewMedicationName.setText(med.getName());
        binding.textviewMedicationStrength.setText(med.getStrengthValue() + " " + "g");
        binding.imgviewMedication.setImageResource(med.getIcon());
        binding.textviewHowToUse.setText(med.getInstruction());
        binding.textviewReminderInstructions.setText(med.getReasonForMedication());
        binding.btnSuspend.setText( med.status == 1 ? "SUSPEND" : "RESUME");
        binding.textviewNoOfPillsLeft.setText("No Of Pills Left " +"( "+med.getRefillReminder().currentNumOfPills + " )" );
        binding.textviewNoOfPillsInOneRefill.setText("No Of Pills in One Refill " +"( "+"10" + " )" );
        binding.textviewNoOfPillsToLaunchReminder.setText("No Of Pills to Lauch Reminder " +"( "+ med.getRefillReminder().countToReminderWhenReach + " )" );

        binding.btnRefill.setOnClickListener(view -> {
            med.getRefillReminder().currentNumOfPills =  med.getRefillReminder().currentNumOfPills + 10 ;
            //presenter.update
        });

        binding.btnSuspend.setOnClickListener(view -> {
           if( med.status == 0) {
               med.status = 1;
           }else{med.status = 0;}
           //presenter.updateMid

            //suspend medication
            //updateMedication
        });
        binding.btnDelete.setOnClickListener(view -> {
          //delete Medication
        });
        binding.btnBack.setOnClickListener(view -> navController.navigateUp());
        binding.btnEdit.setOnClickListener(view -> {
            if (navController.getCurrentDestination().getId() == R.id.medicationDetailsFragment){
                Bundle bundle = new Bundle();
                bundle.putParcelable ("MEDICATION", med);
                navController.navigate(R.id.action_medicationDetailsFragment_to_editMedicationFragment,bundle);
            }
        });
    }

}
