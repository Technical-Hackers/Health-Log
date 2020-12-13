package com.example.healthlog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    EditText hospitalId;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        hospitalId = (EditText) findViewById(R.id.loginActivity_login_editText);
        login = findViewById(R.id.loginActivity_login_btn);
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // COMPLETED(Danish) check if user is already login
        if (HealthLog.ID != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language_change,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.language:
                showChangeLanguageDialogue();break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void showChangeLanguageDialogue() {
        final String[] listitems= {"French","German","Spanish","English"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(LoginActivity.this);
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

    public void signIn() {
        final String hId = hospitalId.getText().toString().trim();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Hospital").document(hId);
        docRef
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        HealthLog.addHospitalIdToSharedPreference(hId);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(
                                                        getApplicationContext(), "Wrong Login credentials", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } else {
                                    Log.d("Loginok", "get failed with ", task.getException());
                                }
                            }
                        });
    }
}
