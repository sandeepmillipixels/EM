package com.example.millipixelsinteractive_031.em.login_signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.millipixelsinteractive_031.em.MainActivity;
import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.utils.Constants;
import com.example.millipixelsinteractive_031.em.utils.Utilities;
import com.example.millipixelsinteractive_031.em.verify_otp.VerifyOtpActity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class LoginSignupActivity extends Activity {

    EditText mobileEditText;
    EditText emailEditText;
    Button submitButton;
    String email;
    String mobileNumber;
    private List<String> country_list = new ArrayList<>();
    @BindView(R.id.spnCountary)
    AppCompatSpinner spnCountary;


    @BindView(R.id.spnCountarySignup)
    AppCompatSpinner spnCountarySignup;

    @BindView(R.id.txtLogin)
    TextView txtLogin;
    @BindView(R.id.txtSignup)
    TextView txtSignup;

    @BindView(R.id.login_container)
    LinearLayout login_container;

    @BindView(R.id.signup_container)
    LinearLayout signup_container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        country_list.add("India");
        country_list.add("New York");
        country_list.add("Sri Lanka");
        country_list.add("Singapore");
        country_list.add("Austria");
        setSpinner();
        setSpinnerCountry();
        if (getIntent().getExtras() != null){
            if (getIntent().getExtras().containsKey(Constants.LOGIN)){
                onLoginTapped();
            }else {
                onSignupTapped();
            }
        }
//        submitButton.setOnClickListener(submitButton_listener);

    }

    private void setSpinnerCountry() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCountarySignup.setAdapter(adapter);
        spnCountarySignup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.txtSignup)
    public void onSignupTapped(){
        txtSignup.setAlpha(1);
        txtLogin.setAlpha(0.6f);
        signup_container.setVisibility(View.VISIBLE);
        login_container.setVisibility(View.GONE);
    }

    @OnClick(R.id.txtSkip)
    public void onSkipped(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnSendOtpLogin)
    public void onSendOtpLogin(){
        Intent intent = new Intent(this, VerifyOtpActity.class);
        startActivity(intent);

    }

    @OnClick(R.id.btnSendOtpSignup)
    public void onSendOtpSignup(){
        Intent intent = new Intent(this, VerifyOtpActity.class);
        startActivity(intent);

    }

    @OnClick(R.id.txtLogin)
    public void onLoginTapped(){
        txtSignup.setAlpha(0.6f);
        txtLogin.setAlpha(1);
        signup_container.setVisibility(View.GONE);
        login_container.setVisibility(View.VISIBLE);
    }

    private void setSpinner(){
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCountary.setAdapter(adapter);
        spnCountary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private View.OnClickListener submitButton_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber=mobileEditText.getText().toString();
                email=emailEditText.getText().toString();

                if(mobileNumber.length()==0){

                }

                else if(!(mobileNumber.length() >0 && mobileNumber.length()>=10 && mobileNumber.length()<=13)){


                }

                else if(email.length()==0){

                }

                else if(!Utilities.emailValidation(email)){

                }

                else {

                }
            }
        };
}
