package com.iti.java.medicano.loginscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentLoginBinding;
import com.iti.java.medicano.loginscreen.presenter.LoginPresenter;
import com.iti.java.medicano.loginscreen.presenter.LoginPresenterInterface;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.validation.Validation;

public class FragmentLogin extends Fragment implements LoginViewInterface{

    private FragmentLoginBinding binding;
    private LoginPresenterInterface presenter;
    private NavController navController;

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new LoginPresenter(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(), FirebaseDatabase.getInstance()));
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = binding.edtUserEmail.getText().toString().trim();
                String userPassword = binding.edtUserPassword.getText().toString();

                String validateUser = Validation.loginValidation(userEmail,userPassword);
                //validateUser = "valid login";
                if(validateUser.equals("valid login")){
                    presenter.loginUser(userEmail,userPassword);
                    if (navController.getCurrentDestination().getId() == R.id.fragmentLogin)
                        navController.navigate(R.id.action_fragmentLogin_to_mainFragment);
                }
                else{
                    System.out.println(validateUser);
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