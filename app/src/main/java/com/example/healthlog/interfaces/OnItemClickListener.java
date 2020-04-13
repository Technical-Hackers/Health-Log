package com.example.healthlog.interfaces;

import android.view.View;

import com.example.healthlog.model.Patient;

public interface OnItemClickListener<T> {

    void onItemClicked(T object, View v);
}
