package com.application.millipixels.expense_rocket.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.application.millipixels.expense_rocket.R;

public class OnboardingPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {

        // Get page index from tag
        int pagePosition = (int) page.getTag();

        int pageWidth = (int) page.getWidth();
        float pageWidthTimesPosition = pageWidth * position;
        float absPosition = Math.abs(position);

        if (position <= -1.0f || position >= 1.0f) {

            // Page is not visible -- stop any running animations

        } else if (position == 0.0f) {

            // Page is selected -- reset any views if necessary

        } else {

            // Page is currently being swiped -- perform animations here

//
//
//            // Fragment 3 -- Feature
//            final View tv_feature_title = page.findViewById(R.id.tv_feature_title);
//            if (tv_feature_title != null) tv_feature_title.setAlpha(1.0f - absPosition * 2);
//
//            final View bike = page.findViewById(R.id.iv_bike);
//            if (bike != null) {
//                bike.setScaleX(1.0f - absPosition * 2);
//                bike.setScaleY(1.0f - absPosition * 2);
//                bike.setAlpha(1.0f - absPosition * 2);
//            }
//
//            final View bike_shadow = page.findViewById(R.id.iv_bike_shadow);
//            if (bike_shadow != null) {
//                bike_shadow.setScaleX(1.0f - absPosition * 2);
//                bike_shadow.setScaleY(1.0f - absPosition * 2);
//                bike_shadow.setAlpha(1.0f - absPosition * 2);
//            }

        }
    }
}
