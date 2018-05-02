package com.application.millipixels.ui.view;

import com.application.millipixels.models.VerifyOTPResponseRX;

public interface VerifyViewinterface {

    void showProgress();
    void hideProgress();
    void getUserData(VerifyOTPResponseRX response);
    void displayError(String error);
}
