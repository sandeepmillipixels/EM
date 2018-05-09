package com.application.millipixels.expense_rocket.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by millipixelsinteractive_031 on 09/03/18.
 */

public class FontButton extends android.support.v7.widget.AppCompatButton {
    public FontButton(Context context) {
        super(context);
        initRegular();
    }

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRegular();
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRegular();
    }

    public void initRegular() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/sfu_bold.ttf");
        setTypeface(tf ,1);

    }
}
