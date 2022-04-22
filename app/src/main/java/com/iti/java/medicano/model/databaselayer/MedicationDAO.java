package com.iti.java.medicano.model.databaselayer;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.iti.java.medicano.model.Medication;

import java.util.List;

@Dao
public interface MedicationDAO {

    @Query("SELECT * FROM Medication")
    List<Medication> getAllMedications();

    @Insert(onConflict = REPLACE)
    void insertMedicationList(List<Medication> medications);

    @Insert(onConflict = REPLACE)
    void insertMedication(Medication medication);

    @Delete
    void delete(Medication movie);

    @Query("SELECT * FROM Medication WHERE id = :mId")
    Medication getMedication(String mId);

    @Query("SELECT * FROM Medication WHERE userId = :uId")
    LiveData<List<Medication>> getUserAllMedications(String uId);

    @Update
    void updateMedication(Medication medication);

                                            //                  25/10       26/10       27/10
    @Query("SELECT * FROM Medication WHERE userId = :uId AND (startDate <= :dayDate OR endDate >= :dayDate) AND days LIKE '%'||:dayCode||'%'")
    List<Medication> getUserMedicationForDay(String uId, long dayDate, String dayCode);

    @Query("SELECT * FROM Medication WHERE  (startDate <= :dayDate OR endDate >= :dayDate) AND days LIKE '%'||:dayCode||'%'")
    List<Medication> getAllMedicationForDay(String uId, long dayDate, String dayCode);
}
