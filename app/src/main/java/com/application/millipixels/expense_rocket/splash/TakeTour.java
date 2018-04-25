package com.application.millipixels.expense_rocket.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.application.millipixels.expense_rocket.R;
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

        Intent intent=new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();

    }

}
