package com.application.millipixels.expense_rocket.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.gallery.GalleyActivity;
import com.application.millipixels.expense_rocket.onboarding.OnBoarding;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    @BindView(R.id.splash_textView)
    TextView splash_textView;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Roboto_Light.ttf");
        splash_textView.setTypeface(typeFace);
        splash_textView.setText(Html.fromHtml("Keep eye on your <br>every <b>expense</b>"));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, OnBoarding.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
