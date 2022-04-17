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

public class FragmentRegister extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterPresenterInterface presenter;

    private EditText edtUserName,edtUserEmailReg,edtUserPasswordReg,edtUserConfirmReg;
    private RadioButton genderMale,genderFemale;
    private Button btnRegister;

    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new RegisterPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtUserName = binding.edtUserName;
        edtUserEmailReg = binding.edtUserEmailReg;
        edtUserPasswordReg = binding.edtUserPasswordReg;
        edtUserConfirmReg = binding.edtUserConfirmReg;

        genderMale = binding.genderMale;
        genderFemale = binding.genderFemale;

        btnRegister = binding.btnRegister;


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate inputs

                User user = new User(edtUserName.getText().toString().trim()
                        ,edtUserEmailReg.getText().toString().toString().trim()
                        ,edtUserPasswordReg.getText().toString().trim()
                        ,genderMale.isChecked()? 1:0);

                presenter.registerUser(user);

            }
        });

    }
}