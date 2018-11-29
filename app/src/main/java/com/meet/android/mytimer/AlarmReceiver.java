package com.meet.android.mytimer;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static MediaPlayer mp = new MediaPlayer();
    private static Ringtone r;
    public static final String NOTIFICATION_CHANNEL_ID = "4655";

    @Override
    public void onReceive(Context context, Intent intent) {
        mp.stop();
        AlarmManager am = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );

        Intent ami = new Intent( context, AlarmReceiver.class );
        PendingIntent pmi = PendingIntent.getBroadcast( context, 0, ami, 0 );

        Intent ami1 = new Intent( ApplicationContextProvider.getContext(), NotificationDismissedReceiver.class );
        PendingIntent pmi1 = PendingIntent.getBroadcast( ApplicationContextProvider.getContext(), 0, ami1, 0 );

        if(MainActivity.getData() && (MainActivity.getFrom() <= MainActivity.getCurrentMillis() && MainActivity.getTo() >= MainActivity.getCurrentMillis()) && MainActivity.getInterval() > 0) {
            Toast.makeText( context, "Its Time!!", Toast.LENGTH_SHORT ).show();

            Uri notification2 = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Meet",NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                nMgr.createNotificationChannel(notificationChannel);

                NotificationCompat.Builder n  = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID);
                        n.setContentTitle("Reminder")
                        .setContentText("Swipe to turn off")
                        .setContentIntent(pmi1)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setDeleteIntent(pmi1)
                        .setChannelId("4655")
                        .setAutoCancel(true);
                nMgr.notify(1,n.build());
            }
            else{
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                                .setContentTitle("A Notification")
                                .setContentText("This is an example notification");
                int notificationId = 101;
                NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notifyMgr.notify(notificationId, builder.build());
            }

            mp = MediaPlayer.create(context,notification2);
            mp.setLooping(true);
            mp.start();

            r = RingtoneManager.getRingtone( ApplicationContextProvider.getContext(), notification2 );

            am.set( AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (MainActivity.getInterval()*1000), pmi );
        }
        else{
            mp.stop();
            am.cancel(pmi);
        }


    }

    public static void stopRingtone(){
        mp.stop();
    }

}
