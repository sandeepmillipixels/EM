package com.application.millipixels.expense_rocket.verify_otp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;

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
