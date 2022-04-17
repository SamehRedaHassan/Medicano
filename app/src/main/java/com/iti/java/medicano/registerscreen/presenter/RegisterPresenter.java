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
import com.iti.java.medicano.model.User;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter implements RegisterPresenterInterface{

    private FirebaseAuth mAuth;
    private final FirebaseDatabase database;
    DatabaseReference ref;

    public RegisterPresenter(){
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();//getReference("server/saving-data/fireblog");
    }

    public void registerUser(User user){

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.i(TAG, "registered successfully");
                    String userId = task.getResult().getUser().getUid();

                    DatabaseReference usersRef = ref.child("users");

                    //Map<String, User> users = new HashMap<>();
                    //users.put(userId, );

                    User userData = new User(user.getFullName(),user.getEmail(),user.getPassword(),user.getGender());

                    usersRef.child(userId).setValue(userData);
                }
                else
                {
                    Log.i(TAG, "registered failed");
                }
            }
        });
    }



}
