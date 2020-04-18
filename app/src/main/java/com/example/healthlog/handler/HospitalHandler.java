package com.example.healthlog.handler;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import com.example.healthlog.HealthLog;
import com.example.healthlog.R;
import com.example.healthlog.model.Hospital;
import com.example.healthlog.model.SuspectedPatient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class HospitalHandler {
    // Completed(SHANK) create dialog using ui

    Context context;
    List<String> hospitalNameList = new ArrayList<>();
    List<Hospital> hospitals = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Spinner spinner;
    Button submit;

    Dialog dialog;

    FirebaseFirestore mRef;

    SuspectedPatient suspectedPatient;

    Hospital selectedHospital;

    public HospitalHandler(Context context, SuspectedPatient patient) {
        this.context = context;
        suspectedPatient = patient;
        mRef = FirebaseFirestore.getInstance();
        setUp();
    }

    void setUp() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_hospital);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog
                .getWindow()
                .setLayout(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        spinner = dialog.findViewById(R.id.dialog_hospitalList_spinner);
        submit = dialog.findViewById(R.id.dialog_submit_button);

        adapter =
                new ArrayAdapter<String>(
                        context, R.layout.spinner_item, hospitalNameList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedHospital = hospitals.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initializeServer();
                    }
                });

        fetchHospitalList();
    }

    void initializeServer() {
        mRef.collection("Hospital")
                .document(selectedHospital.getId())
                .collection("suspect")
                .add(suspectedPatient)
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    mRef.collection("Hospital")
                                            .document(HealthLog.ID)
                                            .collection("suspect")
                                            .whereEqualTo("address", suspectedPatient.getAddress())
                                            .whereEqualTo("age", suspectedPatient.getAge())
                                            .whereEqualTo("contact", suspectedPatient.getContact())
                                            .whereEqualTo("email", suspectedPatient.getEmail())
                                            .whereEqualTo("name", suspectedPatient.getName())
                                            .whereEqualTo("type", suspectedPatient.getType())
                                            .get()
                                            .addOnCompleteListener(
                                                    new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                                                                if (snapshots.size() == 1) {
                                                                    snapshots
                                                                            .get(0)
                                                                            .getReference()
                                                                            .delete()
                                                                            .addOnCompleteListener(
                                                                                    new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                dismiss();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                }
                                                            }
                                                        }
                                                    });
                                }
                            }
                        });
    }

    void fetchHospitalList() {
        mRef.collection("Hospital")
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot snapshot : task.getResult()) {
                                        Hospital hospital = snapshot.toObject(Hospital.class);
                                        if (!hospital.getId().equals(HealthLog.ID)) {
                                            hospitals.add(hospital);
                                            hospitalNameList.add(snapshot.getString("name"));
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
    }

    public void init() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
