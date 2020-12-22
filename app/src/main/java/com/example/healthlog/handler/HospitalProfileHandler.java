package com.example.healthlog.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.healthlog.R;
import com.example.healthlog.model.Hospital;
import com.example.healthlog.model.Patient;

import java.util.Objects;

public class HospitalProfileHandler {

    // activity var
    Context context;
    Activity activity;

    // Hospital var
    Hospital hospital;

    // views
    TextView id;
    TextView name;
    TextView address, bedCount, doctorCount;

    public HospitalProfileHandler(Context context, Activity activity) {
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
        dialog.setContentView(R.layout.profile_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow()
                .setLayout(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        id = dialog.findViewById(R.id.hospital_id_text);
        name = dialog.findViewById(R.id.hospital_name_text);
        address = dialog.findViewById(R.id.hospital_address);
        bedCount = dialog.findViewById(R.id.hospital_bed_count);
        doctorCount = dialog.findViewById(R.id.hospital_doctor_count);
    }

    void initViews() {
        id.setText(context.getString(R.string.hospital_id_text,hospital.getId()));
        name.setText(context.getString(R.string.hospital_name,hospital.getName()));
        address.setText(context.getString(R.string.hospital_address,hospital.getAddress()));
        bedCount.setText(context.getString(R.string.bed_count,hospital.getBedCount()));
        doctorCount.setText(context.getString(R.string.doctor_count,hospital.getDoctorCount()));

    }

    public void init() {
        dialog.show();
    }

    void destroy() {
        dialog.dismiss();
    }

    public void update(Hospital newHospital) {
        setHospital(newHospital);
        initViews();
    }

    public Hospital getHospital(){ return  this.hospital; }

    public void setHospital(Hospital newHospital) {
        this.hospital = newHospital;
    }

}
