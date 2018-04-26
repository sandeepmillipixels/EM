package com.application.millipixels.expense_rocket.verify_otp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActity extends Activity {

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

    @BindView(R.id.back_button_otp)
    ImageView back_button_otp;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);

        setContentView(R.layout.otp);
        ButterKnife.bind(this);

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

    @OnClick(R.id.back_button_otp)
    public void onBackBtnClick(){

        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
