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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthlog.R;
import com.example.healthlog.handler.NewPatientHandler;
import com.example.healthlog.ui.doctor.DoctorViewModel;

import org.w3c.dom.Text;

import org.w3c.dom.Text;

public class HospitalFragment extends Fragment {


    // COMPLETED(SHANK) implement ui

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

    // COMPLETED(SHANK) find all views, create object of viewModel and call attachModel
    void setup() {
        totalPatientsTv = root.findViewById(R.id.hospital_confirmed_textView);
        activePatientsTv = root.findViewById(R.id.hospital_active_textView);
        curedPatientsTv = root.findViewById(R.id.hospital_cured_textView);
        deceasedPatientsTv = root.findViewById(R.id.hospital_deceased_textView);

        notificationsViewModel =
                ViewModelProviders.of(this).get(HospitalViewModel.class);
        notificationsViewModel.init(getContext());
        notificationsViewModel.getNoOfActivePatient()
                .observe(getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                activePatientsTv.setText(integer.toString());
                            }
                        });

        notificationsViewModel.getTotalNoOfPatient()
                .observe(getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                totalPatientsTv.setText(integer.toString());
                            }
                        });

        notificationsViewModel.getNoOfCuredPatient()
                .observe(getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                curedPatientsTv.setText(integer.toString());
                            }
                        });

        notificationsViewModel.getNoOfDeceasedPatient()
                .observe(getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                deceasedPatientsTv.setText(integer.toString());
                            }
                        });

    }

}
