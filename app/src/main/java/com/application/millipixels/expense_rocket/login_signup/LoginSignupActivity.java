package com.application.millipixels.expense_rocket.login_signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.MainActivity;
import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.prefs.PrefrenceClass;
import com.application.millipixels.expense_rocket.socialLogin.SocialLogin;
import com.application.millipixels.expense_rocket.utils.Constants;
import com.application.millipixels.expense_rocket.utils.Utilities;
import com.application.millipixels.expense_rocket.verify_otp.VerifyOtpActity;
import com.application.millipixels.models.LoginResponse;
import com.application.millipixels.network.NetworkClient;
import com.application.millipixels.network.NetworkInterface;

import com.application.millipixels.ui.view.LoginViewInterface;
import com.facebook.AccessToken;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class LoginSignupActivity extends AppCompatActivity {

    @BindView(R.id.imgErrorBack)
    ImageView imgErrorBack;


    @BindView(R.id.txtError)
    TextView txtError;


    @BindView(R.id.sign_in_top_layout)
    RelativeLayout sign_in_top_layout;


    @BindView(R.id.top_layout)
    RelativeLayout top_layout;


    @BindView(R.id.edtPhoneLogin)
    EditText edtPhoneLogin;


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


    @BindView(R.id.twitter_login)
    TwitterLoginButton mLoginButton;


    @BindView(R.id.back_button_otp)
    ImageView back_button_otp;





    String mobileNumber;
    String json = null;
    String otp;

    CallbackManager callbackManager;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;


    ProgressDialog dialog;

    SocialLogin socialLogin;

    String name;
     String email;
    String photo_url;

    String googleToken=null;

    String accountName;


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


        setContentView(R.layout.sign_in);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);


        loadJSONFromAsset();


        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                PrefrenceClass.saveInSharedPrefrence(LoginSignupActivity.this,"twitter",true);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
                updateUI(null);
            }
        });

    }


    @OnClick(R.id.back_button_otp)
    public void backButtonClick(){
        finish();
    }

    @OnClick(R.id.imgTwiiter)
    public void onTwiiterTapped(){
        socialLoginTwitter();
        mLoginButton.performClick();
    }


    @OnClick(R.id.imgFb)
    public void onFbTapped(){

        socialLoginFacebook();

        loginButton.performClick();
    }
    @OnClick(R.id.imgGPlus)
    public void onGPlusTapped(){

        socialLoginGmail();
        signIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else if (requestCode == 140) {
            mLoginButton.onActivityResult(requestCode, resultCode, data);

        }

        else {
            if (callbackManager != null) {
                callbackManager.onActivityResult(requestCode, resultCode, data);

            }
        }

        // Pass the activity result to the Twitter login button.

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUIGoogle(account);
        } catch (ApiException e) {

            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUIGoogle(null);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUIGoogle(@Nullable GoogleSignInAccount account) {
        if (account != null) {

 name=account.getDisplayName();
 email=account.getEmail();
 photo_url=String.valueOf(account.getPhotoUrl());


 new RetrieveTokenTask().execute(email);
 



//            Toast.makeText(LoginSignupActivity.this,"Welcome "+account.getDisplayName(),Toast.LENGTH_LONG).show();
//
//            PrefrenceClass.saveInSharedPrefrence(this,"gmail",true);


//            callNextActivity();

        }
    }
    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUIGoogle(account);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }


    @Override
    protected void onStop() {
        super.onStop();


    }

    @OnClick(R.id.twitter_login)
    public void twitterLogin(){

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @OnClick(R.id.btnSendOtpLogin)
    public void onSendOtpLogin(View v){

//        Intent intent=new Intent(LoginSignupActivity.this,VerifyOtpActity.class);
//        intent.putExtra("otp","2134");
//        intent.putExtra(VerifyOtpActity.OTP_NUMBER,"8699769704");
//        startActivity(intent);

//
        mobileNumber = edtPhoneLogin.getText().toString().trim();
        if(mobileNumber.length()==0){
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_down);
            txtError.setText("Please enter phone number.");
            sign_in_top_layout.startAnimation(animation);
            sign_in_top_layout.setVisibility(View.VISIBLE);
        }
        else if(!(mobileNumber.length() >0 && mobileNumber.length()>=10 && mobileNumber.length()<=13)){
            txtError.setText("Please enter valid phone number.");
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_down);
            sign_in_top_layout.startAnimation(animation);
            sign_in_top_layout.setVisibility(View.VISIBLE);
        }else {
            sign_in_top_layout.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            showProgress();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendOTPRequest(mobileNumber);
                }
            },2000);

        }
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

    private void handleTwitterSession(TwitterSession session) {

        if (dialog != null && dialog.isShowing())
        dialog.show();

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
                            Toast.makeText(LoginSignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                        if (dialog != null && dialog.isShowing())
                        dialog.dismiss();

                    }
                });
    }

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
            String name=user.getDisplayName();
            String photoUri=String.valueOf(user.getPhotoUrl());
            String token=user.getUid();
            socialLoginTwitter();

            socialLogin.postSocialLoginData(this,"twitter",name,email,photoUri,token);
//
//            PrefrenceClass.saveInSharedPrefrence(this,"login",true);
//
//            callNextActivity();

        }
    }
    public void sendOTPRequest(String num){
        Call<LoginResponse> call = NetworkClient.getRetrofit().create(NetworkInterface.class).getOtp(Constants.CLIENT_ID,Constants.CLIENT_SECRET,"+91"+num);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful())
                {
                   if (response.body().isStatus()){
                       otp=response.body().getData().getOtp();
                       Intent intent=new Intent(LoginSignupActivity.this,VerifyOtpActity.class);
                       intent.putExtra("otp",otp);
                       intent.putExtra(VerifyOtpActity.OTP_NUMBER,mobileNumber);
                       startActivity(intent);
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

    public void showErrorMessage(String message){

        txtError.setText(message);
        Animation animation = AnimationUtils.loadAnimation(LoginSignupActivity.this, R.anim.scale_down);
        sign_in_top_layout.startAnimation(animation);
        sign_in_top_layout.setVisibility(View.VISIBLE);

    }


    public void showProgress() {

        dialog.setCancelable(false);

        dialog.setMessage("Please wait...");

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.show();

    }


    public void hideProgress() {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }

    }



    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";

            try {
                googleToken = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {

            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            return googleToken;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            socialLoginGmail();
            socialLogin.postSocialLoginData(LoginSignupActivity.this,"google",name,email,photo_url,googleToken);

        }
    }



    public void callNextActivity(){

        PrefrenceClass.saveInSharedPrefrence(this,"login",true);

        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void socialLoginGmail(){
        socialLogin=new SocialLogin(this,mGoogleSignInClient,signInButton);
        socialLogin.initGplus();
        mGoogleSignInClient=socialLogin.GoogleSignInClient();
    }


    public void socialLoginTwitter(){
        socialLogin=new SocialLogin(this,callbackManager,mLoginButton);
        socialLogin.initTwitter();
        callbackManager=socialLogin.callbackManager();

    }


    public void socialLoginFacebook(){
        socialLogin=new SocialLogin(callbackManager,loginButton,this);
       // socialLogin.initFacebook(this);
        callbackManager=socialLogin.callbackManager();
    }
}
