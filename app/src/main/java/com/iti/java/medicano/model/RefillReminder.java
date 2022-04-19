package com.iti.java.medicano.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class RefillReminder implements Parcelable {
    int currentNumOfPills;
    int countToReminderWhenReady;

    public RefillReminder(int currentNumOfPills, int countToReminderWhenReady) {
        this.currentNumOfPills = currentNumOfPills;
        this.countToReminderWhenReady = countToReminderWhenReady;
    }

    protected RefillReminder(Parcel in) {
        currentNumOfPills = in.readInt();
        countToReminderWhenReady = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentNumOfPills);
        dest.writeInt(countToReminderWhenReady);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RefillReminder> CREATOR = new Creator<RefillReminder>() {
        @Override
        public RefillReminder createFromParcel(Parcel in) {
            return new RefillReminder(in);
        }

        @Override
        public RefillReminder[] newArray(int size) {
            return new RefillReminder[size];
        }
    };
}
