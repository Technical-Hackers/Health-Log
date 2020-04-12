package com.example.healthlog.ui.doctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DoctorViewModel extends ViewModel {

    // TODO(SHANK) fetch from server

    private MutableLiveData<String> mText;

    public DoctorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Doctor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // TODO(SHANK) implement method
    public void fetchDoctor(){

    }
}