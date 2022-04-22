package com.iti.java.medicano.loginscreen.view;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new LoginPresenter(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)));
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

                if(validateUser.equals("valid login")){
                    presenter.loginUser(userEmail,userPassword);
                }
                else{
                    Toast.makeText(getContext(), validateUser, Toast.LENGTH_SHORT).show();
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
    public void navigateToHomeScreen() {
        Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
        if (navController.getCurrentDestination().getId() == R.id.fragmentLogin)
            navController.navigate(R.id.action_fragmentLogin_to_mainFragment);
    }

    @Override
    public void showErrorMsg() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
        Log.i("TAG", "showErrorMsg: show error in toast");
    }
}