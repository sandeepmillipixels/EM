package com.example.millipixelsinteractive_031.em.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.millipixelsinteractive_031.em.MainActivity;
import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.login_signup.LoginSignupActivity;
import com.example.millipixelsinteractive_031.em.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class OnBoarding extends Activity {

    private int[] layouts;
    private TextView[] dots;

    @BindView(R.id.layoutDots)
    LinearLayout layoutDots;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.btn_skip)
    com.example.millipixelsinteractive_031.em.typeface.FontsClassLight btn_skip;

    OnboardViewpagerAdapter onboardViewpagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.onboarding);

        ButterKnife.bind(this);

        layouts = new int[]{
                R.layout.first_onboarding,
                R.layout.fourth_onboarding,
                R.layout.first_onboarding,
                R.layout.fourth_onboarding};

        addBottomDots(0);

        onboardViewpagerAdapter = new OnboardViewpagerAdapter(this, layouts);
        view_pager.setAdapter(onboardViewpagerAdapter);
        view_pager.addOnPageChangeListener(view_pagerPageChangeListener);

    }


    @OnClick(R.id.btnSignup)
    public void onSignupTapped(){
        Intent intent = new Intent(this, LoginSignupActivity.class);
        intent.putExtra(Constants.SIGN_UP,true);
        startActivity(intent);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginTapped(){
        Intent intent = new Intent(this, LoginSignupActivity.class);
        intent.putExtra(Constants.LOGIN,true);
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
                // last page. make button text to GOT IT
               // btn_skip.setVisibility(View.GONE);
            } else {
                // still pages are left

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
