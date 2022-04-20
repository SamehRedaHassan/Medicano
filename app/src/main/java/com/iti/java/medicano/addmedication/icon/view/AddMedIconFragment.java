package com.iti.java.medicano.addmedication.icon.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.iti.java.medicano.addmedication.icon.presenter.AddMedIconPresenter;
import com.iti.java.medicano.addmedication.icon.presenter.AddMedIconPresenterImpl;
import com.iti.java.medicano.addmedication.repo.AddMedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentMedicationIconBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.utils.BundleKeys;
import com.iti.java.medicano.utils.SharedPrefKeys;

import java.util.UUID;

public class AddMedIconFragment extends Fragment implements AddMedIcon {
    private FragmentMedicationIconBinding binding;
    private NavController navController;
    private AddMedIconPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMedicationIconBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        presenter = new AddMedIconPresenterImpl(this, AddMedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(), FirebaseDatabase.getInstance()));
        return view.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null) {
                    Bundle bundle = getArguments();
                    Medication.Builder builder = bundle.getParcelable(BundleKeys.MEDICATION_BUILDER);
                    //switch case to determine the icon
                    builder.setStatus(1);//1 for Active 0 for inactive
                    builder.setIcon(0);
                    Medication medication = builder.build();
//                    String stringUser = getContext().getSharedPreferences(SharedPrefKeys.SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(SharedPrefKeys.OWNER_USER, "");
//                    Gson gson = new Gson();
//                    User user = gson.fromJson(stringUser,User.class);
                   // medication.setUserId(user.getEmail());
                    medication.setUserId("SAm");
                    medication.setId(UUID.randomUUID().toString());


                    //presenter -> Repo -> RoomDB
                    //                  -> FireBase
                    presenter.insertMedicationIntoDB(medication);
//                    Bundle myBundle = new Bundle();
//                    myBundle.putParcelable(BundleKeys.MEDICATION_BUILDER,builder);

                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
