package com.example.healthlog.ui.doctor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.healthlog.HealthLog;
import com.example.healthlog.model.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class DoctorViewModel extends ViewModel {

    // COMPLETED(SHANK) fetch from server
    FirebaseFirestore mRef;
    private ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();
    MutableLiveData<ArrayList<Doctor>> doctor = new MutableLiveData<>();

    private MutableLiveData<ArrayList<Doctor>> liveData;
    private Context mContext;

    private MutableLiveData<String> mText;

    public void init(Context context) {
        mContext = context;
        if (liveData != null) {
            return;
        }

        liveData = getDoctors();
    }

    public LiveData<ArrayList<Doctor>> getDoctorsList() {
        return liveData;
    }

    public MutableLiveData<ArrayList<Doctor>> getDoctors() {
        fetchDoctor();
        return doctor;
    }

    public DoctorViewModel() {
        mRef = FirebaseFirestore.getInstance();
        mText = new MutableLiveData<>();
        mText.setValue("This is doctor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // COMPLETED(SHANK) implement method
    public void fetchDoctor() {
        mRef.collection("Hospital")
                .document(HealthLog.ID)
                .collection("Doctor")
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Doctor d = document.toObject(Doctor.class);
                                        doctorArrayList.add(d);
                                    }
                                    doctor.setValue(doctorArrayList);
                                }
                            }
                        });
    }
}
