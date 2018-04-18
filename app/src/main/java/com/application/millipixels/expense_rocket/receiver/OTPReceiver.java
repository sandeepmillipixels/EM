package com.application.millipixels.expense_rocket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.application.millipixels.expense_rocket.view.SmsView;

/**
 * Created by millipixelsinteractive_031 on 08/03/18.
 */

public class OTPReceiver extends BroadcastReceiver {


    private static SmsView mListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();

            String messageBody = smsMessage.getMessageBody();
          //  messageBody = messageBody.substring(0, messageBody.length()-1);

            mListener.autoFillOTP(messageBody);
        }

    }
    public static void bindListener(SmsView listener) {
        mListener = listener;
    }

}
