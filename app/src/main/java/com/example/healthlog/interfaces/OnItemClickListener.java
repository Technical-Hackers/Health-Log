package com.example.healthlog.interfaces;

import android.view.View;


public interface OnItemClickListener<T> {

    void onItemClicked(T object, View v);
}
