package com.iti.java.medicano.addmedication.repo.medication;

import android.os.Handler;
import android.os.Looper;
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
import com.iti.java.medicano.utils.FireBaseConstants;
import com.iti.java.medicano.utils.ReminderStatus;
import com.iti.java.medicano.work.WorkersHandler;

import java.util.ArrayList;
import java.util.List;


public class MedicationRepoImpl implements MedicationRepo {

    private static MedicationRepoImpl repo = null;
    private final MedicationDAO dao;
    private final WorkManager workManager;
    private DatabaseReference database;
    private final LiveData<List<Medication>> userMedicationsForDay;
    private final MutableLiveData<List<Medication>> _userMedicationsForDay = new MutableLiveData<>();
    private final LiveData<List<Medication>> userMedicationsForUser;
    private final MutableLiveData<List<Medication>> _userMedicationsForUser = new MutableLiveData<>();
    private final LiveData<List<Medication>> userMedicationsNeedsRefill;
    private final MutableLiveData<List<Medication>> _userMedicationsNeedsRefill = new MutableLiveData<>();
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
        userMedicationsForDay = _userMedicationsForDay;
        userMedicationsForUser = _userMedicationsForUser;
        userMedicationsNeedsRefill = _userMedicationsNeedsRefill;
        this.workManager = workManager;
        upDateDatabase();
    }

    @Override
    public void upDateDatabase() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Medication> medications = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    medications.add(dataSnapshot.getValue(Medication.class));
                new Thread(() -> {
                    dao.insertMedicationList(medications);
                    WorkersHandler.loopOnListOfMedications(medications, workManager);
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });
    }

    @Override
    public void insertMedication(Medication medication) {
        new Thread(() -> {
            database.child(medication.getId()).setValue(medication);
            dao.insertMedication(medication);
            WorkersHandler.loopOnMedicationReminders(medication, workManager);
        }).start();

    }


    @Override
    public void deleteMedication(Medication medication) {
        workManager.cancelAllWorkByTag(medication.getId());
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
        upDateDatabase();
        new Thread(() -> {
            List<Medication> userMedications = dao.getUserAllMedications(userId);
            new Handler(Looper.getMainLooper()).post(() -> {
                _userMedicationsForUser.setValue(userMedications);
            });
        }).start();
        return userMedicationsForUser;
    }

    @Override
    public List<Medication> getAlMedications() {
        upDateDatabase();
        return dao.getAllMedications();
    }

    @Override
    public void setUserId(String userId) {
        this.database = FirebaseDatabase.getInstance().getReference(FireBaseConstants.MEDICATIONS).child(userId);
        upDateDatabase();
    }

    @Override
    public LiveData<List<Medication>> getUserMedicationForDay(String uId, long dayDate, String dayCode) {
        setDayAndDate(uId, dayDate, dayCode);
        return userMedicationsForDay;
    }

    @Override
    public List<Medication> getAllMedicationForDay(long dayDate, String dayCode) {
        upDateDatabase();
        return dao.getAllMedications();
    }

    @Override
    public void setDayAndDate(String uId, long dayDate, String dayCode) {
        this.database = FirebaseDatabase.getInstance().getReference(FireBaseConstants.MEDICATIONS).child(uId);
        new Thread(() -> {
            List<Medication> userMedicationForDay = dao.getUserMedicationForDay(uId, dayDate, dayCode);
            new Handler(Looper.getMainLooper()).post(() -> {
                _userMedicationsForDay.setValue(userMedicationForDay);
            });
        }).start();
    }

    @Override
    public void requestUpdateMedicationsForCurrentUser(String userId) {
        Log.i("GGGG", "requestUpdateMedicationsForCurrentUser: " + userId);
        new Thread(() -> {
            List<Medication> userMedications = dao.getUserAllMedications(userId);
            new Handler(Looper.getMainLooper()).post(() -> {
                _userMedicationsForUser.setValue(userMedications);
            });
        }).start();
    }

    @Override
    public LiveData<List<Medication>> getMedicationsNeedsToRefill(String userId) {
        new Thread(() -> {
            List<Medication> medicationsNeedsToRefill = dao.getMedicationsNeedsToRefill(userId, true);
            new Handler(Looper.getMainLooper()).post(() -> {
                _userMedicationsNeedsRefill.setValue(medicationsNeedsToRefill);
            });
        }).start();
        return userMedicationsNeedsRefill;
    }

    @Override
    public void updateAllRemindersWithPendingStatusAfterOneDay() {
        new Thread(() -> {
            List<Medication> alMedications = getAlMedications();
            for (Medication m : alMedications) {
                for (Reminder r : m.getRemindersID()) {
                    r.status = ReminderStatus.PENDING;
                }
                FirebaseDatabase.getInstance().getReference(FireBaseConstants.MEDICATIONS).child(m.getId()).setValue(m);
                dao.updateMedication(m);
            }
        }).start();
    }


}
