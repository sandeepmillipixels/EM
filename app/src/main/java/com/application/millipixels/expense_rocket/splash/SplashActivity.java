package com.application.millipixels.expense_rocket.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.permissions.PermissionActivity;
import com.application.millipixels.expense_rocket.prefs.PrefrenceClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @BindView(R.id.imageView_rocket)
    ImageView imageView_rocket;

//    SharedPreferences sharedPreferences;
//
//    boolean login;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);
        ButterKnife.bind(this);


//        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
//
//        login=sharedPreferences.getBoolean("login",false);



        new Handler().postDelayed(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                PackageInfo info;
                try {
                    info = getPackageManager().getPackageInfo("com.application.millipixels.expense_rocket", PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md;
                        md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        String something = new String(Base64.encode(md.digest(), 0));
                        //String something = new String(Base64.encodeBytes(md.digest()));
                        Log.e("hash key", something);
                    }
                } catch (PackageManager.NameNotFoundException e1) {
                    Log.e("name not found", e1.toString());
                } catch (NoSuchAlgorithmException e) {
                    Log.e("no such an algorithm", e.toString());
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }

                if (PrefrenceClass.getLoginSharedPrefrence(SplashActivity.this)) {
                    Intent intent = new Intent(SplashActivity.this, Dashboard.class);
                    startActivity(intent);
                    finish();

                }else if(PrefrenceClass.getSignoutSharedPrefrence(SplashActivity.this)){

                    Intent intent = new Intent(SplashActivity.this, LoginSignupActivity.class);
                    startActivity(intent);
                    finish();

                } else {


                    Intent intent = new Intent(SplashActivity.this, TakeTour.class);
                    //Intent intent = new Intent(SplashActivity.this, PermissionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

        }, SPLASH_TIME_OUT);


    }
}
