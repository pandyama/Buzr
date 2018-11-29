package com.meet.android.mytimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("IN DISMISS");
        AlarmReceiver.stopRingtone();
    }
}
