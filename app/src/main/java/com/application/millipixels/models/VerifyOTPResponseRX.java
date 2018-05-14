package com.application.millipixels.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  VerifyOTPResponseRX {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("data")
    @Expose
    private VerifyOTPData data;


    @SerializedName("error")
    private ErrorResponse error;



    public VerifyOTPResponseRX(boolean status, ErrorResponse error, VerifyOTPData data){

        this.data=data;
        this.status=status;
        this.error=error;

    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public VerifyOTPData getData() {
        return data;
    }

    public void setData(VerifyOTPData data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
