package com.application.millipixels.expense_rocket.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.gallery.GalleyActivity;
import com.application.millipixels.expense_rocket.onboarding.OnBoarding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    @BindView(R.id.splash_expense_textView)
    TextView splash_textView;
    @BindView(R.id.imageView_rocket)
    ImageView imageView_rocket;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);
        ButterKnife.bind(this);
//        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/sf_pro_text_bold.ttf");
//        splash_textView.setTypeface(typeFace);
//        splash_textView.setText(Html.fromHtml("Keep eye on your <br>every <b>expense</b>"));
        new Handler().postDelayed(new Runnable() {

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
                Intent intent = new Intent(SplashActivity.this, TakeTour.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SplashActivity.this, (View)imageView_rocket, "profile");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, options.toBundle());
                }else {
                    startActivity(intent);
                }
//                Intent i = new Intent(SplashActivity.this, TakeTour.class);
//                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
