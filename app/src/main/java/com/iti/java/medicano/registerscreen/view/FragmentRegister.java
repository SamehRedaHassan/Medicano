package com.iti.java.medicano.registerscreen.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentRegisterBinding;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.registerscreen.presenter.RegisterPresenter;
import com.iti.java.medicano.registerscreen.presenter.RegisterPresenterInterface;
import com.iti.java.medicano.validation.Validation;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class FragmentRegister extends Fragment implements RegisterViewInterface{

    private FragmentRegisterBinding binding;
    private NavController navController;
    private RegisterPresenterInterface presenter;
    public static String userObject = "currentUser";
    public static SharedPreferences mPrefs;

    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new RegisterPresenter(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(), FirebaseDatabase.getInstance()));
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = binding.edtUserName.getText().toString().trim();
                String userEmail = binding.edtUserEmailReg.getText().toString().trim();
                String userPassword = binding.edtUserPasswordReg.getText().toString();
                String confirmPassword = binding.edtUserConfirmReg.getText().toString();
                int userGender = binding.genderMale.isChecked()? 0:1;

                String validateUser = Validation.registerValidation(userName,userEmail,userPassword,confirmPassword);
                //validateUser = "valid registeration";
                if(validateUser.equals("valid registeration")){
                    User user = new User(userName,userEmail,userPassword,userGender);
                    presenter.registerUser(user);

                    mPrefs = getActivity().getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    prefsEditor.putString(userObject, json);
                    prefsEditor.commit();

                    if (navController.getCurrentDestination().getId() == R.id.fragmentRegister)
                        navController.navigate(R.id.action_fragmentRegister_to_mainFragment);
                }
                else{
                    System.out.println(validateUser);
                }
            }
        });

    }
}