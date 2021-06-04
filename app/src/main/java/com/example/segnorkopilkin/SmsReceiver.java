package com.example.segnorkopilkin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(SmsWorker.class).build();
            WorkManager.getInstance(context).enqueue(request);
        }
    }
}