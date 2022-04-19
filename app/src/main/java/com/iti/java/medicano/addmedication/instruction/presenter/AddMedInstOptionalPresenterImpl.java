package com.iti.java.medicano.addmedication.instruction.presenter;

import com.iti.java.medicano.addmedication.instruction.view.AddMedInstOptionalView;

public class AddMedInstOptionalPresenterImpl implements AddMedInstOptionalPresenter {

    private final AddMedInstOptionalView addMedInstOptionalView;

    public AddMedInstOptionalPresenterImpl(AddMedInstOptionalView addMedInstOptionalView) {
        this.addMedInstOptionalView = addMedInstOptionalView;
    }

    @Override
    public void onPressNext(String inst) {

    }
}
