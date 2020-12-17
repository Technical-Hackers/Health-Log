package com.example.healthlog;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class HealthLog extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    public static String ID = null;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        getHospitalId();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//          For high priority Notifications, eg new patient admitted etc.
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

//          For relatively less important notifications
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }


    public static void addHospitalIdToSharedPreference(String s) {
        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ID", s);
        editor.commit();
        getHospitalId();
    }

    private static void getHospitalId() {
        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        ID = preferences.getString("ID", null);
    }

    public static String getDate(Timestamp timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM");
        String date = simpleDateFormat.format(timestamp.toDate());
        return date;
    }

    public static String getTime(Timestamp timestamp){
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("H:m");
        String time = simpleTimeFormat.format(timestamp.toDate());
        return time;
    }

    public static String getDoctorLocation(List<String> location){
        return "Cabin: "+location.get(1)+" Floor: "+location.get(0);
    }
}
