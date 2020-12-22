package com.example.healthlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SettingsTheme);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.setting);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
        SharedPreferences settingsPref;
        String language;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Log.d("LANG","onCreatePreferences");

            settingsPref = getActivity().getSharedPreferences("Settings",MODE_PRIVATE);
            language = settingsPref.getString("My_Lang","en");

            ListPreference langPref = findPreference("list_pref_lang");
            if (langPref != null) {
                if(!language.equals(""))
                    langPref.setValue(language);
                else
                    langPref.setValue("en");
                langPref.setOnPreferenceChangeListener(this);
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            if(preference instanceof ListPreference){
                language = newValue.toString();
                //Toast.makeText(getContext(),language,Toast.LENGTH_SHORT).show();
                setLocale(language);
            }
            return true;
        }

        private void setLocale(String language) {

            //save data to shared preferences
            SharedPreferences.Editor editor = settingsPref.edit();
            editor.putString("My_Lang", language);
            editor.apply();

            Locale locale= new Locale(language);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration config = res.getConfiguration();
            config.locale = locale;
            res.updateConfiguration(config,dm);

            Intent refresh = new Intent(getContext(),MainActivity.class);
            startActivity(refresh);
            refresh = new Intent(getContext(),SettingsActivity.class);
            startActivity(refresh);
            getActivity().finishAffinity();
        }
    }
}