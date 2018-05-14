package com.application.millipixels.models;

public class Data {


    /**
     * mobile : +917009331805
     * otp : 7897
     * expires_in : 600000
     * message : Hi your one time password for Expense Rocket is 7897 and is valid for 10 minutes.
     * sid : SMdf0c664d2623479795c1f08bd27c1160
     * status : queued
     * updated_at : 2018-05-10 10:36:21
     * created_at : 2018-05-10 10:36:21
     * id : 14
     */

    private String mobile;
    private String otp;
    private String expires_in;
    private String message;
    private String sid;
    private String status;
    private String updated_at;
    private String created_at;
    private int id;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
