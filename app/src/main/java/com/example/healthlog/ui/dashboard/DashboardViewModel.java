package com.example.healthlog.ui.dashboard;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthlog.HealthLog;
import com.example.healthlog.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    FirebaseFirestore mRef;
    private ArrayList<Patient> patientArrayList = new ArrayList<Patient>();
    MutableLiveData<ArrayList<Patient>> patient = new MutableLiveData<>();

    private MutableLiveData<ArrayList<Patient>> liveData;
    private Context mContext;

    ArrayList<Patient> patientList;

    private MutableLiveData<String> mText;

    public void init(Context context) {
        mContext = context;
        if (liveData != null) {
            return;
        }

        liveData = getPatients();
    }

    public LiveData<ArrayList<Patient>> getPatientsList() {
        return liveData;
    }

    public MutableLiveData<ArrayList<Patient>> getPatients() {
        fetchPatients();
        return patient;
    }
    public DashboardViewModel() {
        mRef = FirebaseFirestore.getInstance();
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // COMPLETED(DJ) update data realtime
    public void fetchPatients(){
        final CollectionReference patientReference = mRef.collection("Hospital").document(HealthLog.ID).collection("Patient");

        patientReference.document("meta-data").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                patientReference
                        .whereEqualTo("type", 0)
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
                                }
                            }
                        });
            }
        });


    }
}