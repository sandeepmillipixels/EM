package com.application.millipixels.ui.view;

import com.application.millipixels.models.LoginResponse;

public interface LoginViewInterface {

    void showProgress();
    void hideProgress();
    void getOTP(LoginResponse response);
    void displayError(String error);
}
