package com.iti.java.medicano.addmedication.repo.medication;

import static com.iti.java.medicano.utils.MyDateUtils.isTodayIsStartOrEndOrBetweenDate;

import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.databaselayer.MedicationDAO;
import com.iti.java.medicano.utils.Converters;
import com.iti.java.medicano.utils.FireBaseConstants;
import com.iti.java.medicano.utils.MyDateUtils;
import com.iti.java.medicano.work.WorkersHandler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MedicationRepoImpl implements MedicationRepo {

    private static MedicationRepoImpl repo = null;
    private final MedicationDAO dao;
    private final WorkManager workManager;
    private DatabaseReference database;
    private final LiveData<List<Medication>> listLiveData;
    private final MutableLiveData<List<Medication>> mutableLiveData = new MutableLiveData<>();
    private static final String TAG = "MedicationRepoImpl";

    public static MedicationRepoImpl getInstance(MedicationDAO dao, FirebaseDatabase database, String userId, WorkManager workManager) {
        if (repo == null) {
            repo = new MedicationRepoImpl(dao, database, userId, workManager);
        }
        return repo;
    }

    private MedicationRepoImpl(MedicationDAO dao, FirebaseDatabase firebaseDatabase, String userId, WorkManager workManager) {
        this.database = firebaseDatabase.getReference(FireBaseConstants.MEDICATIONS).child(userId);
        this.dao = dao;
        listLiveData = mutableLiveData;
        this.workManager = workManager;
    }

    @Override
    public void insertMedication(Medication medication) {
        new Thread(() -> {
            database.child(medication.getId()).setValue(medication);
            dao.insertMedication(medication);
            WorkersHandler.loopOnMedicationReminders(medication,workManager);
        }).start();

    }



    @Override
    public void deleteMedication(Medication medication) {
        database.child(medication.getId()).removeValue();
        new Thread(() -> dao.delete(medication)).start();
    }

    @Override
    public void editMedication(Medication medication) {
        database.child(medication.getId()).setValue(medication);
        new Thread(() -> dao.updateMedication(medication)).start();
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
                new Thread(() -> {
                    dao.insertMedicationList(medications);
                    WorkersHandler.loopOnListOfMedications(medications,workManager);
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
    public List<Medication> getAlMedications() {
        return dao.getAllMedications();
    }

    @Override
    public void setUserId(String userId) {
        this.database = FirebaseDatabase.getInstance().getReference(FireBaseConstants.MEDICATIONS).child(userId);
    }

    @Override
    public LiveData<List<Medication>> getUserMedicationForDay(String uId, long dayDate, String dayCode) {
        setDayAndDate(uId, dayDate, dayCode);
        return listLiveData;
    }

    @Override
    public List<Medication> getAllMedicationForDay(long dayDate, String dayCode) {
        return dao.getAllMedications();
    }

    @Override
    public void setDayAndDate(String uId, long dayDate, String dayCode) {
        new Thread(() -> {
            List<Medication> userMedicationForDay = dao.getUserMedicationForDay(uId, dayDate, dayCode);
            new Handler(Looper.getMainLooper()).post(() -> {
                mutableLiveData.setValue(userMedicationForDay);
            });
        }).start();
    }
}
