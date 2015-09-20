package com.pyconindia.pycon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.pyconindia.pycon.receivers.AlarmReceiver;

public class Alarm {

    private Context context;
    private String title;
    private String dateTime;
    private String description;
    private int id;
    private static final long FIVE_MINUTES = 300000;

    public Alarm(Context context, int id, String dateTime, String title, String description) {

        this.context = context;
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
        this.id = id;
    }

    @SuppressLint("NewApi")
    public void create() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("id", id);
        intent.setData(Uri.parse("custom://" + id));
        intent.setAction(String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent,0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());

        try {
//            Date date = sdf.parse("2015-09-19 07:10 pm");
            Date date = sdf.parse(dateTime);
//            Log.d("abhishek", ""+date.getTime());
            if(date.getTime() > System.currentTimeMillis()) {
//                Log.d("abhishek", ""+(date.getTime() - FIVE_MINUTES));
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,date.getTime() - FIVE_MINUTES, pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTime() - FIVE_MINUTES, pendingIntent);
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("id", id);
        intent.setData(Uri.parse("custom://" + id));
        intent.setAction(String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent,0);
        alarmManager.cancel(pendingIntent);
    }


}
