package com.application.millipixels.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponse {
    /**
     * code : 408
     * error_message : {"message":["Invalid OTP."]}
     */
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("error_message")
    @Expose
    private ErrorMessageBean error_message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorMessageBean getError_message() {
        return error_message;
    }

    public void setError_message(ErrorMessageBean error_message) {
        this.error_message = error_message;
    }

    public static class ErrorMessageBean {
        @SerializedName("message")
        @Expose
        private List<String> message;

        public List<String> getMessage() {
            return message;
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }
    }

    /**
     * code : 411
     * error_message : [HTTP 400] Unable to create record: The number +9178373179831 is unverified. Trial accounts cannot send messages to unverified numbers; verify +9178373179831 at twilio.com/user/account/phone-numbers/verified, or purchase a Twilio number to send messages to unverified numbers.
     */

}
