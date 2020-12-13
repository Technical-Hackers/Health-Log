package com.example.healthlog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // COMPLETED(SHANK) add logout button in appBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(
                                R.id.navigation_dashboard, R.id.navigation_doctor, R.id.navigation_hospitals)
                        .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        // Set the message show for the Alert time
        builder.setMessage(R.string.ask_logout);
        builder.setTitle(R.string.alert);
        builder.setCancelable(false);
        builder.setPositiveButton(
                android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logOut();
                    }
                });
        builder.setNegativeButton(
                android.R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        inflater.inflate(R.menu.language_change,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                dialog();break;
            case R.id.language:
                showChangeLanguageDialogue();break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void showChangeLanguageDialogue() {
        final String[] listitems= {"French","German","Spanish","English"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
        mbuilder.setTitle(R.string.choose_language);
        mbuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0)
                {   //French
                    setLocale("fr");
                    recreate();
                }
                else if(i==1)
                {   //German
                    setLocale("de");
                    recreate();
                }
                else if(i==2)
                {   //Spanish
                    setLocale("es");
                    recreate();
                }
                else if(i==3)
                {    //English
                    setLocale("en");
                    recreate();
                }
                //dismiss alert dialog when language is selected
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mbuilder.create();
        //show alert dialog
        mDialog.show();
    }
    private void setLocale(String language) {
        Locale locale= new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration= new Configuration();
        configuration.locale= locale;
        Object metrices;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor =getSharedPreferences("Settings",MODE_PRIVATE).edit();

        editor.putString("My_Lang", language);
        editor.apply();
    }
    //load language saved in Shared Preferences
    public void loadLocale()
    {
        SharedPreferences prefs= getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language= prefs.getString("My_Lang","");
        setLocale(language);
    }
    // COMPLETED(SHANK) call this method when logout button is clicked
    // COMPLETED(DJ) implement the method
    void logOut() {
        HealthLog.addHospitalIdToSharedPreference(null);
        /*SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ID", "");
        editor.commit();
        HealthLog.ID = null;*/
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
