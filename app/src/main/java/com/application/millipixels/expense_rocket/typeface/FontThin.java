package com.application.millipixels.expense_rocket.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by millipixelsinteractive_031 on 09/03/18.
 */

public class FontThin extends android.support.v7.widget.AppCompatTextView {


    public FontThin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRegular();

    }

    public FontThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRegular();

    }

    public FontThin(Context context) {
        super(context);
        initRegular();

    }

    public void initRegular() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/sfu_thin.ttf");
        setTypeface(tf ,1);

    }


}
