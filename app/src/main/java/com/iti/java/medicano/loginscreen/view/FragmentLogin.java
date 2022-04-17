package com.iti.java.medicano.loginscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentLoginBinding;
import com.iti.java.medicano.databinding.FragmentRegisterBinding;
import com.iti.java.medicano.loginscreen.presenter.LoginPresenter;
import com.iti.java.medicano.loginscreen.presenter.LoginPresenterInterface;
import com.iti.java.medicano.registerscreen.presenter.RegisterPresenterInterface;

public class FragmentLogin extends Fragment {

    private FragmentLoginBinding binding;

    private LoginPresenterInterface presenter;

    private EditText edtUserEmail,edtUserPassword;
    private Button btnLogin;

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LoginPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtUserEmail = binding.edtUserEmail;
        edtUserPassword = binding.edtUserPassword;
        btnLogin = binding.btnLogin;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validator.validateLogin
                presenter.loginUser(edtUserEmail.getText().toString(),edtUserPassword.getText().toString());
            }
        });


    }
}