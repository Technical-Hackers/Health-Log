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
import android.widget.Toast;

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

import com.example.healthlog.HealthLog;
import com.example.healthlog.R;
import com.example.healthlog.adapter.DashboardAdapter;
import com.example.healthlog.handler.NewPatientHandler;
import com.example.healthlog.handler.PatientViewHandler;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.grpc.Context;

public class DashboardFragment extends Fragment {

    // COMPLETED(DJ) create layout for detailed view of patient

    // TODO(DJ) create search feature

    // COMPLETED(DJ) reformat ui file for this fragment

    private DashboardViewModel dashboardViewModel;
    private DashboardAdapter dashboardAdapter;
    private RecyclerView dashboardRecyclerView;

    private Spinner spinner;

    View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Toast.makeText(getActivity(), HealthLog.ID, Toast.LENGTH_SHORT).show();
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setUpRecyclerView();
        setUpSpinner();

        //Spinner
        spinner = root.findViewById(R.id.dashboard_list_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.statusArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);

        // FAB
        FloatingActionButton addPatient = (FloatingActionButton) root.findViewById(R.id.dashboard_add_fab);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPatient();
            }
        });
        return root;
    }

    public void addNewPatient() {
        NewPatientHandler handler = new NewPatientHandler(getContext(), getActivity());
        handler.init();
    }

    void setUpRecyclerView() {
        final PatientViewHandler patientViewHandler = new PatientViewHandler(getContext(), getActivity());
        dashboardAdapter = new DashboardAdapter(new ArrayList<Patient>(), new OnItemClickListener() {
            @Override
            public void onItemClicked(Patient patient) {
                patientViewHandler.update(patient);
                patientViewHandler.init();
            }
        });

        dashboardRecyclerView = root.findViewById(R.id.dashboard_showList_recycler);

        dashboardRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        dashboardRecyclerView.setHasFixedSize(false);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dashboardRecyclerView.setAdapter(dashboardAdapter);



        dashboardViewModel = ViewModelProviders.of(requireActivity()).get(DashboardViewModel.class);
        dashboardViewModel.init(getActivity());
        dashboardViewModel
                .getPatientsList()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<ArrayList<Patient>>() {
                            @Override
                            public void onChanged(ArrayList<Patient> patientModels) {
                                for(Patient p: patientModels){
                                    dashboardAdapter.add(p);
                                }

                            }
                        });
    }

    void setUpSpinner(){
        spinner = root.findViewById(R.id.dashboard_list_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.statusArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String[] sts = {"All", "Active", "Cured", "Deceased"};
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setSelection(i);
                dashboardAdapter.applyFilter(sts[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
