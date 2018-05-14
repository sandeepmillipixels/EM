package com.application.millipixels.expense_rocket;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.application.millipixels.expense_rocket.verify_otp.VerifyOtpActity;

/**
 * Created by millipixelsinteractive_024 on 26/04/18.
 */

public class GenericTextWatcher implements TextWatcher {


    Activity activity;

    private View view;
    EditText editText;
    public GenericTextWatcher(View view, EditText editText,Activity activity)
    {
        this.activity=activity;
        this.view = view;
        this.editText=editText;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // TODO Auto-generated method stub
        String text = editable.toString();
        switch(view.getId())
        {

            case R.id.edt1:
                if(text.length()==1)
                    editText.requestFocus();
                break;
            case R.id.edt2:
                if(text.length()==1)
                    editText.requestFocus();
                break;
            case R.id.edt3:
                if(text.length()==1)
                    editText.requestFocus();
                break;
            case R.id.edt4:
                VerifyOtpActity.hideKeyboard(activity);

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub



    }
}
