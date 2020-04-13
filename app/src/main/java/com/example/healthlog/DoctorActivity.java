package com.example.healthlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.healthlog.adapter.DashboardAdapter;
import com.example.healthlog.handler.PatientLogHandler;
import com.example.healthlog.handler.PatientViewHandler;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class DoctorActivity extends AppCompatActivity {

    // COMPLETED(SHANK) implement the ui

    // COMPLETED(SHANK) display list of patient allotted

    // COMPLETED(SHANK) create layout for adding log in patient data

    // COMPLETED(SHANK) add feature for doctor to enter log for patient

    private RecyclerView.Adapter patientAdapter;
    private RecyclerView patientRecyclerView;
    DashboardAdapter dashboardAdapter;
    FirebaseFirestore mRef;
    private ArrayList<Patient> patientArrayList = new ArrayList<>();
    MutableLiveData<ArrayList<Patient>> patient = new MutableLiveData<>();
    String doctorName;
    String logMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        setUpRecyclerView();
    }

    void setUpRecyclerView() {
        final PatientLogHandler patientLogHandler = new PatientLogHandler(getApplicationContext(), this);
        patientAdapter = new DashboardAdapter(patientArrayList, new OnItemClickListener<Patient>() {
            @Override
            public void onItemClicked(Patient patient) {
                patientLogHandler.init();
                Button save = findViewById(R.id.doctor_activity_addlog_btn);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logMessage = patientLogHandler.getLog();
                        patientLogHandler.destroyDialog();
                    }
                });
            }
        });
        dashboardAdapter.getLog(logMessage);
        patientRecyclerView = (RecyclerView) findViewById(R.id.doctor_patient_list_recyclerView);
        patientRecyclerView.setHasFixedSize(false);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        patientRecyclerView.setAdapter(patientAdapter);
        fetchPatient();
    }

    public void fetchPatient() {
        mRef.collection("Hospital")
                .document(HealthLog.ID)
                .collection("Doctor")
                .document(doctorName)
                .collection("Routine")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document: task.getResult()){
                                Patient p = document.toObject(Patient.class);
                                patientArrayList.add(p);
                            }
                            patient.setValue(patientArrayList);
                            patientAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

}
