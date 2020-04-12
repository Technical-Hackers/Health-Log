package com.example.healthlog.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthlog.R;

public class DoctorFragment extends Fragment {

    // COMPLETED(DJ) implement layout file

    // TODO(SHANK) implement recycler view and adapter


    private DoctorViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(DoctorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doctor, container, false);

        return root;
    }

    // TODO(SHANK) implement following method
    void setUpRecyclerView(){
    }

}
