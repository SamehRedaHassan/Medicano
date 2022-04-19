package com.iti.java.medicano.addmedication.refill.view;

public interface AddMedRefillView {
    void sendError(int msgRes);

    void next(int valCurrNumOfPills, int valWhenReach);
}
