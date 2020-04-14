package com.example.healthlog.ui.hospital;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthlog.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class HospitalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private int totalNoOfPatient;
    private int totalNoOfActivePatient;
    private int totalNoOfCuredPatient;
    private int totalNoOfDeceasedPatient;

    public HospitalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hospital fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // TODO(SHANK) implement_1
    public MutableLiveData<Integer> getTotalNoOfPatient(){
        return null;
    }

    // TODO(SHANK) implement_2
    public MutableLiveData<Integer> getNoOfActivePatient(){
        return null;
    }

    // TODO(SHANK) implement_3
    public MutableLiveData<Integer> getNoOfCuredPatient(){
        return null;
    }

    // TODO(SHANK) implement_4
    public MutableLiveData<Integer> getNoOfDeceasedPatient(){
        return null;
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