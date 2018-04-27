package com.application.millipixels.expense_rocket.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.addexpense.AddExpense;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.onboarding.OnBoarding;

import butterknife.BindView;
import butterknife.OnClick;

import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 24/04/18.
 */

public class TakeTour extends Activity {


    @BindView(R.id.take_a_tour_btn)
    Button take_a_tour_btn;


    @BindView(R.id.let_get_started_btn)
    Button let_get_started_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.take_tour);

        ButterKnife.bind(this);


    }


    @OnClick(R.id.take_a_tour_btn)
    public void tourBtnClick(){

        Intent intent=new Intent(this, OnBoarding.class);
        startActivity(intent);
        finish();

    }

    @OnClick(R.id.let_get_started_btn)
    public void getStartedBtnClick(){

        Intent intent=new Intent(this, AddExpense.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }
        finish();

    }

}
