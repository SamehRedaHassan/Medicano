package com.iti.java.medicano.notification;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

import static com.iti.java.medicano.utils.BundleKeys.MEDICATION_BUILDER;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.NotificationCompat.Builder;
import com.iti.java.medicano.R;
import com.iti.java.medicano.floatingnotification.ForegroundService;
import com.iti.java.medicano.model.Medication;
import org.jetbrains.annotations.NotNull;


public final class NotificationHandler {
    private static final String CHANNEL_ID = "transactions_reminder_channel";
    private static final String TAG = "NotificationHandler";
    @NotNull
    public static final NotificationHandler INSTANCE;

    public final void createReminderNotification(@NotNull Context context,Medication medication) {
//        Intrinsics.checkNotNullParameter(context, "context");
        // check if the user has already granted
        // the Draw over other apps permission
        Log.e(TAG, "createReminderNotification: "+medication );
        if(Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(context, ForegroundService.class);
            intent.putExtra(MEDICATION_BUILDER,medication);
            // start the service based on the android version
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            this.createNotificationChannel(context);
            Builder builder = new Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Remember to add your transactions!")
                    .setContentText("Logging your transactions daily can help you manage your finances better.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent) // For launching the MainActivity
                    .setAutoCancel(true) // Remove notification when tapped
                    .setVisibility(VISIBILITY_PUBLIC); // Show on lock screen
            NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(intent);
//            } else {
//                context.startService(intent);
//            }
        }

    }

    private void createNotificationChannel(Context context) {
        if (VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            String name = "Daily Reminders";
            String descriptionText = "This channel sends daily reminders to add your transactions";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

    }
    // method for starting the service
    public void startService(Medication medication,Context context){
        // check if the user has already granted
        // the Draw over other apps permission
        if(Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(context, ForegroundService.class);
            intent.putExtra(MEDICATION_BUILDER,medication);
            // start the service based on the android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }
    private NotificationHandler() {
    }

    static {
        NotificationHandler var0 = new NotificationHandler();
        INSTANCE = var0;
    }
}
