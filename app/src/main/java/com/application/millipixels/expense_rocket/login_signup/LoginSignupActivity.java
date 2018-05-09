package com.application.millipixels.expense_rocket.login_signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.MainActivity;
import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.adapter.SpinnerAdapter;
import com.application.millipixels.expense_rocket.api.ApiClient;
import com.application.millipixels.expense_rocket.api.ApiInterface;
import com.application.millipixels.expense_rocket.api.OTP;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.model.CountryCodeData;
import com.application.millipixels.expense_rocket.utils.Constants;
import com.application.millipixels.expense_rocket.utils.Utilities;
import com.application.millipixels.expense_rocket.verify_otp.VerifyOtpActity;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;

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
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


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
import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class LoginSignupActivity extends AppCompatActivity {

//    @BindView(R.id.edtPhone)
//    EditText edtPhone;

//    @BindView(R.id.edtEmail)
//    EditText edtEmail;
@BindView(R.id.imgErrorBack)
ImageView imgErrorBack;

    @BindView(R.id.txtError)
    TextView txtError;


    @BindView(R.id.sign_in_top_layout)
    LinearLayout sign_in_top_layout;

    @BindView(R.id.top_layout)
    RelativeLayout top_layout;

    @BindView(R.id.edtPhoneLogin)
    EditText edtPhoneLogin;

    String email;
    String mobileNumber;



    String json = null;

    SpinnerAdapter adapter;
    List<CountryCodeData> data;

    @BindView(R.id.imgGPlus)
    ImageView imgGPlus;

    @BindView(R.id.imgTwiiter)
    ImageView imgTwiiter;

    @BindView(R.id.imgFb)
    ImageView imgFb;

    @BindView(R.id.login_button)
    LoginButton loginButton;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    @BindView(R.id.main_layout)
    RelativeLayout parentLayout;


    String otp;


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private GoogleSignInClient mGoogleSignInClient;


    private FirebaseAuth mAuth;

    @BindView(R.id.twitter_login)
    TwitterLoginButton mLoginButton;

    @BindView(R.id.back_button_otp)
    ImageView back_button_otp;



    SharedPreferences prefs;

    SharedPreferences.Editor editor;

    ProgressDialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);

        FirebaseApp.initializeApp(this);
        // Configure Twitter SDK
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);

        setContentView(R.layout.sign_in);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);


        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();



        initFacebook();
        initGplus();

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


    @OnClick(R.id.back_button_otp)
    public void backButtonClick(){
        finish();
    }

    @OnClick(R.id.imgTwiiter)
    public void onTwiiterTapped(){
        mLoginButton.performClick();
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


                                            editor.putBoolean("login",true);
                                            editor.commit();


//                                            String birthday = object.getString("birthday"); // 01/31/1980 format
                                            String name = object.getString("name"); // 01/31/1980 format
                                            Toast.makeText(LoginSignupActivity.this,"Welcome "+name,Toast.LENGTH_LONG).show();
                                            LoginManager.getInstance().logOut();
                                            Intent intent = new Intent(LoginSignupActivity.this, Dashboard.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();

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

        // Pass the activity result to the Twitter login button.
        if (requestCode == 140)
        mLoginButton.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUIGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUIGoogle(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void updateUIGoogle(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(LoginSignupActivity.this,"Welcome "+account.getDisplayName(),Toast.LENGTH_LONG).show();

            editor.putBoolean("login",true);
            editor.commit();



            Intent intent = new Intent(this, Dashboard.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
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
        updateUIGoogle(account);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        // [END on_start_sign_in]
    }





    @OnClick(R.id.twitter_login)
    public void twitterLogin(){

    }

    @OnClick(R.id.btnSendOtpLogin)
    public void onSendOtpLogin(View v){



        mobileNumber = edtPhoneLogin.getText().toString().trim();
        if(mobileNumber.length()==0){
//            Snackbar.make(v,R.string.error_empty_number,2000).show();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_down);
            txtError.setText("Please enter phone number.");
            sign_in_top_layout.startAnimation(animation);
            sign_in_top_layout.setVisibility(View.VISIBLE);
        }
        else if(!(mobileNumber.length() >0 && mobileNumber.length()>=10 && mobileNumber.length()<=13)){
//            Snackbar.make(v,R.string.error_not_valid_number,2000).show();
            txtError.setText("Please enter valid phone number.");
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_down);
            sign_in_top_layout.startAnimation(animation);
            sign_in_top_layout.setVisibility(View.VISIBLE);
        }else {
            sign_in_top_layout.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
    }

//    private void setSpinner(){
//
//        loadJSONFromAsset();
//
//        try {
//            data=new ArrayList<>();
//
//            JSONArray jArray = new JSONArray(json);
//
//            for(int i=0;i<jArray.length();i++){
//                JSONObject json_data = jArray.getJSONObject(i);
//                CountryCodeData codeData = new CountryCodeData();
//                codeData.setDial_code(json_data.getString("dial_code"));
//                codeData.setCode(json_data.getString("code"));
//                codeData.setName(json_data.getString("name"));
//                data.add(codeData);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        adapter = new SpinnerAdapter(this,
//                R.layout.custom_country_item,
//                data);
//
//        spnCountarySignup.setAdapter(adapter);
//
//
//        spnCountarySignup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                edtCode.setText(data.get(i).getDial_code());
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//    }

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


    // [END on_start_check_user]


    // [END on_activity_result]

    // [START auth_with_twitter]
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);
        // [START_EXCLUDE silent]
        if (dialog != null && dialog.isShowing())
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
                        if (dialog != null && dialog.isShowing())
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
        if (dialog != null && dialog.isShowing())
        dialog.dismiss();
        if (user != null) {

            String email=user.getEmail();


            editor.putBoolean("login",true);
            editor.commit();

//
//            firebase_text.setText(getString(R.string.twitter_status_fmt, user.getDisplayName()));
//            firebase_detail_text.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            Intent intent=new Intent(this,Dashboard.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } else {
//            firebase_text.setText("SignOut");
//            firebase_detail_text.setText(null);

        }
    }
    public void sendOTPRequest(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<OTP> call = apiService.sendOtp("1","gUCrznkTcnsHakR9vcp8hEyBhTMKXMO7uTtl19ei","+91"+mobileNumber);


        call.enqueue(new retrofit2.Callback<OTP>() {
            @Override
            public void onResponse(Call<OTP> call, Response<OTP> response) {

                if(response.isSuccessful()==true){
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    if(response.body()!=null && response.body().isStatus()){
                        otp=response.body().getData().getOtp();

                        Intent intent=new Intent(LoginSignupActivity.this,VerifyOtpActity.class);
                        intent.putExtra("otp",otp);
                        intent.putExtra(VerifyOtpActity.OTP_NUMBER,mobileNumber);
                        startActivity(intent);
                    }else {

//                        showErrorMessage(response.body().getError().getError_message());

                    }

                }else {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    // server error
                    showErrorMessage("Server Error. Try again Later.");
                }

            }

            @Override
            public void onFailure(Call<OTP> call, Throwable t) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                showErrorMessage("Server Error. Try again Later.");

            }
        });

    }

    public void showErrorMessage(String message){


        txtError.setText(message);
        Animation animation = AnimationUtils.loadAnimation(LoginSignupActivity.this, R.anim.scale_down);
        sign_in_top_layout.startAnimation(animation);
        sign_in_top_layout.setVisibility(View.VISIBLE);

    }

}
