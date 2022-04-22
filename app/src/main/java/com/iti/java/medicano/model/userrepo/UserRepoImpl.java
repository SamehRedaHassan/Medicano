package com.iti.java.medicano.model.userrepo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.UserDAO;

public class UserRepoImpl implements UserRepo{

    private static UserRepoImpl userRepo = null;
    private UserDAO userDAO;

    private FirebaseDatabase firebaseDB;
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    public SharedPreferences mPrefs;

    private UserRepoImpl(UserDAO dao,SharedPreferences mPrefs){
        this.userDAO = dao;
        this.mPrefs = mPrefs;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();
        ref = firebaseDB.getReference();
    }

    public static UserRepoImpl getInstance(UserDAO dao,SharedPreferences mPrefs){
        if(userRepo == null){
            userRepo = new UserRepoImpl(dao,mPrefs);
        }
        return userRepo;
    }

    @Override
    public void registerToFirebase(User user, RegisterCallbackInterface callback) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = firebaseAuth.getUid();
                        User userData = new User(user.getFullName(), user.getEmail(), user.getPassword(), user.getGender());
                        userData.setId(userId);

                        ref.child(Constants.USERS).child(userId).setValue(userData);
                        insertToRoom(user);
                        addToPreferences(user);
                        callback.registeredSuccessfully();
                    } else {
                        callback.registeredFailed();
                    }
                });

    }

    @Override
    public void loginToFirebase(String email, String password, LoginCallbackInterface callback) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String userId = firebaseAuth.getUid();
                        ref.child(Constants.USERS).child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                user.setId(userId);
                                insertToRoom(user);
                                addToPreferences(user);
                                callback.logedinSuccessfully();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });


                    } else {
                        callback.logedinFailed();
                    }
                });

    }

    @Override
    public void insertToRoom(User user) {
        new Thread(()->{
            userDAO.insertUser(user);
        }).start();
    }

    @Override
    public void addToPreferences(User user) {
        //mPrefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(Constants.USER_KEY, json);
        prefsEditor.commit();
    }

    @Override
    public User getPreferences() {
        String json = mPrefs.getString(Constants.USER_KEY, "");
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);

        return user;
    }

}
