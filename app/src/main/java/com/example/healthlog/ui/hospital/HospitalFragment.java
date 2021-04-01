package com.example.healthlog.ui.hospital;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthlog.R;
import com.example.healthlog.adapter.SuspectedPatientAdapter;
import com.example.healthlog.handler.HospitalProfileHandler;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.Hospital;
import com.example.healthlog.model.SuspectedPatient;
import java.util.ArrayList;

// run this for spotless check  "./gradlew spotlessApply"
public class HospitalFragment extends Fragment {

    // COMPLETED(SHANK) create recyclerView
    // COMPLETED(SHANK) display list of requested patients

    /* COMPLETED(INSTRUCTION)
    * 1. When user tap on listItem an alert dialog will popUp
    * 2. Alert dialog will have 2 options
    *   2.1. Positive -> add to ur hospital
    *   2.2. Negative -> send request to diff hospital
    * 3. And call form viewModel @addPatientToHospital for +ve and @sendRequestToHospital for -ve.
    * */

    // COMPLETED(SHANK) implement ui

    private HospitalViewModel notificationsViewModel;

    private SuspectedPatientAdapter suspectedAdapter;
    private RecyclerView suspectedRecyclerView;
    View root;

    TextView totalPatientsTv;
    TextView activePatientsTv;
    TextView curedPatientsTv;
    TextView deceasedPatientsTv;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(HospitalViewModel.class);
        root = inflater.inflate(R.layout.fragment_hospital, container, false);

        setup();
        showSuspectList();
        showHospitalProfile();
        return root;
    }

    // COMPLETED(SHANK) find all views, create object of viewModel and call attachModel
    void setup() {
        totalPatientsTv = root.findViewById(R.id.hospital_confirmed_textView);
        activePatientsTv = root.findViewById(R.id.hospital_active_textView);
        curedPatientsTv = root.findViewById(R.id.hospital_cured_textView);
        deceasedPatientsTv = root.findViewById(R.id.hospital_deceased_textView);

        notificationsViewModel = ViewModelProviders.of(this).get(HospitalViewModel.class);
        notificationsViewModel.init(getContext());
        notificationsViewModel
                .getNoOfActivePatient()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                activePatientsTv.setText(integer.toString());
                            }
                        });

        notificationsViewModel
                .getTotalNoOfPatient()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                totalPatientsTv.setText(integer.toString());
                            }
                        });

        notificationsViewModel
                .getNoOfCuredPatient()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                curedPatientsTv.setText(integer.toString());
                            }
                        });

        notificationsViewModel
                .getNoOfDeceasedPatient()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                deceasedPatientsTv.setText(integer.toString());
                            }
                        });
    }

    public void showSuspectList() {
        suspectedAdapter = new SuspectedPatientAdapter(new ArrayList<SuspectedPatient>());
        suspectedAdapter.setListener(
                new OnItemClickListener<SuspectedPatient>() {
                    @Override
                    public void onItemClicked(SuspectedPatient suspectedPatient, View v) {
                        showDialog(suspectedPatient);
                    }
                });

        suspectedRecyclerView = root.findViewById(R.id.hospital_suspect_list_recyclerView);
        suspectedRecyclerView.setHasFixedSize(false);
        suspectedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        suspectedRecyclerView.setAdapter(suspectedAdapter);

        notificationsViewModel = ViewModelProviders.of(requireActivity()).get(HospitalViewModel.class);
        notificationsViewModel.initSuspectList(getActivity());
        notificationsViewModel
                .getSuspectPatientsList()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<ArrayList<SuspectedPatient>>() {
                            @Override
                            public void onChanged(ArrayList<SuspectedPatient> suspectedPatients) {
                                for (SuspectedPatient s : suspectedPatients) {
                                    suspectedAdapter.add(s);
                                }
                            }
                        });
    }

    public void showHospitalProfile(){
        final HospitalProfileHandler hospitalProfileHandler = new HospitalProfileHandler(getContext(),getActivity());
        Button hospitalProfile = root.findViewById(R.id.hospital_profile);

        final Hospital hospital = new Hospital();

        // Set Hospital info HERE
        hospital.setId("H1");
        hospital.setName("Sir Sunderlal Hospital");
        hospital.setAddress("31/3,Andheri,Mumbai");
        hospital.setBedCount(120);
        hospital.setDoctorCount(30);

        hospitalProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hospitalProfileHandler.update(hospital);
                hospitalProfileHandler.init();
            }
        });
    }

    public void showDialog(final SuspectedPatient currentSuspectedPatient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme);
        // Set the message show for the Alert time
        builder.setMessage(R.string.add_patient);
        builder.setTitle(R.string.alert);
        builder.setCancelable(true);
        builder.setPositiveButton(
                android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notificationsViewModel.addPatientToHospital(currentSuspectedPatient);
                    }
                });
        builder.setNegativeButton(
                R.string.send,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notificationsViewModel.sendRequestToHospital(currentSuspectedPatient);
                    }
                });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
