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

    View root;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // TODO(Shashank) attach observer and update the array

        setUpRecyclerView();
        setUpSpinner();
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

    void setUpRecyclerView(){
        dashboardAdapter = new DashboardAdapter(new ArrayList<Patient>());
        dashboardRecyclerView = (RecyclerView) root.findViewById(R.id.dashboard_showList_recycler);

        dashboardRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        dashboardRecyclerView.setHasFixedSize(false);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dashboardRecyclerView.setAdapter(dashboardAdapter);


        dashboardAdapter.add(new Patient("Ram", "Active", "Three checkup completed"));
        dashboardAdapter.add(new Patient("Shyam", "Cured", "One checkup"));
        dashboardAdapter.add(new Patient("Kumar", "Deceased", "Four checkup late arrival"));

        dashboardAdapter.add(new Patient("Arun", "Active", "Three checkup completed"));
        dashboardAdapter.add(new Patient("Dev", "Cured", "One checkup"));
        dashboardAdapter.add(new Patient("Rishu", "Deceased", "Four checkup late arrival"));

        dashboardAdapter.add(new Patient("Mohini", "Active", "Three checkup completed"));
        dashboardAdapter.add(new Patient("Tinku", "Cured", "One checkup"));
        dashboardAdapter.add(new Patient("Rinkiya", "Deceased", "Four checkup late arrival"));

    }

    // COMPLETED(Danish) apply filter based on spinner selection
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
