package com.iti.java.medicano.addmedication.duration.view;

import static com.iti.java.medicano.utils.BundleKeys.DATE_TYPE;
import static com.iti.java.medicano.utils.BundleKeys.END_DATE;
import static com.iti.java.medicano.utils.BundleKeys.START_DATE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.Locale;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private NavController navController;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Calendar c = Calendar.getInstance(new Locale("en"));
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hours = c.get(Calendar.HOUR);
        DatePickerDialog dialog = new  DatePickerDialog(requireContext(), this, month, day, hours);
        if (getArguments()!=null && getArguments().getLong(START_DATE,-1)!=-1)
            dialog.getDatePicker().setMinDate(getArguments().getLong(START_DATE,-1));
        else
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        return dialog;
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar date = Calendar.getInstance(new Locale("en"));
        date.set(i, i1, i2, 0, 0);
        if (getArguments() != null && getArguments().getString(DATE_TYPE).equals(START_DATE))
            navController.getPreviousBackStackEntry().getSavedStateHandle().set(START_DATE,date);
        else
            navController.getPreviousBackStackEntry().getSavedStateHandle().set(END_DATE,date);
        navController.navigateUp();
    }

    @Override
    public void onStart() {
        super.onStart();
        navController = NavHostFragment.findNavController(this);
    }
}
