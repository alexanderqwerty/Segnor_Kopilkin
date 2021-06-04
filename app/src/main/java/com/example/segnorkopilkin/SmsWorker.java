package com.example.segnorkopilkin;

import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Date;

public class SmsWorker extends Worker {
    public SmsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Cursor cursor = getApplicationContext().getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String msgData = "";
                Date date = null;
                if (cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS)).equals("900"))
                    for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                        if(idx == cursor.getColumnIndex(Telephony.Sms.DATE))
                            date = new Date(cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE)));
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    }
                if (date != null) {
                    System.out.println(date.getTime());
                }
                System.out.println(msgData);
            } while (cursor.moveToNext());
        } else {
            System.out.println("Nothing");
        }

        return Result.success();
    }
}
