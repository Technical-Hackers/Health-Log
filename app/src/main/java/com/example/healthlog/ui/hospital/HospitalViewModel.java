package com.example.healthlog.ui.hospital;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthlog.HealthLog;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HospitalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private Context mContext;

    private DocumentReference patientMetaDataRef;

    private MutableLiveData<Integer> totalNoOfPatient=new MutableLiveData<>();
    private MutableLiveData<Integer> totalNoOfActivePatient=new MutableLiveData<>();
    private MutableLiveData<Integer> totalNoOfCuredPatient=new MutableLiveData<>();
    private MutableLiveData<Integer> totalNoOfDeceasedPatient=new MutableLiveData<>();


    public HospitalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hospital fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void init(Context context) {
        this.mContext = context;
        fetchPatientMetaData();
    }

    // COMPLETED(SHANK) implement_1
    public LiveData<Integer> getTotalNoOfPatient(){
        return totalNoOfPatient;
    }

    // COMPLETED(SHANK) implement_2
    public LiveData<Integer> getNoOfActivePatient(){
        return totalNoOfActivePatient;
    }

    // COMPLETED(SHANK) implement_3
    public LiveData<Integer> getNoOfCuredPatient(){

        return totalNoOfCuredPatient;
    }

    // COMPLETED (SHANK) implement_4
    public LiveData<Integer> getNoOfDeceasedPatient(){
        return totalNoOfDeceasedPatient;
    }


    // COMPLETED(DJ) implement_1
    private void fetchPatientMetaData(){
        patientMetaDataRef = FirebaseFirestore.getInstance().collection("Hospital").document(HealthLog.ID)
                .collection("Patient").document("meta-data");

        patientMetaDataRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException e) {
                totalNoOfPatient.setValue(doc.getLong("size").intValue());
                totalNoOfActivePatient.setValue(doc.getLong("active").intValue());
                totalNoOfCuredPatient.setValue(doc.getLong("cured").intValue());
                totalNoOfDeceasedPatient.setValue(doc.getLong("deceased").intValue());
            }
        });
    }

}