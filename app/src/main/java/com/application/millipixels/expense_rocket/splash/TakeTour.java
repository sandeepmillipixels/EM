package com.application.millipixels.expense_rocket.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.addexpense.AddExpense;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.onboarding.OnBoarding;
import com.application.millipixels.expense_rocket.typeface.CustomTypefaceSpan;

import butterknife.BindView;
import butterknife.OnClick;

import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 24/04/18.
 */

public class TakeTour extends Activity {


    @BindView(R.id.take_a_tour_btn)
    Button take_a_tour_btn;

    @BindView(R.id.txtLearnMore)
    TextView txtLearnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.take_tour);

        ButterKnife.bind(this);


        Typeface font3 = Typeface.createFromAsset(getAssets(), "fonts/sfu_regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/sfu_bold.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/sfu_thin.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Learn more about Expense Rocket");

        SS.setSpan (new CustomTypefaceSpan("", font), 0, 16,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan (new CustomTypefaceSpan("", font2), 16, 24, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan (new CustomTypefaceSpan("", font3), 24, 29,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        txtLearnMore.setText(SS);


    }


    @OnClick(R.id.take_a_tour_btn)
    public void tourBtnClick(){

        Intent intent=new Intent(this, OnBoarding.class);
        startActivity(intent);

    }

    @OnClick(R.id.add_an_expense_button_tour)
    public void getStartedBtnClick(){

        Intent intent=new Intent(this, AddExpense.class);
        startActivity(intent);


    }


    @OnClick(R.id.signin_button_take_tour)
    public void signInButton(){
        Intent intent=new Intent(this, LoginSignupActivity.class);
        startActivity(intent);
    }

}
