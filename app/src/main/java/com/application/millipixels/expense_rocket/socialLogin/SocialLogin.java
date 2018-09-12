package com.application.millipixels.expense_rocket.socialLogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.prefs.PrefrenceClass;
import com.application.millipixels.expense_rocket.utils.Constants;
import com.application.millipixels.expense_rocket.utils.Utilities;
import com.application.millipixels.expense_rocket.utils.Utility;
import com.application.millipixels.expense_rocket.verify_otp.VerifyOtpActity;
import com.application.millipixels.models.LoginResponse;
import com.application.millipixels.models.RegisterResponse;
import com.application.millipixels.network.NetworkClient;
import com.application.millipixels.network.NetworkInterface;
import com.facebook.AccessToken;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static String userName;
    public static String userEmail;
    public static String userPhoto_url;
    String social_token;


    ///////////////////////////Facebook//////////////////////////////
    public SocialLogin(CallbackManager callbackManager, LoginButton loginButton, Activity activity){

        this.callbackManager=callbackManager;
        this.loginButton=loginButton;
        this.activity=activity;

        initFacebook(activity);


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






    public void initFacebook(final Context context){
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


                                        try {
                                            userEmail = object.getString("email");
                                            social_token = String.valueOf(AccessToken.getCurrentAccessToken());


                                            PrefrenceClass.saveInSharedPrefrence(activity,"login",true);
                                            PrefrenceClass.saveInSharedPrefrence(activity,"fb",true);

                                            userPhoto_url=object.getString("picture");

                                            userName = object.getString("name"); // 01/31/1980 format
                                            Toast.makeText(activity,"Welcome "+userName,Toast.LENGTH_LONG).show();
                                            LoginManager.getInstance().logOut();

//                                            callNextActivity();
                                            postSocialLoginData(context,"facebook",userName,userEmail,userPhoto_url,social_token);

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


    public void postSocialLoginData(final Context context, final String loginType,String name,String email,String photoUri,String token){
        // Application code

        if(userEmail==null && userName==null && social_token==null){
            userEmail=email;
            userName=name;
            social_token=token;

        }

        if(userPhoto_url==null ){
            userPhoto_url=photoUri;
        }

        Call<RegisterResponse> call = NetworkClient.getRetrofit().create(NetworkInterface.class).registeration(Constants.CLIENT_ID,Constants.CLIENT_SECRET, Utility.deviceID(context),"deviceToken",Utility.deviceType(),userName,userEmail,loginType,social_token,"");

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if(response.isSuccessful()){
                    if(loginType!=null && loginType.equals("google")){
                        PrefrenceClass.saveInSharedPrefrence(context,"gmail",true);
                    }else if(loginType!=null && loginType.equals("twitter")){
                        PrefrenceClass.saveInSharedPrefrence(context,"twitter",true);
                    }

                    Log.d("Request Success",response.body().getData().getToken());

                    userEmail=null;
                    userName=null;
                    social_token=null;

                    callNextActivity();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
             Log.d("Request Fail",t.getLocalizedMessage());
            }
        });

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

        PrefrenceClass.saveInSharedPrefrence(activity,"login",true);
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
