package com.iti.java.medicano.addmedication.name.presenter;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.name.view.AddMedNameView;

public class AddMedNamePresenterImpl implements AddMedNamePresenter{

    private final AddMedNameView addMedNameView;

    public AddMedNamePresenterImpl(AddMedNameView addMedNameView) {
        this.addMedNameView = addMedNameView;
    }

    public void onPressNext(String name) {
        if (name.isEmpty()){
            addMedNameView.showError(R.string.can_not_be_empty);
        }else {
            addMedNameView.next(name);
        }
    }
}
