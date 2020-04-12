package com.example.healthlog.ui.dashboard;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthlog.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    // COMPLETED(Shashank) fetch data from server and return the arrayList
    public LiveData<ArrayList<Patient>> getPatientsList() {
        return liveData;
    }

    public MutableLiveData<ArrayList<Patient>> getPatients() {
        fetchPatients();
        patient.setValue(patientArrayList);

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

    public void fetchPatients(){
        mRef.collection("Hospital")
                .document("H1_Sir_Sunderlal_Hospital")
                .collection("Patient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document: task.getResult()){
                        patientArrayList.add(document.toObject(Patient.class));
                    }
                }
            }
        });
    }
}