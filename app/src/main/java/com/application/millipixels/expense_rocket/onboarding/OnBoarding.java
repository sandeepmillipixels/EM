package com.application.millipixels.expense_rocket.onboarding;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.addexpense.AddExpense;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class OnBoarding extends Activity{

    private int[] layouts;
    private TextView[] dots;

    @BindView(R.id.layoutDots)
    LinearLayout layoutDots;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.btn_skip)
    com.application.millipixels.expense_rocket.typeface.FontsClassLight btn_skip;

    @BindView(R.id.add_an_expense_button)
    Button add_an_expense_button;

    OnboardViewpagerAdapter onboardViewpagerAdapter;
    private static final String EMAIL = "email";

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

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

        setContentView(R.layout.onboarding);
        ButterKnife.bind(this);

        layouts = new int[]{
                R.layout.first_onboarding,
                R.layout.first_onboarding,
                R.layout.first_onboarding,
                R.layout.fourth_onboarding};

        addBottomDots(0);

        onboardViewpagerAdapter = new OnboardViewpagerAdapter(this, layouts);
        view_pager.setAdapter(onboardViewpagerAdapter);
        view_pager.addOnPageChangeListener(view_pagerPageChangeListener);

    }


    @OnClick(R.id.signin_button)
    public void onLoginTapped(){
        Intent intent = new Intent(this, LoginSignupActivity.class);
        intent.putExtra(Constants.LOGIN,true);
        startActivity(intent);
    }

    @OnClick(R.id.add_an_expense_button)
    public void getStartedClick(){
        Intent intent = new Intent(this, AddExpense.class);
        startActivity(intent);

    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener view_pagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {

                btn_skip.setVisibility(View.GONE);
                add_an_expense_button.setVisibility(View.VISIBLE);
                final Animation myAnim = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bounce);
                add_an_expense_button.startAnimation(myAnim);

            } else {
                // still pages are left
                add_an_expense_button.setVisibility(View.GONE);
                btn_skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    @OnClick(R.id.btn_skip)
    public void onSkip(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
