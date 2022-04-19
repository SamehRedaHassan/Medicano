package com.iti.java.medicano.validation;

import android.text.TextUtils;
import android.util.Patterns;

public class Validation {

    public static String registerValidation(String name,String email,String password,String confirm) {
        String validationMsg = "";

        boolean nameHasDigits = false;
        char[] nameChars = name.toCharArray();
        for(char c : nameChars){
            if(Character.isDigit(c)){
                nameHasDigits = true;
            }
        }

        boolean passwordHasDigits = false;
        boolean passwordHasLetters = false;
        char[] passChars = name.toCharArray();
        for(char c : passChars){
            if(Character.isDigit(c)){
                passwordHasDigits = true;
            }
            else {
                passwordHasLetters = true;
            }
        }

        if(name.isEmpty()||nameHasDigits){
            validationMsg = "Please, Enter a valid name!";
        }
        else if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            validationMsg = "please, enter a valid email!";
        }
        else if(password.length()<8){
            validationMsg = "password should be more than or equal 8 characters!";
        }
        else if(!passwordHasDigits){
            validationMsg = "password should has at least 1 digit!";
        }
        else if(!passwordHasLetters){
            validationMsg = "password should has at least 1 character!";
        }
        else if(!confirm.equals(password)){
            validationMsg = "password confirmation doesn't match!";
        }
        else{
            validationMsg = "valid registeration";
        }

        return validationMsg;
    }

    public static boolean loginValidation(){

        return true;
    }


}
