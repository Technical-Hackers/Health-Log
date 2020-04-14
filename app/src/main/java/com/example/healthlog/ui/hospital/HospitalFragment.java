package com.example.healthlog.ui.hospital;

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
import com.example.healthlog.handler.NewPatientHandler;

import org.w3c.dom.Text;

public class HospitalFragment extends Fragment {

    // TODO(SHANK) implement ui

    private HospitalViewModel notificationsViewModel;

    View root;

    TextView totalPatientsTv;
    TextView activePatientsTv;
    TextView curedPatientsTv;
    TextView deceasedPatientsTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(HospitalViewModel.class);
        root = inflater.inflate(R.layout.fragment_hospital, container, false);

        setup();

        return root;
    }

    // TODO(SHANK) find all views, create object of viewModel and call attachModel
    void setup(){

    }

    // TODO(SHANK) attach observer to 4 textViews
    void attachModel(){

    }

}
