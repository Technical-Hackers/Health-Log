package com.example.healthlog.ui.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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

public class DashboardFragment extends Fragment {

    // COMPLETED(DJ) add ID in patient_list_item

    // COMPLETED(DJ) create layout for detailed view of patient

    // COMPLETED(DJ) create search feature

    // COMPLETED(DJ) reformat ui file for this fragment

    private DashboardViewModel dashboardViewModel;
    private DashboardAdapter dashboardAdapter;
    private RecyclerView dashboardRecyclerView;

    RelativeLayout searchContainer;

    private Spinner spinner;

    private EditText searchEditText;

    private Spinner category;

    View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        Toast.makeText(getActivity(), HealthLog.ID, Toast.LENGTH_SHORT).show();
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setUpRecyclerView();
        setUpSpinner();

        // search edit text
        searchEditText = root.findViewById(R.id.dashboard_searchBox_editText);

        searchContainer = root.findViewById(R.id.dashboard_searchContainer_relativeLayout);

        searchEditText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable editable) {
                        dashboardAdapter.filter(editable.toString().trim());
                    }
                });


        // FAB
        FloatingActionButton addPatient =
                (FloatingActionButton) root.findViewById(R.id.dashboard_add_fab);
        addPatient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNewPatient();
                    }
                });
        return root;

    }

    public void addNewPatient() {
        NewPatientHandler handler = new NewPatientHandler(getContext());
        handler.init();
    }

    void setUpRecyclerView() {
        final PatientViewHandler patientViewHandler =
                new PatientViewHandler(getContext(), getActivity());
        dashboardAdapter =
                new DashboardAdapter(getContext(),
                        new ArrayList<Patient>(),
                        new OnItemClickListener<Patient>() {
                            @Override
                            public void onItemClicked(Patient patient, View v) {
                                patientViewHandler.update(patient);
                                patientViewHandler.init();
                            }
                        });

        dashboardRecyclerView = root.findViewById(R.id.dashboard_showList_recycler);
        dashboardRecyclerView.setHasFixedSize(false);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dashboardRecyclerView.setAdapter(dashboardAdapter);

        dashboardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    searchContainer.animate().alpha(0.1f).setDuration(500).start();
                }else{
                    searchContainer.animate().alpha(0.8f).setDuration(500).start();
                }
            }
        });

        dashboardViewModel = ViewModelProviders.of(requireActivity()).get(DashboardViewModel.class);
        dashboardViewModel.init(getActivity());
        dashboardViewModel
                .getPatientsList()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<ArrayList<Patient>>() {
                            @Override
                            public void onChanged(ArrayList<Patient> patientModels) {
                                for (Patient p : patientModels) {
                                    dashboardAdapter.add(p);
                                }
                            }
                        });
    }

    void setUpSpinner() {
        final String[] sts = { "Active", "Cured", "Deceased","All"};
        spinner = root.findViewById(R.id.dashboard_list_spinner);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getActivity(), R.layout.spinner_item, sts);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner.setSelection(i);
                        dashboardAdapter.applyFilter(sts[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_spinner, menu);
        inflater.inflate(R.menu.menu_logout, menu);
        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dashboardAdapter.filter(newText.trim());
                return true;
            }
        });

        MenuItem spinnerItem=menu.findItem(R.id.spinner);
        category=(Spinner)spinnerItem.getActionView();

        final String[] sts = { "Active", "Cured", "Deceased","All"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, sts);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(adapter);
        category.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        category.setSelection(i);
                        dashboardAdapter.applyFilter(sts[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
