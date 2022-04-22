package com.iti.java.medicano.addmedication.repo.medication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.databaselayer.MedicationDAO;
import com.iti.java.medicano.utils.FireBaseConstants;

import java.util.ArrayList;
import java.util.List;


public class MedicationRepoImpl implements MedicationRepo {

    private static MedicationRepoImpl repo = null;
    private MedicationDAO dao;
    private DatabaseReference database ;
    private static final String TAG = "MedicationRepoImpl";
    public static MedicationRepoImpl getInstance(MedicationDAO dao , FirebaseDatabase database,String userId) {
        if(repo == null){
            repo = new MedicationRepoImpl(dao,database,userId);
        }
        return repo;
    }

    private MedicationRepoImpl(MedicationDAO dao , FirebaseDatabase firebaseDatabase,String  userId){
        this.database =  firebaseDatabase.getReference(FireBaseConstants.MEDICATIONS).child(userId);
        this.dao = dao ;

    }

    @Override
    public void insertMedication(Medication medication) {
        new Thread(()->{
            database.child(medication.getId()).setValue(medication);
            dao.insertMedication(medication);
        }).start();

    }

    @Override
    public void deleteMedication(Medication medication) {
        database.child(medication.getId()).removeValue();
        new Thread(()->dao.delete(medication));
    }

    @Override
    public void editMedication(Medication medication) {
        database.child(medication.getId()).setValue(medication);
        new Thread(()->dao.updateMedication(medication));
    }

    @Override
    public Medication getMedication(String medicationId) {
        return dao.getMedication(medicationId);
    }

    @Override
    public LiveData<List<Medication>> getUserMedications(String userId) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Medication> medications = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    medications.add(dataSnapshot.getValue(Medication.class));
                new Thread(() ->{
                    dao.insertMedicationList(medications);
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });
        return dao.getUserAllMedications(userId);
    }

    @Override
    public void setUserId(String userId) {
        this.database =  FirebaseDatabase.getInstance().getReference(FireBaseConstants.MEDICATIONS).child(userId);

    }
}
