package com.example.millipixelsinteractive_031.em.verify_otp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.millipixelsinteractive_031.em.MainActivity;
import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.dashboard.Dashboard;
import com.example.millipixelsinteractive_031.em.receiver.OTPReceiver;
import com.example.millipixelsinteractive_031.em.view.SmsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActity extends AppCompatActivity {

    @BindView(R.id.btnVerify)
    Button btnVerify;
    @BindView(R.id.edt1)
    EditText edt1;

    @BindView(R.id.edt2)
    EditText edt2;

    @BindView(R.id.edt3)
    EditText edt3;

    @BindView(R.id.edt4)
    EditText edt4;

    @BindView(R.id.txtResendOtp)
    TextView txtResendOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);


//        OTPReceiver.bindListener(new SmsView() {
//
//            @Override
//            public void autoFillOTP(String otp) {
//
//                    char first = otp.charAt(0);
//                    char second = otp.charAt(1);
//                    char third = otp.charAt(2);
//                    char fourth = otp.charAt(3);
//
//                    first_digit_EditText.setText(String.valueOf(first));
//                    second_digit_EditText.setText(String.valueOf(second));
//                    third_digit_EditText.setText(String.valueOf(third));
//                    fourth_digit_EditText.setText(String.valueOf(fourth));
//
//
//                Log.e("OTP",otp);

//            }
//        });

    }
    @OnClick(R.id.btnChangeNumber)
    public void onChangeNumber(){
        finish();
    }

    @OnClick(R.id.txtResendOtp)
    public void onResendOtp(View v){
        Snackbar.make(v,"OTP sent to selected phone number.",2000).show();
    }


    @OnClick(R.id.btnVerify)
    public void onVerify(View v){
        if (edt1.getText().toString().trim().length() == 1 && edt2.getText().toString().trim().length() == 1 && edt3.getText().toString().trim().length() == 1 && edt4.getText().toString().trim().length() == 1){
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        }else {
            Snackbar.make(v,"Please enter OTP.",2000).show();
        }
    }
    @OnClick(R.id.txtSkip)
    public void onSkipped(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
