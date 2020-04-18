package com.example.healthlog.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.healthlog.R;
import com.example.healthlog.model.Patient;

public class PatientViewHandler {

    // activity var
    Context context;
    Activity activity;

    // patient var
    Patient patient;

    // views
    TextView id;
    TextView name;
    TextView address;
    TextView dob;
    TextView age;
    TextView dateAdded;
    TextView location;
    TextView log;

    public PatientViewHandler(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        if (dialog == null) {
            setUp();
        }
    }

    Dialog dialog;

    void setUp() {
        // initialising dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.patient_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog
                .getWindow()
                .setLayout(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        id = dialog.findViewById(R.id.patient_layout_id_tV);
        name = dialog.findViewById(R.id.patient_layout_name_tV);
        address = dialog.findViewById(R.id.patient_layout_address_tV);
        dob = dialog.findViewById(R.id.patient_layout_dob_tV);
        age = dialog.findViewById(R.id.patient_layout_age_tV);
        dateAdded = dialog.findViewById(R.id.patient_layout_dateAdded_tV);
        location = dialog.findViewById(R.id.patient_layout_location_tV);
        log = dialog.findViewById(R.id.patient_layout_log_tV);
    }

    void initViews() {
        id.setText("Patient: " + patient.getId());
        name.setText("Name: " + patient.getName());
        address.setText("Address: " + patient.getAddress());
        dob.setText("DOB: " + patient.getDob());
        age.setText("Age: " + patient.getAge());
        dateAdded.setText("Added On: " + patient.getDateAdded().getSeconds());
        location.setText(
                "Location: "
                        + patient.getLocation().get(0)
                        + patient.getLocation().get(1)
                        + patient.getLocation().get(2));
        log.setText("Log: " + patient.getRecentLog());
    }

    public void init() {
        dialog.show();
    }

    void destroy() {
        dialog.dismiss();
    }

    public void update(Patient newPatient) {
        setPatient(newPatient);
        initViews();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
