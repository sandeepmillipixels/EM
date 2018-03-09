package com.example.millipixelsinteractive_031.em.verify_otp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.millipixelsinteractive_031.em.MainActivity;
import com.example.millipixelsinteractive_031.em.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnChangeNumber)
    public void onChangeNumber(){
        finish();
    }
    @OnClick(R.id.txtSkip)
    public void onSkipped(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
