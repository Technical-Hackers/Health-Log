package com.example.healthlog.ui.doctor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthlog.R;
import com.example.healthlog.adapter.DoctorAdapter;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.Doctor;

import java.util.ArrayList;

public class DoctorFragment extends Fragment {

    // TODO(DJ) setOnItemClickListener to doctor_list_item

    // COMPLETED(Danish) create Application class and handle the shared preferences

    // COMPLETED(DJ) apply search filter

    // COMPLETED(DJ) implement layout file

    // COMPLETED(SHANK) implement recycler view and adapter

    private DoctorAdapter doctorAdapter;
    private RecyclerView doctorRecyclerView;
    private DoctorViewModel doctorViewModel;

    private EditText searchEditText;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_doctor, container, false);

        setUpRecyclerView();

        searchEditText = root.findViewById(R.id.doctor_searchBox_editText);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                doctorAdapter.filter(editable.toString().trim());
            }
        });

        return root;
    }

    // COMPLETED(SHANK) implement following method
    void setUpRecyclerView(){
        doctorAdapter = new DoctorAdapter(new ArrayList<Doctor>());
        doctorRecyclerView = root.findViewById(R.id.doctor_showList_recycler);
        doctorRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        doctorRecyclerView.setHasFixedSize(false);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        doctorRecyclerView.setAdapter(doctorAdapter);

        doctorViewModel =
                ViewModelProviders.of(this).get(DoctorViewModel.class);
        doctorViewModel.init(getActivity());
        doctorViewModel
                .getDoctorsList()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onChanged(ArrayList<Doctor> doctors) {
                                for (Doctor d : doctors) {
                                    doctorAdapter.add(d);
                                }
                            }
                        }
                );
    }

}
