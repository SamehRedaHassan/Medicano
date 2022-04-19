package com.iti.java.medicano.utils;

import com.google.android.material.textfield.TextInputEditText;

public class UIHelper {
    public static void sendEditTextError(TextInputEditText textInputEditText,String msg){
        textInputEditText.setError(msg);
        textInputEditText.clearFocus();
    }
    public static void removeErrorFromEditTextOnTextChange(TextInputEditText textInputEditText, OperationsWithNoArgs operationsWithNoArgs){
        textInputEditText.addTextChangedListener((OnTextChangeListener)( charSequence,  i,  i1,  i2) -> {
            textInputEditText.setError(null);
            operationsWithNoArgs.doWork();
        });
    }
    public static void removeErrorFromEditTextOnTextChange(TextInputEditText textInputEditText){
        textInputEditText.addTextChangedListener((OnTextChangeListener)( charSequence,  i,  i1,  i2) -> {
            textInputEditText.setError(null);
        });
    }
}
