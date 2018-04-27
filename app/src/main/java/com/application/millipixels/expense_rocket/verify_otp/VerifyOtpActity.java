package com.application.millipixels.expense_rocket.verify_otp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.GenericTextWatcher;
import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.api.ApiClient;
import com.application.millipixels.expense_rocket.api.ApiInterface;
import com.application.millipixels.expense_rocket.api.OTP;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

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

    boolean ignoreChange = false;

    String otp;
    @BindView(R.id.txtOtp)
    TextView txtOtp;

    public static final String OTP_NUMBER = "otp_number";

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
        if (getIntent().getExtras() != null){
            String number = getIntent().getStringExtra(OTP_NUMBER);
            String text = "We just sent a OTP to your \n mobile number "+ number.substring(0,2)+"***_***"+ number.substring(number.length() - 2) + ". Enter the \n OTP here to sign in.";
            txtOtp.setText(text);
        }

//        sendOTPRequest();



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




    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getDisplayLabel()!=0) {

            onFoucus();

        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_DEL){

            edt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL && edt4.getText().toString().length()!=0 && edt4.hasFocus()) {
                    edt3.requestFocus();
                }
                return false;
            }
        });

        edt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL && edt3.getText().toString().length()!=0) {
                    edt2.requestFocus();
                }
                return false;
            }
        });

        edt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL && edt2.getText().toString().length()!=0) {
                    edt1.requestFocus();
                }
                return false;
            }
        });
}



        return super.dispatchKeyEvent(event);
    }


    public void onFoucus(){
        edt1.addTextChangedListener(new GenericTextWatcher(edt1,edt2));
        edt2.addTextChangedListener(new GenericTextWatcher(edt2,edt3));
        edt3.addTextChangedListener(new GenericTextWatcher(edt3,edt4));
        edt4.addTextChangedListener(new GenericTextWatcher(edt4,edt1));
    }


    public void sendOTPRequest(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<OTP> call = apiService.sendOtp("","","");

        call.enqueue(new Callback<OTP>() {
            @Override
            public void success(Result<OTP> result) {

                otp=result.data.getData().getOtp();
                setOTPInFields(otp);

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });


    }

    public void setOTPInFields(String OTP){
        char firstDigit = OTP.charAt(0);
        char secondDigit = OTP.charAt(1);
        char thirdDigit = OTP.charAt(2);
        char fourthDigit = OTP.charAt(3);


        edt1.setText(firstDigit);
        edt2.setText(secondDigit);
        edt3.setText(thirdDigit);
        edt4.setText(fourthDigit);


    }

}
