package com.iti.java.medicano.loginscreen.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginPresenterInterface {

    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;

    public LoginPresenter(){

    }
    public void loginUser(String email,String password) {
        mAuth=FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, user.getEmail()+" "+user.getDisplayName());
                            //updateUI(user);

                        } else {
                            //updateUI(null);
                            Log.i(TAG, "Failed");
                        }
                    }
                });
    }

}
