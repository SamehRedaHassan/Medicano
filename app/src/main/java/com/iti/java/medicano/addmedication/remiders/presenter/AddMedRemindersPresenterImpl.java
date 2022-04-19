package com.iti.java.medicano.addmedication.remiders.presenter;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.remiders.view.AddMedRemindersView;
import com.iti.java.medicano.model.Reminder;

import java.util.List;

public class AddMedRemindersPresenterImpl implements AddMedRemindersPresenter{
    private final AddMedRemindersView addMedRemindersView;

    public AddMedRemindersPresenterImpl(AddMedRemindersView addMedRemindersView) {
        this.addMedRemindersView = addMedRemindersView;
    }

    @Override
    public void onPressNext(List<Reminder> reminders) {
        if (!reminders.isEmpty()){
            addMedRemindersView.next();
        } else {
            addMedRemindersView.sendError(R.string.add_reminders_first);
        }
    }
}
