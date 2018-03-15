package com.example.millipixelsinteractive_031.em.verify_otp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.millipixelsinteractive_031.em.MainActivity;
import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.dashboard.Dashboard;
import com.example.millipixelsinteractive_031.em.receiver.OTPReceiver;
import com.example.millipixelsinteractive_031.em.view.SmsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActity extends AppCompatActivity {

    @BindView(R.id.first_digit_EditText)
    EditText first_digit_EditText;

    @BindView(R.id.second_digit_EditText)
    EditText second_digit_EditText;

    @BindView(R.id.third_digit_EditText)
    EditText third_digit_EditText;

    @BindView(R.id.fourth_digit_EditText)
    EditText fourth_digit_EditText;

    @BindView(R.id.btnVerify)
    Button btnVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);


        OTPReceiver.bindListener(new SmsView() {

            @Override
            public void autoFillOTP(String otp) {

                    char first = otp.charAt(0);
                    char second = otp.charAt(1);
                    char third = otp.charAt(2);
                    char fourth = otp.charAt(3);

                    first_digit_EditText.setText(String.valueOf(first));
                    second_digit_EditText.setText(String.valueOf(second));
                    third_digit_EditText.setText(String.valueOf(third));
                    fourth_digit_EditText.setText(String.valueOf(fourth));


                Log.e("OTP",otp);

            }
        });

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

    @OnClick(R.id.btnVerify)
    public void verify(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
