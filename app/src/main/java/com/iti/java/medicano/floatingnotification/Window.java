package com.iti.java.medicano.floatingnotification;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;

import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepo;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.Reminder;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.utils.ReminderStatus;
import com.iti.java.medicano.work.WorkersHandler;

public class Window {

    // declaring required variables
    private Context context;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private LayoutInflater layoutInflater;
    //private Medication medication;
    //private String reminderId;
    private Reminder reminder;
    private MedicationRepo mediRepo;

    private TextView txtTime,txtMediName,txtMediDesc;
    private ImageView mediImg;
    private Button btnTake,btnSkip;

    public Window(Context context, Medication medication, String reminderId){
        this.context=context;
        //this.medication = medication;
        //this.reminderId = reminderId;
        this.mediRepo = MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(context).MedicationDAO(),
                FirebaseDatabase.getInstance(),
                FirebaseAuth.getInstance().getUid(),
                WorkManager.getInstance(context));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set the layout parameters of the window
            mParams = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    // Don't let it grab the input focus
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    // Make the underlying application window visible
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT);

        }
        // getting a LayoutInflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        mView = layoutInflater.inflate(R.layout.reminder_dialog, null);
        //mView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txtTime = mView.findViewById(R.id.txtReminderTime);
        txtMediDesc = mView.findViewById(R.id.txtMedDesc);
        txtMediName = mView.findViewById(R.id.txtMedName);
        mediImg = mView.findViewById(R.id.imgMedication);

        btnTake = mView.findViewById(R.id.btnTake);
        btnSkip = mView.findViewById(R.id.btnSkip);

        txtMediName.setText(medication.getName());
        String remTime="";
        String doseage="";
        Log.i("TAG", medication+" "+medication.getRemindersID());
        for(Reminder r:medication.getRemindersID()){

            if(r.reminderID.equals(reminderId)){
                this.reminder = r;
                remTime = r.hours+":"+r.minutes;
                doseage = r.drugQuantity+"";
            }
        }
        txtTime.setText(remTime);
        txtMediDesc.setText(medication.getStrengthValue()+" g, take "+doseage+" pill(s)");
        mediImg.setImageResource(medication.getIcon());

        // set onClickListener on the remove button, which removes
        // the view from the window
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Reminder r:medication.getRemindersID()){
                    if(r.reminderID.equals(reminderId)){
                        r.status = ReminderStatus.TAKEN;
                        reminder = r;
                    }
                }
                if(medication.getRefillReminder().currentNumOfPills>reminder.drugQuantity){
                    medication.getRefillReminder().currentNumOfPills-=reminder.drugQuantity;
                }
                else{
                    medication.getRefillReminder().currentNumOfPills=0;
                }
                //mediRepo.editMedication(medication);
                //mediRepo.upDateDatabase();
                WorkersHandler.cancelUniqueReminderFromId(context,reminderId);
                Log.i("TAG", "onClick: take"+reminder.status);
                context.stopService(new Intent(context,ForegroundService.class));
                close();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Reminder r:medication.getRemindersID()){
                    if(r.reminderID.equals(reminderId)){
                        r.status = ReminderStatus.CANCELED;
                        reminder = r;
                    }
                }
                //mediRepo.editMedication(medication);
                //mediRepo.upDateDatabase();
                WorkersHandler.cancelUniqueReminderFromId(context,reminderId);
                Log.i("TAG", "onClick skip: "+reminder.status);
                context.stopService(new Intent(context,ForegroundService.class));
                close();
            }
        });
        // Define the position of the
        // window within the screen
        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

    }

    public void open() {

        try {
            // check if the view is already
            // inflated or present in the window
            if(mView.getWindowToken()==null) {
                if(mView.getParent()==null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }

    }

    public void close() {

        try {
            // remove the view from the window
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            // invalidate the view
            mView.invalidate();
            // remove all views
            ((ViewGroup)mView.getParent()).removeAllViews();

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }
}

