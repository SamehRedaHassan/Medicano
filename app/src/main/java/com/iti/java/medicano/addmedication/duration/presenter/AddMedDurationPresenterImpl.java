package com.iti.java.medicano.addmedication.duration.presenter;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.duration.view.AddMedDurationView;

import java.util.Date;

public class AddMedDurationPresenterImpl  implements AddMedDurationPresenter{
    private final AddMedDurationView addMedDurationView;

    public AddMedDurationPresenterImpl(AddMedDurationView addMedDurationView) {
        this.addMedDurationView = addMedDurationView;
    }

    @Override
    public void onPressNext(Date startDate, Date endDate) {
        if (startDate != null && endDate != null){
            addMedDurationView.next();
        }else {
            addMedDurationView.sendError(R.string.can_not_be_empty);
        }
    }
}
