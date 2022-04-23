package com.iti.java.medicano.work;

import static com.iti.java.medicano.Constants.SHARED_PREFERENCES;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DailyWorker extends Worker {
    private static final String TAG = "DailyWorker";
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
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        long hours = c.get(Calendar.HOUR);
        List<Medication> medications = medicationRepo.getAllMedicationForDay(date.getTime(), dayOfWeek + "");
        for (Medication m : medications) {
            for (Reminder r : m.getRemindersID()) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MINUTE,r.minutes);
                cal.set(Calendar.HOUR,r.hours);
                Log.e(TAG, "doWork: date.getTime() ="+date.getTime());
                Log.e(TAG, "doWork: date.getTime() ="+cal.getTime().getTime());
                long delay = date.getTime() - cal.getTime().getTime();
                Log.e(TAG, "doWork: "+delay);
                OneTimeWorkRequest reminderWork = new OneTimeWorkRequest
                        .Builder(ReminderWorker.class)
                        .addTag(m.getId())
                        .setInputData(
                                new Data.Builder()
                                        .putString(MEDICATION_ID,m.getId())
                                        .build()
                        )
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .build();
                WorkManager.getInstance(getApplicationContext()).enqueue(reminderWork);
            }
        }
        return Result.success();
    }
}
