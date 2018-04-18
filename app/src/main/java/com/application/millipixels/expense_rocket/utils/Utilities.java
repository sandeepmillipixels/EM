package com.application.millipixels.expense_rocket.utils;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class Utilities {


    public static boolean emailValidation(String email){

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return (email.matches(emailPattern));
    }
}
