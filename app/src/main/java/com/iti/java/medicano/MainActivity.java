package com.iti.java.medicano;

import static com.iti.java.medicano.Constants.MAIN_WORKER_ID;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.iti.java.medicano.work.DailyWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar currentDate = Calendar.getInstance();
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 22);
        instance.set(Calendar.MINUTE, 10);
        instance.set(Calendar.SECOND, 0);
        if (instance.before(currentDate)){
            instance.add(Calendar.HOUR_OF_DAY,24);
        }
        long diff = instance.getTime().getTime() - currentDate.getTime().getTime();
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(DailyWorker.class, diff, TimeUnit.HOURS).build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(MAIN_WORKER_ID, ExistingPeriodicWorkPolicy.REPLACE,request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOverlayPermission();
    }

    // method to ask user to grant the Overlay permission
    public void checkOverlayPermission(){

        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }
    }




}