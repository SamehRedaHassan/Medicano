package com.iti.java.medicano.addmedication.strength.presenter;


import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.strength.view.AddMedStrengthView;
import com.iti.java.medicano.utils.CheckValue;

public class AddMedStrengthPresenterImpl implements AddMedStrengthPresenter {
    private final AddMedStrengthView addMedStrengthView;

    public AddMedStrengthPresenterImpl(AddMedStrengthView addMedStrengthView) {
        this.addMedStrengthView = addMedStrengthView;
    }

    @Override
    public void onPressNext(String strength) {
        if (CheckValue.isNumeric(strength)){
            float value = Float.parseFloat(strength);
            if (value > 0){
                addMedStrengthView.next(value);
            }else {
                addMedStrengthView.sendError(R.string.strength_should_be_bigger_than_zero);
            }
        }else {
            addMedStrengthView.sendError(R.string.strength_should_be_numeric);
        }
    }
}
