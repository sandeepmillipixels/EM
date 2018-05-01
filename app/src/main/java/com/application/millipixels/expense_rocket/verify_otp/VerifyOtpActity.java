package com.application.millipixels.expense_rocket.verify_otp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
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
import com.application.millipixels.expense_rocket.api.ApiClient;
import com.application.millipixels.expense_rocket.api.ApiInterface;
import com.application.millipixels.expense_rocket.api.OTP;
import com.application.millipixels.expense_rocket.api.VerifyOTPResponse;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.prefs.PrefrenceClass;

import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class VerifyOtpActity extends Activity {

    @BindView(R.id.imgErrorBack)
    ImageView imgErrorBack;

    @BindView(R.id.txtError)
    TextView txtError;


    @BindView(R.id.sign_in_top_layout)
    LinearLayout sign_in_top_layout;

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

    boolean ignoreChange = false;

    String otp;
    @BindView(R.id.txtOtp)
    TextView txtOtp;
    String number;

    String token;

    public static final String OTP_NUMBER = "otp_number";

    ProgressDialog dialog;


//    SharedPreferences prefs;
//
//    SharedPreferences.Editor editor;


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

//        prefs= PreferenceManager.getDefaultSharedPreferences(this);
//        editor=prefs.edit();


        dialog = new ProgressDialog(this);


    }

    @OnClick(R.id.btnVerify)
    public void onVerify(View v){
        sign_in_top_layout.setVisibility(View.GONE);
        if (edt1.getText().toString().trim().length() == 1 && edt2.getText().toString().trim().length() == 1 && edt3.getText().toString().trim().length() == 1 && edt4.getText().toString().trim().length() == 1){
            dialog.setCancelable(false);

            dialog.setMessage("Please wait...");

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    verifyOTPRequest();
                }
            },2000);


        }else {
//            Snackbar.make(v,R.string.error_enter_otp,2000).show();
            txtError.setText("Please enter valid OTP.");
            Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
            sign_in_top_layout.startAnimation(animation);
            sign_in_top_layout.setVisibility(View.VISIBLE);
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
        edt1.addTextChangedListener(new GenericTextWatcher(edt1,edt2,VerifyOtpActity.this));
        edt2.addTextChangedListener(new GenericTextWatcher(edt2,edt3,VerifyOtpActity.this));
        edt3.addTextChangedListener(new GenericTextWatcher(edt3,edt4,VerifyOtpActity.this));
        edt4.addTextChangedListener(new GenericTextWatcher(edt4,edt1,VerifyOtpActity.this));
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

                            PrefrenceClass.saveInSharedPrefrence(VerifyOtpActity.this,"login",true);

//                            editor.putBoolean("login",true);
//                            editor.commit();

                            Intent intent = new Intent(VerifyOtpActity.this, Dashboard.class);
                            intent.putExtra("login",true);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    }else {
                        txtError.setText(response.body().getError().getError_message());
                        Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
                        sign_in_top_layout.startAnimation(animation);
                        sign_in_top_layout.setVisibility(View.VISIBLE);
                    }

                }else {
                    txtError.setText("Server Error. Try again Later.");
                    Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
                    sign_in_top_layout.startAnimation(animation);
                    sign_in_top_layout.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                Log.d("Error",t.getLocalizedMessage());
                txtError.setText("Server Error. Try again Later.");
                Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
                sign_in_top_layout.startAnimation(animation);
                sign_in_top_layout.setVisibility(View.VISIBLE);
            }
        });
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @OnClick(R.id.btnNotReceived)
    public void resendOtp(){
        sign_in_top_layout.setVisibility(View.GONE);
        dialog.setCancelable(false);

        dialog.setMessage("Please wait...");

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendOTPRequest();
            }
        },2000);
    }

    public void sendOTPRequest(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<OTP> call = apiService.sendOtp("2","3IpCWSVYd20Agpmra7ALyviJeRTEspFDDvyiRy61","+91"+number);


        call.enqueue(new retrofit2.Callback<OTP>() {
            @Override
            public void onResponse(Call<OTP> call, Response<OTP> response) {

                if(response.isSuccessful()==true){

                    if(response.body()!=null && response.body().isStatus()){
                        otp=response.body().getData().getOtp();



                    }else {
                        txtError.setText(response.body().getError().getError_message());
                        Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
                        sign_in_top_layout.startAnimation(animation);
                        sign_in_top_layout.setVisibility(View.VISIBLE);
                    }

                }else {
                    // server error
                    txtError.setText("Server Error. Try again Later.");
                    Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
                    sign_in_top_layout.startAnimation(animation);
                    sign_in_top_layout.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onFailure(Call<OTP> call, Throwable t) {
//                Log.d("Error",t.getLocalizedMessage());
                txtError.setText("Server Error. Try again Later.");
                Animation animation = AnimationUtils.loadAnimation(VerifyOtpActity.this, R.anim.scale_down);
                sign_in_top_layout.startAnimation(animation);
                sign_in_top_layout.setVisibility(View.VISIBLE);
            }
        });
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

                edt1.setText("");
                edt2.setText("");
                edt3.setText("");
                edt4.setText("");

        edt1.requestFocus();

    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
