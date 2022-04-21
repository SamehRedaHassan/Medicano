package com.iti.java.medicano.addmedication.repo.reminder;

import androidx.lifecycle.LiveData;

import com.iti.java.medicano.model.Reminder;

import java.util.Date;
import java.util.List;

public interface ReminderRepo {
    void insertReminder(Reminder reminder);
    void insertAllReminder(List<Reminder> reminders);
    LiveData<List<Reminder>> getAllMedicationReminders(String medicationID);
    LiveData<List<Reminder>> getAllReminderForDay(Date specificDay);
}
