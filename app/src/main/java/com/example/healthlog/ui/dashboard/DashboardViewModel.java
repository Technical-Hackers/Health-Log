package com.example.healthlog.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthlog.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    FirebaseFirestore mRef;

    ArrayList<Patient> patientList;

    private MutableLiveData<String> mText;


    public DashboardViewModel() {
        mRef = FirebaseFirestore.getInstance();
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void fetchPatients(){
        mRef.collection("Hospital").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document: task.getResult()){
                        patientList.add(document.toObject(Patient.class));
                    }
                }
            }
        });
    }
}