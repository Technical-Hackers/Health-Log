package com.example.healthlog.handler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.healthlog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewPatientHandler {

    //activity
    Context context;
    Activity activity;

    //dialog
    Dialog dialog;

    //editText
    EditText name;
    EditText address;
    EditText dob;
    EditText floor;
    EditText roomNo;
    EditText bedNo;

    //textView
    TextView id;

    //firebase reference
    FirebaseFirestore mRef;

    int size;


    public NewPatientHandler(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        setUp();
    }

    void setUp(){
        mRef = FirebaseFirestore.getInstance();
        fetchPatientMetaData();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.new_patient_layout);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        id = dialog.findViewById(R.id.new_patient_layout_id_tV);

        name = dialog.findViewById(R.id.new_patient_layout_name_eT);
        address = dialog.findViewById(R.id.new_patient_layout_address_eT);
        dob = dialog.findViewById(R.id.new_patient_layout_dob_eT);
        floor = dialog.findViewById(R.id.new_patient_layout_floor_eT);
        roomNo = dialog.findViewById(R.id.new_patient_layout_roomNo_eT);
        bedNo = dialog.findViewById(R.id.new_patient_layout_bedNo_eT);

        (dob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    pickDate();
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                id.setText("Patient id: "+("P"+size+"_"+editable.toString()));
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void pickDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context);

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dob.setText(day+"/"+month+"/"+year);
            }
        });

        datePickerDialog.show();
    }

    // TODO(Danish) implement method
    void createNewPatient(){
        /*
        * 1. Create new patient object
        * 2.
        */
    }

    void fetchPatientMetaData(){
        mRef.collection("Hospital").document("H1_Sir_Sunderlal_Hospital")
                .collection("Patient").document("meta-data").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                   size = task.getResult().getDouble("size").intValue();
                   id.setText("Patient id: "+("P"+size+"_"));
                }
            }
        });
    }

    public void init(){
        dialog.show();
    }

}
