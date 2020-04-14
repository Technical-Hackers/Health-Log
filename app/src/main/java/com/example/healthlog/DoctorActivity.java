package com.example.healthlog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthlog.adapter.DashboardAdapter;
import com.example.healthlog.handler.PatientLogHandler;
import com.example.healthlog.handler.PatientViewHandler;
import com.example.healthlog.interfaces.DialogClickListener;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.Doctor;
import com.example.healthlog.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DoctorActivity extends AppCompatActivity {

    // COMPLETED(SHANK) implement the ui

    // COMPLETED(SHANK) display list of patient allotted

    // COMPLETED(SHANK) create layout for adding log in patient data

    // COMPLETED(SHANK) add feature for doctor to enter log for patient

    DashboardAdapter patientAdapter;
    private RecyclerView patientRecyclerView;
    FirebaseFirestore mRef;

    PatientLogHandler patientLogHandler;

    Doctor doctor;

    Pair<String, String> changeStatus = new Pair<>("", "");// status change Pair<from, to>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        mRef = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        doctor = (Doctor)intent.getSerializableExtra("doctor") ;

        setUpRecyclerView();
    }

    void setUpRecyclerView() {
        patientLogHandler = new PatientLogHandler(DoctorActivity.this, this);

        patientAdapter = new DashboardAdapter(new ArrayList<Patient>(), new OnItemClickListener<Patient>() {
            @Override
            public void onItemClicked(final Patient patient, final View v) {
                patientLogHandler.init();
                patientLogHandler.setDialogClickListener(new DialogClickListener() {
                    @Override
                    public void onSaveClicked(String log, String status) {
                        updatePatient(patient, log, status);
                    }
                });
            }
        });
        patientAdapter.setCurrentFilter("All");

        patientRecyclerView = (RecyclerView) findViewById(R.id.doctor_patient_list_recyclerView);
        patientRecyclerView.setHasFixedSize(false);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        patientRecyclerView.setAdapter(patientAdapter);
        fetchPatient();
    }

    // fetch patient details
    public void fetchPatient() {
        mRef.collection("Hospital")
                .document(HealthLog.ID)
                .collection("Doctor")
                .document(doctor.getId())
                .collection("Routine")
                .document("Routine").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            List<DocumentReference> patientsRef = (List<DocumentReference>) document.get("patientList");

                            for(DocumentReference ref: patientsRef){
                                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            Patient p = task.getResult().toObject(Patient.class);

                                            patientAdapter.add(p);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }

    // update status and log of patient
    void updatePatient(Patient p, final String log, final String status){
        Map<String, Object> update;

        if(status==null){
            update = new HashMap<String, Object>(){{
                put("recentLog", log);
            }};
        }else{
            update = new HashMap<String, Object>(){{
                put("recentLog", log);
                put("status", status);
            }};
        }

        setChangeStatus(new Pair<String, String>(p.getStatus(), status));

        mRef.collection("Hospital").document(HealthLog.ID)
                .collection("Patient").document(p.getId())
                .update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // COMPLETED(DJ) call @updatePatientMetaData() function & destroy dialog after that task is completed
                    updatePatientMetaData();

                }
            }
        });
    }

    // COMPLETED(DJ) update meta-data
    void updatePatientMetaData(){
        if(changeStatus.first.equals(changeStatus.second)){
            return;
        }
        final DocumentReference metaDataRef = mRef.collection("Hospital").document(HealthLog.ID)
                .collection("Patient").document("meta-data");

        mRef.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                metaDataRef.update(changeStatus.first.toLowerCase(), FieldValue.increment(-1));
                metaDataRef.update(changeStatus.second.toLowerCase(), FieldValue.increment(1));
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                patientLogHandler.destroyDialog();
            }
        });
    }

    public void setChangeStatus(Pair<String, String> changeStatus) {
        this.changeStatus = changeStatus;
    }
}
