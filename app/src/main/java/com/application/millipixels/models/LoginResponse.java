package com.application.millipixels.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("code")
    @Expose
    private ErrorResponse code;


    public LoginResponse() {
    }

    /**
     * @param error
     * @param data
     * @param status
     */

    public LoginResponse(String status, Data data, ErrorResponse error) {

        this.status = status;
        this.data = data;
        this.code = error;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return code;
    }

    public void setError(ErrorResponse error) {
        this.code = error;
    }
}
