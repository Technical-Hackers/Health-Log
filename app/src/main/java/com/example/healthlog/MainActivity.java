package com.example.healthlog;

import android.content.DialogInterface;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    // COMPLETED(SHANK) add logout button in appBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                dialog();
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
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
