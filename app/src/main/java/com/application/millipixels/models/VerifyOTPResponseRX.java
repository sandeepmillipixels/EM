package com.application.millipixels.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOTPResponseRX {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private VerifyOTPData data;


    @SerializedName("error")
    private String error;



    public VerifyOTPResponseRX(String status, String error, VerifyOTPData data){

        this.data=data;
        this.status=status;
        this.error=error;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VerifyOTPData getData() {
        return data;
    }

    public void setData(VerifyOTPData data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
