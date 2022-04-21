package com.iti.java.medicano.registerscreen.presenter;
import static android.content.ContentValues.TAG;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.registerscreen.view.RegisterViewInterface;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter implements RegisterPresenterInterface{

    private FirebaseAuth mAuth;
    private final FirebaseDatabase database;
    DatabaseReference ref;
    private RegisterViewInterface registerView;
    private UserRepo repo;

    public RegisterPresenter(RegisterViewInterface view,UserRepo repo){
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        this.registerView = view;
        this.repo = repo;
    }

    public void registerUser(User user){

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    User userData = new User(user.getFullName(),user.getEmail(),user.getPassword(),user.getGender());

                    String userId = mAuth.getUid();
                    userData.setId(userId);

                    DatabaseReference usersRef = ref.child(Constants.USERS);
                    usersRef.child(userId).setValue(userData);
                    Log.i(TAG, "registered successfully");

                    insertUser(userData);
                }
                else
                {
                    //System.out.println(task.getResult());
                    Log.i(TAG, "registered failed");
                }
            }
        });
    }

    private void insertUser(User user){
        repo.insertUser(user);
        System.out.println("user inserted successfully in room");
    }

}
