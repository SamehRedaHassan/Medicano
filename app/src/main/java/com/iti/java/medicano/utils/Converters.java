package com.iti.java.medicano.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iti.java.medicano.model.RefillReminder;
import com.iti.java.medicano.model.Reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static RefillReminder getRefillFromString(String value) {
        return new Gson().fromJson(value, RefillReminder.class);
        }

    @TypeConverter
    public static String RefillReminderToString(RefillReminder reminder) {
        return new Gson().toJson(reminder);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public static List<Integer> getdays(String value) {

        return new ArrayList<Integer>(Stream.of(value.split(";")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public static String getDays(List<Integer> days) {
        return days.stream().map(Object::toString).collect(Collectors.joining(";"));
    }

    @TypeConverter
    public static String getStringListOfReminders(List<Reminder> reminders) {
        return new Gson().toJson(reminders);
    }


    @TypeConverter
    public static List<Reminder> getListOfRemindersFromString(String reminders) {
        return new Gson().fromJson(reminders, new TypeToken<List<Reminder>>() {
        }.getType());
    }

    @TypeConverter
    public static String getStringToList(List<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<String> getListString(String str) {
        return new Gson().fromJson(str, new TypeToken<List<String>>() {
        }.getType());
    }

    @TypeConverter
    public static HashMap<String, String> getInvitations(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, String>>() {
        }.getType());
    }

    @TypeConverter
    public static String getStringToHashMAp(HashMap<String, String> list) {
        return new Gson().toJson(list);
    }
}
