package com.iti.java.medicano.model.databaselayer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.iti.java.medicano.model.Medication;
import java.util.List;

@Dao
public interface MedicationDAO {
    @Query("SELECT * FROM Medication")
    LiveData<List<Medication>> getAllMedications();

    @Insert
    void insertMedication(Medication medication);

    @Delete
    void delete(Medication movie);

//    @Query("SELECT * FROM Medication WHERE id = id")
//    Medication getMedication(String id);
}
