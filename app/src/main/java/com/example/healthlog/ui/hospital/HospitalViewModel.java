package com.example.healthlog.ui.hospital;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.healthlog.HealthLog;
import com.example.healthlog.handler.HospitalHandler;
import com.example.healthlog.handler.NewPatientHandler;
import com.example.healthlog.model.SuspectedPatient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class HospitalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private Context mContext;

    private DocumentReference patientMetaDataRef;

    private ArrayList<SuspectedPatient> suspectedPatientArrayList = new ArrayList<SuspectedPatient>();
    MutableLiveData<ArrayList<SuspectedPatient>> suspect = new MutableLiveData<>();

    private MutableLiveData<ArrayList<SuspectedPatient>> liveData;
    private MutableLiveData<Integer> totalNoOfPatient = new MutableLiveData<>();
    private MutableLiveData<Integer> totalNoOfActivePatient = new MutableLiveData<>();
    private MutableLiveData<Integer> totalNoOfCuredPatient = new MutableLiveData<>();
    private MutableLiveData<Integer> totalNoOfDeceasedPatient = new MutableLiveData<>();

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

    public void initSuspectList(Context context) {
        mContext = context;
        if (liveData != null) {
            return;
        }
        liveData = getSuspectPatients();
    }

    public LiveData<ArrayList<SuspectedPatient>> getSuspectPatientsList() {
        return liveData;
    }

    public MutableLiveData<ArrayList<SuspectedPatient>> getSuspectPatients() {
        fetchSuspectPatients();
        return suspect;
    }

    // COMPLETED(SHANK) implement_1
    public LiveData<Integer> getTotalNoOfPatient() {
        return totalNoOfPatient;
    }

    // COMPLETED(SHANK) implement_2
    public LiveData<Integer> getNoOfActivePatient() {
        return totalNoOfActivePatient;
    }

    // COMPLETED(SHANK) implement_3
    public LiveData<Integer> getNoOfCuredPatient() {
        return totalNoOfCuredPatient;
    }

    // COMPLETED (SHANK) implement_4
    public LiveData<Integer> getNoOfDeceasedPatient() {
        return totalNoOfDeceasedPatient;
    }

    // COMPLETED(DJ) implement_1
    private void fetchPatientMetaData() {
        patientMetaDataRef =
                FirebaseFirestore.getInstance()
                        .collection("Hospital")
                        .document(HealthLog.ID)
                        .collection("Patient")
                        .document("meta-data");

        patientMetaDataRef.addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(
                            @Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException e) {
                        totalNoOfPatient.setValue(doc.getLong("size").intValue());
                        totalNoOfActivePatient.setValue(doc.getLong("active").intValue());
                        totalNoOfCuredPatient.setValue(doc.getLong("cured").intValue());
                        totalNoOfDeceasedPatient.setValue(doc.getLong("deceased").intValue());
                    }
                });
    }

    private void fetchSuspectPatients() {
        CollectionReference patientRef =
                FirebaseFirestore.getInstance()
                        .collection("Hospital")
                        .document(HealthLog.ID)
                        .collection("suspect");

        patientRef
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot snapshot : task.getResult()) {
                                        SuspectedPatient suspectedPatient = snapshot.toObject(SuspectedPatient.class);
                                        suspectedPatientArrayList.add(suspectedPatient);
                                    }
                                    suspect.setValue(suspectedPatientArrayList);
                                }
                            }
                        });
    }

    // COMPLETED(DJ) implement_1
    public void addPatientToHospital(SuspectedPatient patient) {
        NewPatientHandler patientHandler = new NewPatientHandler(mContext);
        patientHandler.setName(patient.getName());
        patientHandler.setAddress(patient.getAddress());
        patientHandler.init();
    }

    // COMPLETED(DJ) implement_2
    public void sendRequestToHospital(SuspectedPatient patient) {
        HospitalHandler hospitalHandler = new HospitalHandler(mContext, patient);
        hospitalHandler.init();
    }
}
