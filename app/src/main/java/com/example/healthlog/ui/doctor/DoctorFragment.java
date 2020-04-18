package com.example.healthlog.ui.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthlog.DoctorActivity;
import com.example.healthlog.R;
import com.example.healthlog.adapter.DoctorAdapter;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.Doctor;
import java.util.ArrayList;

public class DoctorFragment extends Fragment {

    // COMPLETED(DJ) setOnItemClickListener to doctor_list_item

    // COMPLETED(Danish) create Application class and handle the shared preferences

    // COMPLETED(DJ) apply search filter

    // COMPLETED(DJ) implement layout file

    // COMPLETED(SHANK) implement recycler view and adapter

    private DoctorAdapter doctorAdapter;
    private RecyclerView doctorRecyclerView;
    private DoctorViewModel doctorViewModel;

    LinearLayout searchContainer;
    int currentSate = 0;//0: dim, 1:bright

    private EditText searchEditText;

    View root;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_doctor, container, false);

        setUpRecyclerView();

        searchEditText = root.findViewById(R.id.doctor_searchBox_editText);

        searchContainer = root.findViewById(R.id.doctor_searchContainer_linearLayout);

        searchEditText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable editable) {
                        doctorAdapter.filter(editable.toString().trim());
                    }
                });

        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(currentSate==0){
                    currentSate = 1;
                    searchContainer.animate().alpha(0.8f).setDuration(500).start();
                }
                return false;
            }
        });

        (root.findViewById(R.id.root)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentSate==1){
                    currentSate = 0;
                    searchContainer.animate().alpha(0.1f).setDuration(500).start();
                }
            }
        });

        searchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentSate==0){
                    currentSate = 1;
                    searchContainer.animate().alpha(0.8f).setDuration(500).start();
                }
            }
        });

        return root;
    }

    // COMPLETED(SHANK) implement following method
    void setUpRecyclerView() {
        doctorAdapter =
                new DoctorAdapter(
                        new ArrayList<Doctor>(),
                        new OnItemClickListener<Doctor>() {
                            @Override
                            public void onItemClicked(Doctor d, View v) {
                                Intent intent = new Intent(getActivity(), DoctorActivity.class);
                                intent.putExtra("doctor", d);

                                startActivity(intent);
                            }
                        });

        doctorRecyclerView = root.findViewById(R.id.doctor_showList_recycler);
        doctorRecyclerView.setHasFixedSize(false);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        doctorRecyclerView.setAdapter(doctorAdapter);

        doctorRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy >= 0){
                    currentSate = 0;
                    searchContainer.animate().alpha(0.1f).setDuration(500).start();
                }else{
                    currentSate = 1;
                    searchContainer.animate().alpha(0.8f).setDuration(500).start();
                }
            }
        });

        doctorViewModel = ViewModelProviders.of(this).get(DoctorViewModel.class);
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
                        });
    }
}
