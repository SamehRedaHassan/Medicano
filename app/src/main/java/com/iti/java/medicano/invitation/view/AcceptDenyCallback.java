package com.iti.java.medicano.invitation.view;

public interface AcceptDenyCallback {
    void didPressAcceptWithID(String id , String name);
    void didPressDenyWithID(String id);
}
