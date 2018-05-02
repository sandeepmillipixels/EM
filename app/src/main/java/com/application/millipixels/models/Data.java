package com.application.millipixels.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("mobile")
    @Expose
    private String mobileNumber;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("expires_in")
    @Expose
    private String expires_in;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("sid")
    @Expose
    private String sid;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("id")
    @Expose
    private String id;



    public Data(){

    }

    /**
     * @param mobile
     * @param otp
     * @param expires_in
     * @param message
     * @param sid
     * @param status
     * @param updated_at
     * @param created_at
     * @param id"
     */

    public Data(String mobile,String otp,String expires_in,String message,String sid,String status,String updated_at,String created_at,String id){

        this.mobileNumber=mobile;
        this.otp=otp;
        this.expires_in=expires_in;
        this.message=message;
        this.sid=sid;
        this.status=status;
        this.updated_at=updated_at;
        this.created_at=created_at;
        this.id=id;

    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
