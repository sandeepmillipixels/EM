package com.application.millipixels.expense_rocket.login_signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.adapter.SpinnerAdapter;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.model.CountryCodeData;
import com.application.millipixels.expense_rocket.utils.Constants;
import com.application.millipixels.expense_rocket.utils.Utilities;
import com.application.millipixels.expense_rocket.verify_otp.VerifyOtpActity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetui.TweetUi;

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

import static android.content.ContentValues.TAG;

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


    private FirebaseAuth mAuth;

    @BindView(R.id.twitter_login)
     TwitterLoginButton mLoginButton;

    @BindView(R.id.firebase_text)
    TextView firebase_text;

    @BindView(R.id.firebase_detail_text)
    TextView firebase_detail_text;

    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        // Configure Twitter SDK
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);

        setContentView(R.layout.activity_login_signup);

        mAuth = FirebaseAuth.getInstance();

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);


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

        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
                updateUI(null);
            }
        });

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


    @OnClick(R.id.twitter_login)
    public void twitterLogin(){

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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_twitter]
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);
        // [START_EXCLUDE silent]
        dialog.show();
        // [END_EXCLUDE]

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginSignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        dialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_twitter]

    private void signOut() {
        mAuth.signOut();
        TwitterCore.getInstance().getSessionManager().clearActiveSession();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        dialog.dismiss();
        if (user != null) {

            String email=user.getEmail();

            firebase_text.setText(getString(R.string.twitter_status_fmt, user.getDisplayName()));
            firebase_detail_text.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            Intent intent=new Intent(this,Dashboard.class);
            startActivity(intent);
            finish();

        } else {
            firebase_text.setText("SignOut");
            firebase_detail_text.setText(null);

        }
    }

}
