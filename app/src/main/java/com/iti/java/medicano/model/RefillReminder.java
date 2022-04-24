package com.iti.java.medicano.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RefillReminder implements Parcelable {
   public int currentNumOfPills;
   public int countToReminderWhenReach;


   public RefillReminder(){}
    public RefillReminder(int currentNumOfPills, int countToReminderWhenReady) {
        this.currentNumOfPills = currentNumOfPills;
        this.countToReminderWhenReach = countToReminderWhenReady;
    }

    protected RefillReminder(Parcel in) {
        currentNumOfPills = in.readInt();
        countToReminderWhenReach = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentNumOfPills);
        dest.writeInt(countToReminderWhenReach);
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
