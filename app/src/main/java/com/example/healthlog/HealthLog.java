package com.example.healthlog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class HealthLog extends Application {

    public static String ID = null;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
       getHospitalId();
    }

    public static void addHospitalIdToSharedPreference(String s){
        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ID", s);
        editor.commit();
        getHospitalId();
    }

    private static void getHospitalId(){
        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        ID = preferences.getString("ID", null);
    }

}
