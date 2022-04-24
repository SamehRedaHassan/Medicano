package com.iti.java.medicano.model.userrepo;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserRepoImpl implements UserRepo {
    private static final String TAG = "UserRepoImpl";
    private static UserRepoImpl userRepo = null;
    private UserDAO userDAO;
    private FirebaseDatabase firebaseDB;
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    public SharedPreferences mPrefs;
    public final LiveData<HashMap<String,Object>> liveData;
    private final MutableLiveData<HashMap<String,Object>> mutableLiveData = new MutableLiveData<>();


    private UserRepoImpl(UserDAO dao, SharedPreferences mPrefs) {
        this.userDAO = dao;
        this.mPrefs = mPrefs;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();
        ref = firebaseDB.getReference();
        liveData = mutableLiveData;
    }

    public static UserRepoImpl getInstance(UserDAO dao, SharedPreferences mPrefs) {
        if (userRepo == null) {
            userRepo = new UserRepoImpl(dao, mPrefs);
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
                        addOwnerUserToPreferences(user);
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
                                addOwnerUserToPreferences(user);
                                addToPreferences(user);
                                callback.logedinSuccessfully();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                    } else {
                        callback.logedinFailed();
                    }
                });

    }

    @Override
    public void insertToRoom(User user) {
        new Thread(() -> {
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
    //save ownerUser

    @Override
    public void inviteMedFriendWithEmail(String email) {
        ref.child(Constants.USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    User user = userSnap.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        Map<String, Object> updates = new HashMap<String,Object>();
                        updates.put(getPreferences().getId(), getPreferences().getFullName());
                        ref.child("users").child(user.getId()).child("invitationList").updateChildren(updates);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public LiveData<HashMap<String,Object>> listenToMedFriendsInvitations() {
        requestUserInvitations();
        return liveData;
    }


    @Override
    public void acceptMedFriendInvitationWithID(String id, String name) {
        ref.child(Constants.USERS).child(getPreferences().getId()).child("invitationList").child(id).removeValue();
        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put(id,name);
        ref.child(Constants.USERS).child(getPreferences().getId()).child("MedFriends").updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("TAG", "onSuccess: GOOOOD");
                        //samna balady
                        ref.child(Constants.USERS).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                               insertToRoom(user);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", "onFailure: FAAAAIIIILLLED"+e.toString());
                    }
                });

    }

    @Override
    public void DenyMedFriendInvitationWithID(String id) {
        ref.child(Constants.USERS).child(getPreferences().getId()).child("invitationList").child(id).removeValue();
    }


    @Override
    public void getUserFromFirebaseAndSaveToRoom(String id) {
        ref.child(Constants.USERS).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                insertToRoom(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    @Override
    public LiveData<List<User>> getLocalUsersFromRoom() {
        ref.child(Constants.USERS).child(getPreferences().getId()).child("MedFriends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> friend = (HashMap<String, Object>) dataSnapshot.getValue();
                if(friend != null && friend.keySet() != null){
                    Set<String>  s = friend.keySet();
                    for (String se:s) {
                        getUserFromFirebaseAndSaveToRoom(se);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return userDAO.getAllUsers();
    }

    @Override
    public void addOwnerUserToPreferences(User user) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(Constants.OWNER_USER_KEY, json);
        prefsEditor.commit();
    }

    @Override
    public User getOwnerUserPreferences() {
        String json = mPrefs.getString(Constants.OWNER_USER_KEY, "");
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        return user;
    }

    @Override
    public void requestUserInvitations() {
        ref.child(Constants.USERS).child(getPreferences().getId()).child("invitationList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> invitor = (HashMap<String, Object>) snapshot.getValue();
                mutableLiveData.setValue(invitor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ",error.toException() );
            }
        });
    }

}
