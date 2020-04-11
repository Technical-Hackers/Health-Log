package com.example.healthlog.ui.hospital;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HospitalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HospitalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hospital fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}