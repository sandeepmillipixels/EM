package com.application.millipixels.expense_rocket.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class Utilities {


    public static boolean emailValidation(String email){

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return (email.matches(emailPattern));
    }

    public  static String getErrorMessage(ResponseBody val){
        String error = "Server Error!";
        try {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(val.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            error = jsonObject.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").get(0).toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }
}
