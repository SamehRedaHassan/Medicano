package com.iti.java.medicano.addmedication.refill.presenter;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.refill.view.AddMedRefillView;
import com.iti.java.medicano.utils.CheckValue;

public class AddMedRefillPresenterImpl implements AddMedRefillPresenter{
    private final AddMedRefillView addMedRefillView;

    public AddMedRefillPresenterImpl(AddMedRefillView addMedRefillView) {
        this.addMedRefillView =addMedRefillView;
    }

    @Override
    public void onPressNext(String currNumOfPills, String whenReach) {
        if (CheckValue.isNumeric(currNumOfPills) && CheckValue.isNumeric(whenReach)){
            int valCurrNumOfPills = Integer.parseInt(currNumOfPills);
            int valWhenReach = Integer.parseInt(whenReach);
            if (valCurrNumOfPills > 0 && valWhenReach > 0){
                addMedRefillView.next(valCurrNumOfPills,valWhenReach);
            }else
                addMedRefillView.sendError(R.string.enter_valid_value);

        }else
            addMedRefillView.sendError(R.string.enter_valid_value);
    }
}
