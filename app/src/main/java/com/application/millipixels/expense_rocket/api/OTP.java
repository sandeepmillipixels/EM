package com.application.millipixels.expense_rocket.api;

/**
 * Created by millipixelsinteractive_024 on 26/04/18.
 */

public class OTP {


    /**
     * status : true
     * data : {"mobile":"+917837317983","otp":"7480","expires_in":"600000","message":"Hi your one time password for Expense Rocket is 7480 and is valid for 10 minutes.","sid":"SM4d24c1b361b24abaa0b8843b450ad743","status":"queued","updated_at":"2018-04-27 11:53:57","created_at":"2018-04-27 11:53:57","id":36}
     * error : null
     */

    private boolean status;
    private DataBean data;
    private ErrorResponse error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * mobile : +917837317983
         * otp : 7480
         * expires_in : 600000
         * message : Hi your one time password for Expense Rocket is 7480 and is valid for 10 minutes.
         * sid : SM4d24c1b361b24abaa0b8843b450ad743
         * status : queued
         * updated_at : 2018-04-27 11:53:57
         * created_at : 2018-04-27 11:53:57
         * id : 36
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
}
