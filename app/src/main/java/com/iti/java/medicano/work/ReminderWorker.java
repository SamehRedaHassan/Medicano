package com.iti.java.medicano.work;

import static com.iti.java.medicano.Constants.SHARED_PREFERENCES;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;
import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_ID;
import static com.iti.java.medicano.utils.BundleKeys.REMINDER;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.floatingnotification.ForegroundService;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.notification.NotificationHandler;
import com.iti.java.medicano.utils.MedicationStatus;
import com.iti.java.medicano.utils.ReminderStatus;

public class ReminderWorker extends Worker {
    private static final String TAG = "ReminderWorker";
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DatabaseLayer databaseLayer = DatabaseLayer.getDBInstance(getApplicationContext());
        UserRepo userRepo = UserRepoImpl.getInstance(databaseLayer.UserDAO(), getApplicationContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE));
        User user = userRepo.getPreferences();
        String mID = getInputData().getString(MEDICATION_ID);
        String reminderId = getInputData().getString(REMINDER);
        Log.e(TAG, "doWork: "+mID);
        if (mID!=null){
            MedicationRepo medicationRepo = MedicationRepoImpl.getInstance(
                    databaseLayer.MedicationDAO(),
                    FirebaseDatabase.getInstance(),
                    user.getId(),
                    WorkManager.getInstance(getApplicationContext()));
            Medication medication = medicationRepo.getMedication(mID);
            if (medication!=null && medication.status == MedicationStatus.ACTIVE ){
                Reminder reminder =null;
                for(Reminder mRemind :medication.getRemindersID()){
                    if (mRemind.reminderID.equals(reminderId))
                        reminder = mRemind;
                }
                if (reminder!=null && reminder.status== ReminderStatus.PENDING)
                    NotificationHandler.INSTANCE.createReminderNotification(getApplicationContext(),medication,reminderId);
                else
                    Log.e(TAG, "doWork: Reminder Work reminder == null or => *Status Not Equal pending*" );
            }else{
                Log.e(TAG, "doWork: Reminder Work med null or not active or");
            }
        }
        return Result.success();
    }

}
