package com.application.millipixels.models;

public class ErrorResponse {

    /**
     * code : 411
     * error_message : [HTTP 400] Unable to create record: The number +9178373179831 is unverified. Trial accounts cannot send messages to unverified numbers; verify +9178373179831 at twilio.com/user/account/phone-numbers/verified, or purchase a Twilio number to send messages to unverified numbers.
     */

    private int code;
    private String error_message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
