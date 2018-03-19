package com.example.millipixelsinteractive_031.em.login_signup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.millipixelsinteractive_031.em.MainActivity;
import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.adapter.SpinnerAdapter;
import com.example.millipixelsinteractive_031.em.dashboard.Dashboard;
import com.example.millipixelsinteractive_031.em.model.CountryCodeData;
import com.example.millipixelsinteractive_031.em.utils.Constants;
import com.example.millipixelsinteractive_031.em.utils.Utilities;
import com.example.millipixelsinteractive_031.em.verify_otp.VerifyOtpActity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class LoginSignupActivity extends Activity {

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPhoneLogin)
    EditText edtPhoneLogin;

    String email;
    String mobileNumber;
    private List<String> country_list = new ArrayList<>();


    @BindView(R.id.divider_line)
    View divider_line;

    @BindView(R.id.edtCode)
    EditText edtCode;

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

    String json = null;

    SpinnerAdapter adapter;
    List<CountryCodeData> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        setSpinner();
        if (getIntent().getExtras() != null){
            if (getIntent().getExtras().containsKey(Constants.LOGIN)){
                onLoginTapped();
            }else {
                onSignupTapped();
            }
        }

        loadJSONFromAsset();
//        submitButton.setOnClickListener(submitButton_listener);

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
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnSendOtpLogin)
    public void onSendOtpLogin(View v){
        mobileNumber = edtPhoneLogin.getText().toString().trim();
        if(mobileNumber.length()==0){
            Snackbar.make(v,"Please enter phone number.",2000).show();
        }
        else if(!(mobileNumber.length() >0 && mobileNumber.length()>=10 && mobileNumber.length()<=13)){
            Snackbar.make(v,"Please enter valid phone number.",2000).show();
        }else {
            Intent intent = new Intent(this, VerifyOtpActity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnSendOtpSignup)
    public void onSendOtpSignup(View v){
        mobileNumber=edtPhone.getText().toString().trim();
        email=edtEmail.getText().toString().trim();

        if(mobileNumber.length()==0){
            Snackbar.make(v,"Please enter phone number.",2000).show();
        }
        else if(!(mobileNumber.length() >0 && mobileNumber.length()>=10 && mobileNumber.length()<=13)){
            Snackbar.make(v,"Please enter valid phone number.",2000).show();
        }
        else if(email.length()==0){
            Snackbar.make(v,"Please enter email address.",2000).show();
        }
        else if(!Utilities.emailValidation(email)){
            Snackbar.make(v,"Please enter valid email address.",2000).show();
        }
        else {
            Intent intent = new Intent(this, VerifyOtpActity.class);
            startActivity(intent);
        }


    }

    @OnClick(R.id.txtLogin)
    public void onLoginTapped(){
        mobileNumber = edtPhoneLogin.getText().toString().trim();
        txtSignup.setAlpha(0.6f);
        txtLogin.setAlpha(1);
        signup_container.setVisibility(View.GONE);
        login_container.setVisibility(View.VISIBLE);
    }

    private void setSpinner(){

        loadJSONFromAsset();

        try {
            data=new ArrayList<>();

            JSONArray jArray = new JSONArray(json);

            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                CountryCodeData codeData = new CountryCodeData();
                codeData.setDial_code(json_data.getString("dial_code"));
                codeData.setCode(json_data.getString("code"));
                codeData.setName(json_data.getString("name"));
                data.add(codeData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SpinnerAdapter(this,
                R.layout.custom_country_item,
                data);

        spnCountarySignup.setAdapter(adapter);


        spnCountarySignup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                edtCode.setText(data.get(i).getDial_code());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public String loadJSONFromAsset() {

        try {
            InputStream is = getAssets().open("country_codes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
