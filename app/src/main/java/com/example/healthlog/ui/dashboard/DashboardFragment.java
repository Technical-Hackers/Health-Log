package com.example.healthlog.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthlog.R;
import com.example.healthlog.adapter.DashboardAdapter;
import com.example.healthlog.handler.NewPatientHandler;
import com.example.healthlog.model.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private ArrayList<Patient> patientList = new ArrayList<>();
    private DashboardViewModel dashboardViewModel;
    private DashboardAdapter dashboardAdapter;
    private RecyclerView dashboardRecyclerView;

    private Spinner spinner;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // TODO(Shashank) attach observer and update the array
        dashboardAdapter = new DashboardAdapter(patientList);
        dashboardRecyclerView = (RecyclerView) root.findViewById(R.id.dashboard_showList_recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        dashboardRecyclerView.setLayoutManager(mLayoutManager);
        dashboardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dashboardRecyclerView.setAdapter(dashboardAdapter);


        dashboardRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        FloatingActionButton addPatient = (FloatingActionButton) root.findViewById(R.id.dashboard_add_fab);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPatient();
            }
        });

        spinner = root.findViewById(R.id.dashboard_list_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.statusArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);
        // TODO(Danish) apply filter based on spinner selection
        preparePatientData();
        return root;
    }

    private void preparePatientData() {
        Patient patient = new Patient("Ram", "Active", "Three checkup completed");
        patientList.add(patient);

        patient = new Patient("Shyam", "Cured", "One checkup");
        patientList.add(patient);

        patient = new Patient("Kumar", "Deceased", "Four checkup late arrival");
        patientList.add(patient);

        dashboardAdapter.notifyDataSetChanged();
    }

    public void addNewPatient() {
        NewPatientHandler handler = new NewPatientHandler(getContext(), getActivity());
        handler.init();
    }
}
