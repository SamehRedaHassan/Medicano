package com.iti.java.medicano.registerscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentRegisterBinding;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.registerscreen.presenter.RegisterPresenter;
import com.iti.java.medicano.registerscreen.presenter.RegisterPresenterInterface;
import com.iti.java.medicano.validation.Validation;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class FragmentRegister extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterPresenterInterface presenter;

    //private EditText edtUserName,edtUserEmailReg,edtUserPasswordReg,edtUserConfirmReg;
    //private RadioButton genderMale,genderFemale;
    //private Button btnRegister;

    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String userName = binding.edtUserName.getText().toString().trim();
        String userEmail = binding.edtUserEmailReg.getText().toString().trim();
        String userPassword = binding.edtUserPasswordReg.getText().toString();
        String confirmPassword = binding.edtUserConfirmReg.getText().toString();
        int userGender = binding.genderMale.isChecked()? 0:1;

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate inputs
                String validateUser = Validation.registerValidation(userName,userEmail,userPassword,confirmPassword);

                if(validateUser.equals("valid registeration")){
                    User user = new User(userName,userEmail,userPassword,userGender);
                    presenter.registerUser(user);
                }
                else{
                    System.out.println(validateUser);
                }
            }
        });

    }
}