package com.iti.java.medicano.addmedication.remiders.presenter;

import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.remiders.view.ReminderPickerDialogView;
import com.iti.java.medicano.utils.CheckValue;

public class ReminderPickerDialogPresenterImpl implements ReminderPickerDialogPresenter{
    private final ReminderPickerDialogView reminderPickerDialogView;

    public ReminderPickerDialogPresenterImpl(ReminderPickerDialogView reminderPickerDialogView) {
        this.reminderPickerDialogView = reminderPickerDialogView;
    }

    @Override
    public void onPressSave(String quantity) {
        if (CheckValue.isNumeric(quantity)){
            float value = Float.parseFloat(quantity);
            if (value > 0){
                reminderPickerDialogView.next(value);
            }else {
                reminderPickerDialogView.sendError(R.string.value_should_be_bigger_than_zero);
            }
        }else {
            reminderPickerDialogView.sendError(R.string.enter_valid_value);
        }
    }
}
