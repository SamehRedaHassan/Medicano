package com.iti.java.medicano.addmedication.repo.reminder;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Reminder;

import java.util.Date;
import java.util.List;

public class ReminderRepoImpl implements ReminderRepo{

    @Override
    public void insertReminder(Reminder reminder) {

    }

    @Override
    public void insertAllReminder(List<Reminder> reminders) {

    }

    @Override
    public LiveData<List<Reminder>> getAllMedicationReminders(String medicationID) {
        return null;
    }

    @Override
    public LiveData<List<Reminder>> getAllReminderForDay(Date specificDay) {
        return null;
    }
}
