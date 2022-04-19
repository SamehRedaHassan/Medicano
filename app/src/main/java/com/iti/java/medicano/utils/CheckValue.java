package com.iti.java.medicano.utils;

import java.util.regex.Pattern;

public class CheckValue {
    public static final String isNumber = "[+-]?([0-9]*[.])?[0-9]+";
    public static boolean isNumeric(String value){
        return Pattern.matches(isNumber,value);
    }
}
