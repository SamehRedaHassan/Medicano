package com.iti.java.medicano.utils;

import com.google.android.material.textfield.TextInputEditText;
import com.iti.java.medicano.R;

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

    public static int getIconFromPosition(int position) {
        int identifier = -1;
        switch (position) {
            case 0:
                identifier = R.drawable.ic__02_pill;
                break;
            case 1:
                identifier = R.drawable.ic__03_capsules;
                break;
            case 2:
                identifier = R.drawable.ic__04_pill;
                break;
            case 3:
                identifier = R.drawable.ic__05_pills;
                break;
            case 4:
                identifier = R.drawable.ic__08_pill;
                break;
            case 5:
                identifier = R.drawable.ic__10_eye_drops;
                break;
            case 6:
                identifier = R.drawable.ic__11_eye_drops;
                break;
            case 7:
                identifier = R.drawable.ic__12_tooth_paste;
                break;
            case 8:
                identifier = R.drawable.ic__13_eye_drops;
                break;
            case 9:
                identifier = R.drawable.ic__15_skincare;
                break;
            case 10:
                identifier = R.drawable.ic__26_ampoule;
                break;
            case 11:
                identifier = R.drawable.ic__28_iv_bag;
                break;
            case 12:
                identifier = R.drawable.ic__29_injection;
                break;
            case 13:
                identifier = R.drawable.ic__33_pills;
                break;
            case 14:
                identifier = R.drawable.ic__45_ear_dropper;
                break;
            case 15:
                identifier = R.drawable.ic__55_pills_bottle;
                break;
            case 16:
                identifier = R.drawable.ic__58_medicne;
                break;
            case 17:
                identifier = R.drawable.ic__62_dropper;
                break;
            case 18:
                identifier = R.drawable.ic__70_eye_drops;
                break;
            case 19:
                identifier = R.drawable.ic_pills_1;
                break;
            default:
                identifier = R.drawable.ic__02_pill;
                break;
        }
        return identifier;
    }
}
