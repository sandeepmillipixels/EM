package com.application.millipixels.ui.view;

import com.application.millipixels.models.LoginResponse;

public interface LoginViewInterface {

    void showProgress();
    void hideProgress();
    void getOTP(String response);
    void displayError(String error);
}
