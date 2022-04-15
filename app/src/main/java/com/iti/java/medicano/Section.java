package com.iti.java.medicano;

import java.util.List;

public class Section {
    private String sectionName;
    private List<MyData> sectionItems;

    public Section(String sectionName, List<MyData> sectionItems) {
        this.sectionName = sectionName;
        this.sectionItems = sectionItems;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<MyData> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(List<MyData> sectionItems) {
        this.sectionItems = sectionItems;
    }
}
