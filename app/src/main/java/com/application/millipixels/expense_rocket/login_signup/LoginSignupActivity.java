package com.application.millipixels.expense_rocket.login_signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.facebook.AccessTokenManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

    @BindView(R.id.imgGPlus)
    ImageView imgGPlus;

    @BindView(R.id.imgFb)
    ImageView imgFb;
    @BindView(R.id.login_button)
    LoginButton loginButton;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;



    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
//    LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);

        initFacebook();
        initGplus();
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

    private void initGplus(){
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

    }
    private void initFacebook(){
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        // If you are using in a fragment, call loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
// Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("","");
                // App code
            }

            @Override
            public void onCancel() {
                // App code
                Log.e("","");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("","");
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("","");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        // Application code
                                        try {
                                            String email = object.getString("email");

//                                            String birthday = object.getString("birthday"); // 01/31/1980 format
                                            String name = object.getString("name"); // 01/31/1980 format
                                            Toast.makeText(LoginSignupActivity.this,"Welcome "+name,Toast.LENGTH_LONG).show();
                                            LoginManager.getInstance().logOut();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("","");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("","");
                    }
                });
    }


    @OnClick(R.id.imgFb)
    public void onFbTapped(){
        loginButton.performClick();
    }
    @OnClick(R.id.imgGPlus)
    public void onGPlusTapped(){
//        signInButton.performClick();
        signIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
// Pass the activity result to the saveSession button.

        super.onActivityResult(requestCode, resultCode, data);
    }
    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(LoginSignupActivity.this,"Welcome "+account.getDisplayName(),Toast.LENGTH_LONG).show();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
//
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        // [END on_start_sign_in]
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
