package com.example.millipixelsinteractive_031.em;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.millipixelsinteractive_031.em.receiver.OTPReceiver;
import com.example.millipixelsinteractive_031.em.view.SmsView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        autoFillOTP();

    }

    public void autoFillOTP() {

        OTPReceiver.bindListener(new SmsView() {

            @Override
            public void autoFillOTP(String otp) {

                if (otp!=null && otp.matches("[0-9]+")) {



                }

                Log.e("OTP",otp);

            }
        });


    }
}
