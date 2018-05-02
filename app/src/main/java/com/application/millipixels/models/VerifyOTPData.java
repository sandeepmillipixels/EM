package com.application.millipixels.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOTPData {



    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("User")
    @Expose
    private UserData userData;


    public VerifyOTPData(String token,UserData userData){

        this.token=token;
        this.userData=userData;

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
