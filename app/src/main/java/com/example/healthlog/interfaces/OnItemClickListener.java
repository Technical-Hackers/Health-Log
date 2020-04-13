package com.example.healthlog.interfaces;

import com.example.healthlog.model.Patient;

public interface OnItemClickListener<T> {

    void onItemClicked(T object);
}
