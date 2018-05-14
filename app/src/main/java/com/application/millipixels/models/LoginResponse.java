package com.application.millipixels.models;

import java.util.List;

public class LoginResponse {


    /**
     * status : true
     * data : {"mobile":"+917009331805","otp":"4724","expires_in":"600000","message":"Hi your one time password for Expense Rocket is 4724 and is valid for 10 minutes.","sid":"SM0e2bbef06a4044bba40f227a1333c373","status":"queued","updated_at":"2018-05-09 05:59:36","created_at":"2018-05-09 05:59:36","id":4}
     * error : {"code":422,"error_message":{"message":["The mobile must be a number."]}}
     */

    private boolean status;
    private DataBean data;
    private ErrorBean error;

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

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * mobile : +917009331805
         * otp : 4724
         * expires_in : 600000
         * message : Hi your one time password for Expense Rocket is 4724 and is valid for 10 minutes.
         * sid : SM0e2bbef06a4044bba40f227a1333c373
         * status : queued
         * updated_at : 2018-05-09 05:59:36
         * created_at : 2018-05-09 05:59:36
         * id : 4
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

    public static class ErrorBean {
        /**
         * code : 422
         * error_message : {"message":["The mobile must be a number."]}
         */

        private int code;
        private ErrorMessageBean error_message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public ErrorMessageBean getError_message() {
            return error_message;
        }

        public void setError_message(ErrorMessageBean error_message) {
            this.error_message = error_message;
        }

        public static class ErrorMessageBean {
            private List<String> message;

            public List<String> getMessage() {
                return message;
            }

            public void setMessage(List<String> message) {
                this.message = message;
            }
        }
    }
}
