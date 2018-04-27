package com.application.millipixels.expense_rocket.verify_otp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.application.millipixels.expense_rocket.api.VerifyOTPResponse;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;

import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

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
    String number;

    String token;

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

            number = getIntent().getStringExtra(OTP_NUMBER);
            String text = "We just sent a OTP to your \n mobile number "+ number.substring(0,2)+"***_***"+ number.substring(number.length() - 2) + ". Enter the \n OTP here to sign in.";
            txtOtp.setText(text);
        }

      otp=getIntent().getStringExtra("otp");

        if(otp!=null){
            setOTPInFields(otp);
        }




    }

    @OnClick(R.id.btnVerify)
    public void onVerify(View v){
        if (edt1.getText().toString().trim().length() == 1 && edt2.getText().toString().trim().length() == 1 && edt3.getText().toString().trim().length() == 1 && edt4.getText().toString().trim().length() == 1){


            verifyOTPRequest();

        }else {
            Snackbar.make(v,R.string.error_enter_otp,2000).show();
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
        edt1.addTextChangedListener(new GenericTextWatcher(edt1,edt2));
        edt2.addTextChangedListener(new GenericTextWatcher(edt2,edt3));
        edt3.addTextChangedListener(new GenericTextWatcher(edt3,edt4));
        edt4.addTextChangedListener(new GenericTextWatcher(edt4,edt1));
    }



    public void setOTPInFields(String OTP){


//        char firstDigit = OTP.charAt(0);
//        char secondDigit = OTP.charAt(1);
//        char thirdDigit = OTP.charAt(2);
//        char fourthDigit = OTP.charAt(3);
//
//
//        StringTokenizer st = new StringTokenizer(OTP);
//
//        String one=st.nextToken();
//        String two=st.nextToken();
//        String three=st.nextToken();
//        String four=st.nextToken();
//
//        edt1.setText(one);
//        edt2.setText(two);
//        edt3.setText(three);
//        edt4.setText(four);


    }

    public void verifyOTPRequest(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<VerifyOTPResponse> call = apiService.verifyOtp("2","3IpCWSVYd20Agpmra7ALyviJeRTEspFDDvyiRy61",otp,"+91"+number);


        call.enqueue(new retrofit2.Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {

                if(response.isSuccessful()==true){

                    if(response.body()!=null && response.body().isStatus()){
                        token=response.body().getData().getToken();

                        if(token!=null){
                            Intent intent = new Intent(VerifyOtpActity.this, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    }else {

                    }

                }else {

                }

            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                Log.d("Error",t.getLocalizedMessage());
            }
        });
    }

}
