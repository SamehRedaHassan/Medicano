package com.iti.java.medicano.utils;

import android.text.Editable;
import android.text.TextWatcher;

public interface OnTextChangeListener extends TextWatcher {
    @Override
    public default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public default void afterTextChanged(Editable editable) {

    }
}
