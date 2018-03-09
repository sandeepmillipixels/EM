package com.example.millipixelsinteractive_031.em.onboarding;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by millipixelsinteractive_024 on 04/09/17.
 */

public class OnboardViewpagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;

    Context context ;
    private int[] layouts;
    public OnboardViewpagerAdapter(Context context, int[] layouts) {
        this.context = context ;
        this.layouts = layouts ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

