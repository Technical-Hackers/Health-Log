package com.example.healthlog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class HealthLog extends Application {

    public static String ID = null;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        getHospitalId();
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
