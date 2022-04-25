package com.iti.java.medicano.utils;

import android.text.format.DateUtils;

import com.iti.java.medicano.model.Medication;

import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {
    public static Date getTodayDate() {
        Calendar c = Calendar.getInstance();

        // set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // and get that as a Date
        return c.getTime();
    }

    public static boolean isInRange(Date selected, Date start, Date end) {
        return selected.before(end) && selected.after(start);
    }

    public static boolean isTodayInRange(Date start, Date end) {
        return isInRange(getTodayDate(), start, end);
    }

    public static int getTodayDayCode() {
        Calendar c = Calendar.getInstance();
        c.setTime(getTodayDate());

        return c.get(Calendar.DAY_OF_WEEK);
    }
    public static boolean isTodayIsStartOrEndOrBetweenDate(Medication medication) {
        boolean isTodayEqualStartDate = DateUtils.isToday(medication.getStartDate().getTime());
        boolean isTodayEqualEndDate = DateUtils.isToday(medication.getEndDate().getTime());
        boolean isTodayBetweenStartAndEndDate = MyDateUtils.isTodayInRange(medication.getStartDate(), medication.getEndDate());
        return (isTodayEqualStartDate || isTodayEqualEndDate || isTodayBetweenStartAndEndDate);
    }

    public static Date truncateToDate(Date startDate) {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    public static Date getLastTime(Date startDate) {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}

