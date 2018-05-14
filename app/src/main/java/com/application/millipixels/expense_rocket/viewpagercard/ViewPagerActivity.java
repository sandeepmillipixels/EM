package com.application.millipixels.expense_rocket.viewpagercard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.application.millipixels.expense_rocket.R;

public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

//    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        mButton = (Button) findViewById(R.id.cardTypeBtn);
//        ((CheckBox) findViewById(R.id.checkBox)).setOnCheckedChangeListener(this);
       // mButton.setOnClickListener(this);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.where_does_all_money, R.string.we_designed_experience));
        mCardAdapter.addCardItem(new CardItem(R.string.where_is_the_receipt, R.string.never_lose_a_receipt));
        mCardAdapter.addCardItem(new CardItem(R.string.did_i_mail, R.string.oops_moment));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(80, 10, 80, 10);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        mViewPager.setPageMargin(40);
    }

    @Override
    public void onClick(View view) {
        if (!mShowingFragments) {
         //   mButton.setText("Views");
            mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {
          //  mButton.setText("Fragments");
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }

        mShowingFragments = !mShowingFragments;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
}