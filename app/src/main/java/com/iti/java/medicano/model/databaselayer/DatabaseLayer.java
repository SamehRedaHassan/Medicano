package com.iti.java.medicano.model.databaselayer;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.utils.Converters;

@Database(entities = {Medication.class} , version = 1 , exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseLayer extends RoomDatabase {
    public abstract MedicationDAO MedicationDAO();

    private static DatabaseLayer INSTANCE;

    public static synchronized   DatabaseLayer getDBInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseLayer.class, "MedicationsDB").build();
        }
        return INSTANCE;
    }
}
