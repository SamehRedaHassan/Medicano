package com.iti.java.medicano.addmedication.days.presenter;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.days.view.AddMedDaysView;

import java.util.List;

public class AddMedDaysPresenterImpl implements AddMedDaysPresenter{
    private final AddMedDaysView addMedDaysView;

    public AddMedDaysPresenterImpl(AddMedDaysView addMedDaysView) {
        this.addMedDaysView = addMedDaysView;
    }

    @Override
    public void onPressNext(List<Integer> days) {
        if (days.isEmpty()){
            addMedDaysView.sendError(R.string.must_select_days);
        }else {
            addMedDaysView.next();
        }
    }
}
