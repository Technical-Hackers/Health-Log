package com.example.healthlog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthlog.R;
import com.example.healthlog.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private List<Patient> allPatientList;
    private List<Patient> currentPatientList;

    private Context mcontext;
    private String currentFilter = "Deceased";

    public DashboardAdapter(List<Patient> patientList ) {
        this.allPatientList = patientList;
        currentPatientList = new ArrayList<>();

    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);
        return new DashboardViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        // COMPLETED(Shashank) add colors to res directory rather than hardcoding
        Patient patient = currentPatientList.get(position);
        holder.patientName.setText(patient.getName());
        holder.patientStatus.setText(patient.getStatus());
        holder.patientLogDescription.setText(patient.getRecentLog());
        if (patient.getStatus().equals("Active")) {
            holder.patientcolorStatus.setBackgroundColor(0xFFFF0000);
        } else if (patient.getStatus().equals("Cured")) {
            holder.patientcolorStatus.setBackgroundColor(0xFF00FF00);
        } else {
            holder.patientcolorStatus.setBackgroundColor(0xFFC0C0C0);
        }
    }

    @Override
    public int getItemCount() {
        return currentPatientList.size();
    }

    public void add(Patient p){
        allPatientList.add(p);
        if(currentFilter.equals("All")){
            currentPatientList.clear();
            currentPatientList.addAll(allPatientList);
            notifyDataSetChanged();
            return;
        }
        if(p.getStatus().equals(currentFilter)){
            currentPatientList.add(p);
            notifyDataSetChanged();
        }
    }

    // COMPLETED(Danish) handle filter  here
    public void applyFilter(String filter){
        currentFilter = filter;
        if(filter.equals("All")){
            for(Patient p: allPatientList){
                if(!currentPatientList.contains(p)){
                    currentPatientList.add(p);
                }
            }

            notifyDataSetChanged();
            return;
        }
        currentPatientList.clear();
        for(Patient p: allPatientList){
            if(p.getStatus().equals(filter)){
                currentPatientList.add(p);
            }
        }
        notifyDataSetChanged();
    }

    // COMPLETED(Shashank) create Add and AddAll method to add new patients in array

    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        TextView patientName;
        TextView patientStatus;
        TextView patientLogDescription;
        View patientcolorStatus;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.patient_list_item_name_textView);
            patientStatus = itemView.findViewById(R.id.patient_list_item_statusText_textView);
            patientLogDescription = itemView.findViewById(R.id.patient_list_item_logDescription_textView);
            patientcolorStatus = itemView.findViewById(R.id.patient_list_item_statusCircle_view);
        }
    }


}
