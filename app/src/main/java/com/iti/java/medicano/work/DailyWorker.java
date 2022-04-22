package com.iti.java.medicano.work;

import static com.iti.java.medicano.Constants.SHARED_PREFERENCES;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyWorker extends Worker {
    public DailyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DatabaseLayer databaseLayer = DatabaseLayer.getDBInstance(getApplicationContext());
        UserRepo userRepo = UserRepoImpl.getInstance(databaseLayer.UserDAO(), getApplicationContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE));
        User user = userRepo.getPreferences();
        MedicationRepo medicationRepo = MedicationRepoImpl.getInstance(
                databaseLayer.MedicationDAO(),
                FirebaseDatabase.getInstance(),
                user.getId());
        Date date =new Date();
        List<Medication> medications = medicationRepo.getAlMedications();
        return Result.success();
    }
}
