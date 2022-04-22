package com.iti.java.medicano.mymedications.view;

import com.iti.java.medicano.model.Medication;

import java.util.List;

public class MedicationSection {
    private List<Medication> sectionItems;

    public MedicationSection( List<Medication> sectionItems) {
        this.sectionItems = sectionItems;
    }

    public List<Medication> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(List<Medication> sectionItems) {
        this.sectionItems = sectionItems;
    }
}
