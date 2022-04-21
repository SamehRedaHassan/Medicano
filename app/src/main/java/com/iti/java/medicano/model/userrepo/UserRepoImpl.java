package com.iti.java.medicano.model.userrepo;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.UserDAO;

public class UserRepoImpl implements UserRepo{

    private static UserRepoImpl userRepo = null;
    private UserDAO userDAO;
    private FirebaseDatabase firebase;


    private UserRepoImpl(UserDAO dao,FirebaseDatabase db){
        this.userDAO = dao;
        this.firebase = db;
    }

    public static UserRepoImpl getInstance(UserDAO dao,FirebaseDatabase db){
        if(userRepo == null){
            userRepo = new UserRepoImpl(dao,db);
        }
        return userRepo;
    }

    @Override
    public void insertUser(User user) {
        new Thread(()->{
            userDAO.insertUser(user);
        }).start();
    }
}
