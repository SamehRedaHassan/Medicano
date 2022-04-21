package com.iti.java.medicano.loginscreen.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.loginscreen.view.LoginViewInterface;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.UserRepo;

public class LoginPresenter implements LoginPresenterInterface {

    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    private final FirebaseDatabase database;
    DatabaseReference ref;
    private LoginViewInterface loginView;
    private UserRepo repo;


    public LoginPresenter(LoginViewInterface view,UserRepo repo){
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        this.loginView = view;
        this.repo = repo;
    }

    public void loginUser(String email,String password) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        //DatabaseReference usersRef = ref.child(Constants.USERS);
                        String userId = mAuth.getUid();
                        ref.child(Constants.USERS).child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                user.setId(userId);
                                insertUser(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    } else {
                        //updateUI(null);
                        Log.i(TAG, "Failed");
                    }
                }
            });


    }

    private void insertUser(User user){
        repo.insertUser(user);
    }

}
