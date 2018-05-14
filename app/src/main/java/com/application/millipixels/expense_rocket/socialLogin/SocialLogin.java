package com.application.millipixels.expense_rocket.socialLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.prefs.PrefrenceClass;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SocialLogin {

    Activity activity;


    /////////////Facebook Login/////////////
    CallbackManager callbackManager;
    LoginButton loginButton;


    ////////////Twitter Login///////////////
    TwitterLoginButton twitterLoginButton;


    ///////////Google SignIn////////////////
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

    GoogleApiClient mGoogleApiClient;


    ///////////////////////////Facebook//////////////////////////////
    public SocialLogin(CallbackManager callbackManager, LoginButton loginButton, Activity activity){

        this.callbackManager=callbackManager;
        this.loginButton=loginButton;
        this.activity=activity;

        initFacebook();


    }

    //////////////////////////Twitter///////////////////////////////////
    public SocialLogin(Activity activity, CallbackManager callbackManager,TwitterLoginButton twitterLoginButton){

        this.callbackManager=callbackManager;
        this.activity=activity;
        this.twitterLoginButton=twitterLoginButton;

        initTwitter();


    }



    ////////////////////////////Google//////////////////////////////////
    public SocialLogin(Activity activity, GoogleSignInClient mGoogleSignInClient,SignInButton signInButton){

        this.activity=activity;
        this.mGoogleSignInClient=mGoogleSignInClient;
        this.signInButton=signInButton;

        initGplus();


    }

    ///////////////////////////Logout from google/////////////////////////////

    public SocialLogin(Activity activity){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();


        logoutFromGmail(activity);


    }






    public void initFacebook(){
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
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


                                            PrefrenceClass.saveInSharedPrefrence(activity,"login",true);
                                            PrefrenceClass.saveInSharedPrefrence(activity,"fb",true);

                                            String name = object.getString("name"); // 01/31/1980 format
                                            Toast.makeText(activity,"Welcome "+name,Toast.LENGTH_LONG).show();
                                            LoginManager.getInstance().logOut();

                                            callNextActivity();

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
        callbackManager();
    }


    public CallbackManager callbackManager(){

        return callbackManager;
    }

    public void initGplus(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        GoogleSignInClient();

    }


    public GoogleSignInClient GoogleSignInClient(){


        return mGoogleSignInClient;
    }




    public void initTwitter(){

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                activity.getString(R.string.twitter_consumer_key),
                activity.getString(R.string.twitter_consumer_secret));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(activity)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);

    }

    public void callNextActivity(){

        Intent intent = new Intent(activity, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();

    }

    public void logoutFromGmail(final Activity activity){


        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected( Bundle bundle) {

                FirebaseAuth.getInstance().signOut();
                if(mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult( Status status) {
                            if (status.isSuccess()) {
;
                                PrefrenceClass.saveInSharedPrefrence(activity,"onboard",true);
                                PrefrenceClass.saveInSharedPrefrence(activity,"login",false);

                                Intent i=new Intent(activity,LoginSignupActivity.class);
                                activity.startActivity(i);
                                activity.finish();

                                Toast.makeText(activity,"Logged Out",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d("Suspend", "Google API Client Connection Suspended");
            }
        });


    }



}
