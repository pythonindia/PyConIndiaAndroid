package com.pyconindia.pycon.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.pyconindia.pycon.ScheduleActivity;
import com.pyconindia.pycon.R;

public class AlarmReceiver extends BroadcastReceiver {

    String title;
    String description;
    private static int mId = 0;
    private final static String GROUP = "MOJO_JOJO_NOTIFS"; // Coudn't think of a more decent name
    public static int notificationCount = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            title = extras.getString("title");
            description = extras.getString("description");
            mId = extras.getInt("id");
        }

        Intent resultIntent = new Intent(context, ScheduleActivity.class);
        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        String[] events = new String[1];
//        events[0] = description;
//        inboxStyle.setBigContentTitle("Multiple events starting");
//        inboxStyle.addLine(description);
//        inboxStyle.setSummaryText("Pycon Event Alert");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
        .setLargeIcon(largeIcon)
        .setSmallIcon(R.drawable.ic_stat_pycon)
        .setContentTitle(title)
        .setGroup(GROUP)
        .setContentText(description)
        .setContentIntent(resultPendingIntent);
//        .setStyle(inboxStyle);


        Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
        note.number = notificationCount++;



//        mBuilder.setStyle(inboxStyle);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(mId, note);
    }

}
