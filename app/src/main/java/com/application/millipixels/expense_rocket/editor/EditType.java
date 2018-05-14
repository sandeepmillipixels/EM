package com.application.millipixels.expense_rocket.editor;

import com.application.millipixels.expense_rocket.R;

/**
 * Created by millipixelsinteractive_024 on 23/04/18.
 */

public enum EditType {
    Crop(R.drawable.ic_crop),
    Rotate(R.drawable.ic_rotate),
    Saturation(R.drawable.ic_saturation),
    Brightness(R.drawable.ic_brightness),
    Contrast(R.drawable.ic_contrast);

    public int VALUE;

    EditType(int VALUE) {
        this.VALUE = VALUE;
    }
}