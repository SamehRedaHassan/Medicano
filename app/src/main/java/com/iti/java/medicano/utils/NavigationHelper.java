package com.iti.java.medicano.utils;

import android.os.Bundle;

import androidx.navigation.NavController;

public class NavigationHelper {
    public static void safeNavigateTo(NavController navController, int currentFragmentID, int actionID){
        if (navController.getCurrentDestination()!=null && navController.getCurrentDestination().getId() == currentFragmentID)
            navController.navigate(actionID);
    }
    public static void safeNavigateTo(NavController navController, int currentFragmentID, int actionID, Bundle bundle){
        if (navController.getCurrentDestination()!=null && navController.getCurrentDestination().getId() == currentFragmentID)
            navController.navigate(actionID,bundle);
    }
}

