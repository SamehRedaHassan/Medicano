package com.iti.java.medicano.addmedication.form.presenter;

import com.iti.java.medicano.addmedication.form.view.AddMedFormFragment;

public class AddMedFormPresenterImpl implements AddMedFormPresenter{
    private AddMedFormFragment addMedFormFragment;
    public AddMedFormPresenterImpl(AddMedFormFragment addMedFormFragment) {
        this.addMedFormFragment = addMedFormFragment;
    }
}
