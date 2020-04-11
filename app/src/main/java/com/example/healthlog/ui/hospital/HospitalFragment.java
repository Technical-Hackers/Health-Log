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

public class HospitalFragment extends Fragment {

    private HospitalViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(HospitalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hospital, container, false);
        final TextView textView = root.findViewById(R.id.text_hospital);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        NewPatientHandler  handler = new NewPatientHandler(getContext(), getActivity());
        handler.init();
        return root;
    }
}
