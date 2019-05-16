package com.app.boxee.shopper.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.app.boxee.shopper.receiver.SmsListener;

public class OtpVerificationReceiver extends BroadcastReceiver {
    public static final String TAG=OtpVerificationReceiver.class.getName();
    private static SmsListener mListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        try {

            Log.e(TAG, "onReceive: " );
            Object[] pdus = (Object[]) data.get("pdus");
            for (Object pdu : pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String sender = smsMessage.getDisplayOriginatingAddress();
                Log.e(TAG, "onReceive: sender"+sender );

                if (sender.equalsIgnoreCase("99095"))
                {
                    String messageBody = smsMessage.getMessageBody();

                    int lastIndex=messageBody.length();
                    int startInDex=lastIndex-4;
                    String code=messageBody.substring(startInDex,lastIndex).trim();
                    Log.e(TAG, "onReceive: otp code is "+code);
                    try {
                        if (mListener != null)
                            mListener.messageReceived(code);
                        else
                            Log.e(TAG, "OtpVerificationReceiver: listener is empty");
                    } catch (Exception e) {
                        Log.e(TAG, "onReceive: " + e.getMessage());
                    }
                }

            }

        }catch (Exception e)
        {
            Log.e(TAG, "onReceive: " + e.getMessage());
        }

    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}