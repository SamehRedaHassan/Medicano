package com.iti.java.medicano.invitation.view;

public interface AcceptDenyCallback {
    void didPressAcceptWithID(String id);
    void didPressDenyWithID(String id);
}
