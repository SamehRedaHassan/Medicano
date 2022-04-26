package com.iti.java.medicano.medictiondetailsfragment.view;

import static com.iti.java.medicano.Constants.POSITION;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;
import static com.iti.java.medicano.utils.BundleKeys.REMINDER;
import static android.content.Context.MODE_PRIVATE;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentMedicationInfoBinding;
import com.iti.java.medicano.medictiondetailsfragment.presenter.MedicationDetailsPresenter;
import com.iti.java.medicano.medictiondetailsfragment.presenter.MedicationDetailsPresenterImpl;
import com.iti.java.medicano.model.Medication;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.WorkManager;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.utils.NavigationHelper;

public class MedicationDetailsFragment extends Fragment implements MedicationDetailsView {

    private FragmentMedicationInfoBinding binding;
    private NavController navController;
    private Medication med;
    private MedicationDetailsPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMedicationInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        med = getArguments().getParcelable(MEDICATION_BUILDER);
        presenter = new MedicationDetailsPresenterImpl(this, MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                FirebaseDatabase.getInstance(),
                FirebaseAuth.getInstance().getUid(),WorkManager.getInstance(getContext().getApplicationContext())), UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)));
        navController = NavHostFragment.findNavController(this);
        setListeners();
        configureUI();
    }

    private void setListeners() {
        binding.btnEdit.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MEDICATION_BUILDER, med);
            NavigationHelper.safeNavigateTo(navController, R.id.medicationDetailsFragment, R.id.action_medicationDetailsFragment_to_editMedicationFragment, bundle);
        });
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
        binding.btnSuspend.setText(med.status == 1 ? "SUSPEND" : "RESUME");
        binding.textviewNoOfPillsLeft.setText("No Of Pills Left " + "( " + med.getRefillReminder().currentNumOfPills + " )");
        binding.textviewNoOfPillsInOneRefill.setText("No Of Pills in One Refill " + "( " + "10" + " )");
        binding.textviewNoOfPillsToLaunchReminder.setText("No Of Pills to Lauch Reminder " + "( " + med.getRefillReminder().countToReminderWhenReach + " )");
        if(!presenter.isOwnerUser()){

            binding.btnRefill.setVisibility(View.GONE);
            binding.btnSuspend.setVisibility(View.GONE);
        }else{
            binding.btnRefill.setVisibility(View.VISIBLE);
            binding.btnSuspend.setVisibility(View.VISIBLE);
        }
        binding.btnRefill.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(POSITION,1);
            navController.navigate(R.id.action_medicationDetailsFragment_to_refillDialogFragment,bundle);

         
//             med.getRefillReminder().currentNumOfPills =  med.getRefillReminder().currentNumOfPills + 10 ;
//             presenter.updateMedication(med);
//             Toast.makeText(getContext(), "Refilled successfully", Toast.LENGTH_SHORT).show();


        });

        binding.btnSuspend.setOnClickListener(view -> {
            if (med.status == 0) {
                med.status = 1;
                binding.btnSuspend.setText("Suspend");
            } else {
                med.status = 0;
                binding.btnSuspend.setText("Activate");
                WorkManager.getInstance(getContext().getApplicationContext()).cancelAllWorkByTag(med.getId());
            }
            presenter.updateMedication(med);

            binding.btnSuspend.setText( med.status == 1 ? "SUSPEND" : "RESUME");


            //presenter.updateMid

            //suspend medication
            //updateMedication
        });
        if (navController.getCurrentBackStackEntry() != null) {
            SavedStateHandle savedStateHandler = navController.getCurrentBackStackEntry().getSavedStateHandle();
            savedStateHandler.getLiveData(REMINDER).removeObservers(getViewLifecycleOwner());
            savedStateHandler.getLiveData(REMINDER).observe(getViewLifecycleOwner(), (valueOfRefill) -> {
                 float v = (float) valueOfRefill;
                new Thread(() -> {
                    med.getRefillReminder().currentNumOfPills += v;
                    med.needsToRefill = med.getRefillReminder().currentNumOfPills <= med.getRefillReminder().countToReminderWhenReach;
                    presenter.editMedication(med);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        binding.textviewNoOfPillsLeft.setText("No Of Pills Left " + "( " + med.getRefillReminder().currentNumOfPills + " )");
                    });
                }).start();

            });
        }
        binding.btnDelete.setOnClickListener(view -> {
            //delete Medication
            presenter.deleteMedication(med);
            navController.navigate(R.id.action_medicationDetailsFragment_to_mainFragment2);
        });
        binding.btnBack.setOnClickListener(view -> navController.navigateUp());

    }

}
