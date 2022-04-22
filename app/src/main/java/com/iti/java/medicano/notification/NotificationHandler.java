package com.iti.java.medicano.notification;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.NotificationCompat.Builder;

import com.iti.java.medicano.MainActivity;
import com.iti.java.medicano.R;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;


public final class NotificationHandler {
    private static final String CHANNEL_ID = "transactions_reminder_channel";
    @NotNull
    public static final NotificationHandler INSTANCE;

    public final void createReminderNotification(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        this.createNotificationChannel(context);
        Builder builder = new Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Remember to add your transactions!")
                .setContentText("Logging your transactions daily can help you manage your finances better.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) // For launching the MainActivity
                .setAutoCancel(true) // Remove notification when tapped
                .setVisibility(VISIBILITY_PUBLIC); // Show on lock screen
        NotificationManagerCompat.from(context).notify(1, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            String name = "Daily Reminders";
            String descriptionText = "This channel sends daily reminders to add your transactions";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

    }

    private NotificationHandler() {
    }

    static {
        NotificationHandler var0 = new NotificationHandler();
        INSTANCE = var0;
    }
}
