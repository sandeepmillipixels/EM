package com.application.millipixels.expense_rocket.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefrenceClass {



    public static void saveInSharedPrefrence(Context context,String key,boolean value){

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean(key,value);
        editor.commit();

    }


    public static boolean getLoginSharedPrefrence(Context context){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        boolean value=prefs.getBoolean("login",false);

        return value;



    }

    public static boolean getSignoutSharedPrefrence(Context context){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        boolean value=prefs.getBoolean("onboard",false);

        return value;



    }


}
