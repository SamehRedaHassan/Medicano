package com.iti.java.medicano.addmedication.icon.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.WorkManager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.icon.presenter.AddMedIconPresenter;
import com.iti.java.medicano.addmedication.icon.presenter.AddMedIconPresenterImpl;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentMedicationIconBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.utils.BundleKeys;
import com.iti.java.medicano.utils.MedicationStatus;
import com.iti.java.medicano.utils.UIHelper;

import java.util.UUID;

public class AddMedIconFragment extends Fragment implements AddMedIcon {
    private FragmentMedicationIconBinding binding;
    private NavController navController;
    private AddMedIconPresenter presenter;
    private static final String TAG = "AddMedIconFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationIconBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //TODO change and get ID from shared Prefs. for current user...
        presenter = new AddMedIconPresenterImpl(this,
                MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                        FirebaseDatabase.getInstance(),
                        FirebaseAuth.getInstance().getUid(),
                        WorkManager.getInstance(getContext().getApplicationContext())),
                UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(), getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)));
        return view.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.iconTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(tab.view, "scaleX", 2f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(tab.view, "scaleY", 2f);
                scaleDownX.setDuration(200);
                scaleDownY.setDuration(200);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                scaleDown.start();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(tab.view, "scaleX", 1f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(tab.view, "scaleY", 1f);
                scaleDownX.setDuration(200);
                scaleDownY.setDuration(200);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                scaleDown.start();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null) {
                    Bundle bundle = getArguments();
                    Medication.Builder builder = bundle.getParcelable(BundleKeys.MEDICATION_BUILDER);
                    builder.setStatus(MedicationStatus.ACTIVE);//1 for Active 0 for inactive
                    builder.setIcon(UIHelper.getIconFromPosition(binding.iconTabLayout.getSelectedTabPosition()));
                    Medication medication = builder.build();
                    medication.setId(UUID.randomUUID().toString());
                    presenter.insertMedicationIntoDB(medication);
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void navigateBack() {
        navController.popBackStack(R.id.addMedNameFragment, true);
    }
}
