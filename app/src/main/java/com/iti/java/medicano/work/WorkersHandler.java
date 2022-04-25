package com.iti.java.medicano.work;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_ID;
import static com.iti.java.medicano.utils.BundleKeys.REMINDER;
import static com.iti.java.medicano.utils.MyDateUtils.isTodayIsStartOrEndOrBetweenDate;
import android.content.Context;
import android.util.Log;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.utils.MyDateUtils;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkersHandler {
    private static final String TAG = "WorkersHandler";

    public static void cancelMedicationRemindersForTag(Context context, String tag) {
        WorkManager.getInstance(context).cancelAllWorkByTag(tag);
    }

    public static void cancelUniqueReminderFromId(Context context, String id) {
        WorkManager.getInstance(context).cancelUniqueWork(id);
    }

    public static void fireWorkManagerRequestForReminder(Medication m, Reminder r, Context context) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, r.minutes);
        cal.set(Calendar.HOUR, r.hours);
        Log.e(TAG, "fireWorkManagerRequestForReminder doWork: date.getTime() =" + date.getTime());
        Log.e(TAG, "fireWorkManagerRequestForReminder doWork: date.getTime() =" + cal.getTime().getTime());
        long delay =  cal.getTime().getTime() -new Date(System.currentTimeMillis()).getTime() ;
        Log.e(TAG, "fireWorkManagerRequestForReminder doWork: " + delay);
        if (delay>=-1000) {
            OneTimeWorkRequest reminderWork = new OneTimeWorkRequest
                    .Builder(ReminderWorker.class)
                    .addTag(m.getId())
                    .setInputData(
                            new Data.Builder()
                                    .putString(REMINDER, r.reminderID)
                                    .putString(MEDICATION_ID, m.getId())
                                    .build()
                    )
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .build();
            WorkManager.getInstance(context.getApplicationContext()).enqueueUniqueWork(r.reminderID, ExistingWorkPolicy.REPLACE, reminderWork);
        }else {
            Log.e(TAG, "fireWorkManagerRequestForReminder: "+"old one "+delay );
        }
    }

    public static void fireWorkManagerRequestForReminder(Medication m, Reminder r, WorkManager workManager) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, r.minutes);
        cal.set(Calendar.HOUR_OF_DAY, r.hours);
        Log.e(TAG, "doWork: date.getTime() =" + date.getTime());
        Log.e(TAG, "doWork: date.getTime() =" + cal.getTime().getTime());
        long delay =  cal.getTime().getTime() -new Date(System.currentTimeMillis()).getTime() ;
        Log.e(TAG, "doWork: " + delay);
        if (delay>=-1000) {
            Log.e(TAG, "fireWorkManagerRequestForReminder: "+m.getId()+"  "+m.getUserId() +"  "+m.getName());
            OneTimeWorkRequest reminderWork = new OneTimeWorkRequest
                    .Builder(ReminderWorker.class)
                    .addTag(m.getId())
                    .setInputData(
                            new Data.Builder()
                                    .putString(MEDICATION_ID, m.getId())
                                    .putString(REMINDER, r.reminderID)
                                    .build()
                    )
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .build();
            workManager.enqueueUniqueWork(r.reminderID, ExistingWorkPolicy.REPLACE, reminderWork);
        }else {
            Log.e(TAG, "fireWorkManagerRequestForReminder: else "+m.getName() +" delay "+delay );
        }
    }

    public static void loopOnMedicationReminders(Medication medication, WorkManager workManager) {
        for (Reminder r : medication.getRemindersID()) {
            boolean isTodayIsSelectedWeekDay = (medication.getDays().contains(MyDateUtils.getTodayDayCode()));
            if (isTodayIsStartOrEndOrBetweenDate(medication) && isTodayIsSelectedWeekDay) {
                WorkersHandler.fireWorkManagerRequestForReminder(medication, r, workManager);
            }
        }
    }

    public static void loopOnListOfMedications(List<Medication> medications, WorkManager workManager) {
        for (Medication m : medications) {
            loopOnMedicationReminders(m, workManager);
        }
    }
}
