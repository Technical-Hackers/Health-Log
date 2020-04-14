package com.example.healthlog.ui.hospital;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthlog.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class HospitalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<Integer>  totalNoOfPatient;
    private MutableLiveData<Integer>  totalNoOfActivePatient;
    private MutableLiveData<Integer>  totalNoOfCuredPatient;
    private MutableLiveData<Integer>  totalNoOfDeceasedPatient;

    private Context mContext;

    public HospitalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hospital fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

        public void init(Context context) {
            this.mContext = context;
        }

    // COMPLETED(SHANK) implement_1
    public MutableLiveData<Integer> getTotalNoOfPatient(){
        return totalNoOfPatient;
    }

    // COMPLETED(SHANK) implement_2
    public MutableLiveData<Integer> getNoOfActivePatient(){
        return totalNoOfActivePatient;
    }

    // COMPLETED(SHANK) implement_3
    public MutableLiveData<Integer> getNoOfCuredPatient(){
        return totalNoOfCuredPatient;
    }

    // COMPLETED (SHANK) implement_4
    public MutableLiveData<Integer> getNoOfDeceasedPatient(){
        return totalNoOfDeceasedPatient;
    }


    // TODO(DJ) implement_1
    private void fetchTotalNoOfPatient(){

    }

    // TODO(DJ) implement_2
    private void fetchNoOfActivePatient(){

    }

    // TODO(DJ) implement_3
    private void fetchNoOfCuredPatient(){

    }

    // TODO(DJ) implement_4
    private void fetchNoOfDeceasedPatient(){

    }
}