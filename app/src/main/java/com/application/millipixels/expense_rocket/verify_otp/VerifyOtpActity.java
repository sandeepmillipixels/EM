package com.application.millipixels.expense_rocket.verify_otp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.GenericTextWatcher;
import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.prefs.PrefrenceClass;
import com.application.millipixels.expense_rocket.utils.Constants;
import com.application.millipixels.expense_rocket.utils.Utilities;
import com.application.millipixels.models.LoginResponse;
import com.application.millipixels.models.VerifyOTPResponseRX;

import com.application.millipixels.network.NetworkClient;
import com.application.millipixels.network.NetworkInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class VerifyOtpActity extends Activity{

    @BindView(R.id.imgErrorBack)
    ImageView imgErrorBack;

    @BindView(R.id.txtError)
    TextView txtError;


    @BindView(R.id.sign_in_top_layout)
    RelativeLayout sign_in_top_layout;

    @BindView(R.id.top_layout)
    RelativeLayout top_layout;
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

    String otp;
    @BindView(R.id.txtOtp)
    TextView txtOtp;
    String number;

    String token;

    public static final String OTP_NUMBER = "otp_number";

    ProgressDialog dialog;



    char[] charArray;
    String firstDigit,secondDigit,thirdDigit,fourthDigit;

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
                number = getIntent().getStringExtra(OTP_NUMBER);
                String text = "We just sent a OTP to your \n mobile number " + number.substring(0, 2) + "***_***" + number.substring(number.length() - 2) + ". Enter the \n OTP here to sign in.";
                txtOtp.setText(text);
        }

      otp=getIntent().getStringExtra("otp");

        if(otp!=null){
            charArray=otp.toCharArray();
            setOTPInFields(otp);
        }

        dialog = new ProgressDialog(this);



    }

    @OnClick(R.id.btnVerify)
    public void onVerify(View v){
        sign_in_top_layout.setVisibility(View.GONE);
        if (edt1.getText().toString().trim().length() == 1 && edt2.getText().toString().trim().length() == 1 && edt3.getText().toString().trim().length() == 1 && edt4.getText().toString().trim().length() == 1){

           showProgress();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    verifyOTPRequest(otp);

                }
            },2000);


        }else {
            txtError.setText("Please enter valid OTP.");
            Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
            sign_in_top_layout.startAnimation(animation);
            sign_in_top_layout.setVisibility(View.VISIBLE);
        }

//        verifyOTPRequest();
//        getUserData(VerifyOtpActity.this);
    }

    public void sendOTPRequest(String num){
        Call<LoginResponse> call = NetworkClient.getRetrofit().create(NetworkInterface.class).getOtp(Constants.CLIENT_ID,Constants.CLIENT_SECRET,"+91"+num);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isStatus()){
                        showErrorMessage("One time Password has been sent\nto registered mobile number.");
                    }else {
                        showErrorMessage(response.body().getError().getError_message().getMessage().get(0));
                    }
                }
                else
                {
                    showErrorMessage(Utilities.getErrorMessage(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showErrorMessage(t.getLocalizedMessage());
            }
        });
        hideProgress();

    }
    public void verifyOTPRequest(String otp){
        Call<VerifyOTPResponseRX> call = NetworkClient.getRetrofit().create(NetworkInterface.class).verifyOtp(Constants.CLIENT_ID,Constants.CLIENT_SECRET,otp,"+91"+number);
        call.enqueue(new retrofit2.Callback<VerifyOTPResponseRX>() {
            @Override
            public void onResponse(Call<VerifyOTPResponseRX> call, Response<VerifyOTPResponseRX> response) {

                if (response.isSuccessful())
                {
                    if (response.body().getStatus()){
                        callNextActivity();
                    }else {
                        showErrorMessage(response.body().getError().getError_message().getMessage().get(0));
                    }
                }
                else
                {
                    showErrorMessage(Utilities.getErrorMessage(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponseRX> call, Throwable t) {
                showErrorMessage(t.getLocalizedMessage());
            }
        });
        hideProgress();

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
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    edt3.requestFocus();
                    edt4.setText("");
                }
                return false;
            }
        });

        edt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    edt2.requestFocus();
                    edt3.setText("");
                }
                return false;
            }
        });

        edt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    edt1.requestFocus();
                    edt2.setText("");
                }
                return false;
            }
        });



            edt1.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt1.setText("");


                    }
                    return false;
                }
            });

}

        return super.dispatchKeyEvent(event);
    }


    public void onFoucus(){
        edt1.addTextChangedListener(new GenericTextWatcher(edt1,edt2,VerifyOtpActity.this));
        edt2.addTextChangedListener(new GenericTextWatcher(edt2,edt3,VerifyOtpActity.this));
        edt3.addTextChangedListener(new GenericTextWatcher(edt3,edt4,VerifyOtpActity.this));
        edt4.addTextChangedListener(new GenericTextWatcher(edt4,edt1,VerifyOtpActity.this));
    }



    public void setOTPInFields(String OTP){

        for(int i=0;i<OTP.length();i++){

            firstDigit=""+charArray[0];
            secondDigit=""+charArray[1];
            thirdDigit=""+charArray[2];
            fourthDigit=""+charArray[3];

        }

            edt1.setText(firstDigit);
            edt2.setText(secondDigit);
            edt3.setText(thirdDigit);
            edt4.setText(fourthDigit);

            edt4.requestFocus();




    }

    @OnClick(R.id.btnNotReceived)
    public void resendOtp(){
        sign_in_top_layout.setVisibility(View.GONE);

        showProgress();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendOTPRequest(number);
            }
        },2000);
    }



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void showProgress() {

        dialog.setCancelable(false);

        dialog.setMessage("Please wait...");

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.show();

    }


    public void hideProgress() {

        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        edt4.setText("");

        edt1.requestFocus();

        dialog.dismiss();

    }



    public void showErrorMessage(String message){
        txtError.setText(message);
        Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
        sign_in_top_layout.startAnimation(animation);
        sign_in_top_layout.setVisibility(View.VISIBLE);
    }


    public void callNextActivity(){
        PrefrenceClass.saveInSharedPrefrence(VerifyOtpActity.this,"login",true);

        Intent intent = new Intent(VerifyOtpActity.this, Dashboard.class);
        intent.putExtra("login",true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
