package com.iti.java.medicano.addmedication.repo;

import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.databaselayer.MedicationDAO;


public class AddMedicationRepoImpl  implements  AddMedicationRepo{

    private static AddMedicationRepoImpl repo = null;
    private MedicationDAO dao;
    private FirebaseDatabase database ;

    public static AddMedicationRepoImpl getInstance(MedicationDAO dao , FirebaseDatabase database) {
        if(repo == null){
            repo = new AddMedicationRepoImpl(dao,database);
        }
        return repo;
    }

    private AddMedicationRepoImpl(MedicationDAO dao , FirebaseDatabase database){

        this.database = database ;
        this.dao = dao ;
    }

    @Override
    public void insertMedication(Medication medication) {
        new Thread(()->{
            dao.insertMedication(medication);
        }).start();

    }
}
