package com.iti.java.medicano.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {
    int treatmentTime;
    String instruction;

    protected Instruction(Parcel in) {
        treatmentTime = in.readInt();
        instruction = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(treatmentTime);
        dest.writeString(instruction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Instruction> CREATOR = new Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };
}
