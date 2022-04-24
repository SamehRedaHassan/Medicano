package com.iti.java.medicano.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.iti.java.medicano.utils.ReminderStatus;
import java.util.UUID;


public class Reminder implements Parcelable {
    //Room experimental
    public String reminderID;
    //Room
    public String medicationID;
    public int hours;
    public int minutes;
    public float drugQuantity;
    public int status = ReminderStatus.PENDING;


    public Reminder(){}

    public Reminder(int hours, int minutes, float drugQuantity) {
        this.reminderID = UUID.randomUUID().toString();
        this.hours = hours;
        this.minutes = minutes;
        this.drugQuantity = drugQuantity;
    }


    protected Reminder(Parcel in) {
        reminderID = in.readString();
        medicationID = in.readString();
        hours = in.readInt();
        minutes = in.readInt();
        drugQuantity = in.readFloat();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reminderID);
        dest.writeString(medicationID);
        dest.writeInt(hours);
        dest.writeInt(minutes);
        dest.writeFloat(drugQuantity);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };
}
