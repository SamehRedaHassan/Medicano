package com.iti.java.medicano.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

@Entity
public class Medication implements Parcelable{
    @NonNull
    @PrimaryKey
    String id ;
    //@ForeignKey(entity = User.class)
    String userId;
    String name;
    int strengthType;
    float strengthValue;
    String reasonForMedication;
    int formOfMedication;
    Date startDate;
    Date endDate;
    int treatmentTime;
    String instruction;
    RefillReminder refillReminder;
    List<Reminder> remindersID;
    List<Integer> days;
    public int status ;
    int icon ;
    public boolean needsToRefill = false;


    protected Medication(Parcel in) {
        id = in.readString();
        userId = in.readString();
        name = in.readString();
        strengthType = in.readInt();
        strengthValue = in.readFloat();
        reasonForMedication = in.readString();
        formOfMedication = in.readInt();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        treatmentTime = in.readInt();
        instruction = in.readString();
        refillReminder = in.readParcelable(RefillReminder.class.getClassLoader());
        remindersID = in.createTypedArrayList(Reminder.CREATOR);
        days = new Gson().fromJson(in.readString(),new TypeToken<List<Integer>>(){}.getType());
        status = in.readInt();
        icon = in.readInt();
        needsToRefill = in.readInt() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeInt(strengthType);
        dest.writeFloat(strengthValue);
        dest.writeString(reasonForMedication);
        dest.writeInt(formOfMedication);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeInt(treatmentTime);
        dest.writeString(instruction);
        dest.writeParcelable(refillReminder, flags);
        dest.writeTypedList(remindersID);
        dest.writeString(new Gson().toJson(days));
        dest.writeInt(status);
        dest.writeInt(icon);
        dest.writeInt(needsToRefill ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Medication(){

    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Medication(String name, int strengthType, float strengthValue, String reasonForMedication, int formOfMedication, Date startDate, Date endDate, int treatmentTime, String instruction, RefillReminder refillReminder, List<Reminder> remindersID, List<Integer> days, int icon , int status) {
        this.name = name;
        this.strengthType = strengthType;
        this.strengthValue = strengthValue;
        this.reasonForMedication = reasonForMedication;
        this.formOfMedication = formOfMedication;
        this.startDate = startDate;
        this.endDate = endDate;
        this.treatmentTime = treatmentTime;
        this.instruction = instruction;
        this.refillReminder = refillReminder;
        this.remindersID = remindersID;
        this.days = days;
        this.icon = icon;
        this.status = status;
    }

    public Medication(@NonNull String id, String userId, String name, int strengthType, float strengthValue, String reasonForMedication, int formOfMedication, Date startDate, Date endDate, int treatmentTime, String instruction, RefillReminder refillReminder, List<Reminder> remindersID, List<Integer> days, int icon, int status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.strengthType = strengthType;
        this.strengthValue = strengthValue;
        this.reasonForMedication = reasonForMedication;
        this.formOfMedication = formOfMedication;
        this.startDate = startDate;
        this.endDate = endDate;
        this.treatmentTime = treatmentTime;
        this.instruction = instruction;
        this.refillReminder = refillReminder;
        this.remindersID = remindersID;
        this.days = days;
        this.icon = icon;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrengthType() {
        return strengthType;
    }

    public void setStrengthType(int strengthType) {
        this.strengthType = strengthType;
    }

    public float getStrengthValue() {
        return strengthValue;
    }

    public void setStrengthValue(float strengthValue) {
        this.strengthValue = strengthValue;
    }

    public String getReasonForMedication() {
        return reasonForMedication;
    }

    public void setReasonForMedication(String reasonForMedication) {
        this.reasonForMedication = reasonForMedication;
    }

    public int getFormOfMedication() {
        return formOfMedication;
    }

    public void setFormOfMedication(int formOfMedication) {
        this.formOfMedication = formOfMedication;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(int treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public RefillReminder getRefillReminder() {
        return refillReminder;
    }

    public void setRefillReminder(RefillReminder refillReminder) {
        this.refillReminder = refillReminder;
    }

    public List<Reminder> getRemindersID() {
        return remindersID;
    }

    public void setRemindersID(List<Reminder> remindersID) {
        this.remindersID = remindersID;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public static class Builder implements Parcelable {

        String name;
        int strengthType;
        float strengthValue;
        String reasonForMedication;
        int formOfMedication;
        Date startDate;
        Date endDate;
        int treatmentTime;


        String instruction;
        RefillReminder refillReminder;
        List<Reminder> remindersID;
        List<Integer> days;
        int icon;
        int status;

        public Builder(String name) {
            this.name = name;
        }


        protected Builder(Parcel in) {
            name = in.readString();
            strengthType = in.readInt();
            strengthValue = in.readFloat();
            reasonForMedication = in.readString();
            formOfMedication = in.readInt();
            treatmentTime = in.readInt();
            instruction = in.readString();
            refillReminder = in.readParcelable(RefillReminder.class.getClassLoader());
            remindersID = in.createTypedArrayList(Reminder.CREATOR);
            icon = in.readInt();
            status = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(strengthType);
            dest.writeFloat(strengthValue);
            dest.writeString(reasonForMedication);
            dest.writeInt(formOfMedication);
            dest.writeInt(treatmentTime);
            dest.writeString(instruction);
            dest.writeParcelable(refillReminder, flags);
            dest.writeTypedList(remindersID);
            dest.writeInt(icon);
            dest.writeInt(status);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setStrengthType(int type){
            this.strengthType = type;
            return this;
        }
        public Builder setStrengthValue(float strengthValue){
            this.strengthValue = strengthValue;
            return this;
        }
        public Builder setReasonForMedication(String reasonForMedication){
            this.reasonForMedication = reasonForMedication;
            return this;
        }
        public Builder setFormOfMedication(int formOfMedication){
            this.formOfMedication = formOfMedication;
            return this;
        }

        public Builder setStartDate(Date startDate){
            this.startDate = startDate;
            return this;
        }
        public Builder setEndDate(Date endDate){
            this.endDate = endDate;
            return this;
        }

        public Builder setTreatmentTime(int treatmentTime) {
            this.treatmentTime = treatmentTime;
            return this;
        }

        public Builder setInstruction(String instruction) {
            this.instruction = instruction;
            return this;
        }

        public Builder setRefillReminder(RefillReminder refillReminder){
            this.refillReminder = refillReminder;
            return this;
        }

        public Builder setReminderId(List<Reminder> remindersIDs){
            this.remindersID = remindersIDs;
            return this;
        }
        public Builder setDays(List<Integer> days){
            this.days =days;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "name='" + name + '\'' +
                    ", strengthType=" + strengthType +
                    ", strengthValue=" + strengthValue +
                    ", reasonForMedication='" + reasonForMedication + '\'' +
                    ", formOfMedication=" + formOfMedication +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", treatmentTime=" + treatmentTime +
                    ", instruction='" + instruction + '\'' +
                    ", refillReminder=" + refillReminder +
                    ", remindersID=" + remindersID +
                    ", days=" + days +
                    ", status=" + status +
                    '}';
        }

        public Medication build(){
            return new Medication(name, strengthType, strengthValue, reasonForMedication, formOfMedication, startDate, endDate, treatmentTime, instruction, refillReminder, remindersID, days,icon,status);
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Medication){
            return this.id.equals(((Medication) obj).id);
        }else
            return false;
    }
}
