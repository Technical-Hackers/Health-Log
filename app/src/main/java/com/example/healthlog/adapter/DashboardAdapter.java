package com.example.healthlog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthlog.R;
import com.example.healthlog.model.Patient;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private List<Patient> patientList;
    private Context mcontext;

    public DashboardAdapter(List<Patient> patientList ) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);
        return new DashboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.patientName.setText(patient.getName());
        holder.patientStatus.setText(patient.getStatus());
        holder.patientLogDescription.setText(patient.getRecentLog());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        TextView patientName;
        TextView patientStatus;
        TextView patientLogDescription;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.patient_list_item_name_textView);
            patientStatus = itemView.findViewById(R.id.patient_list_item_statusText_textView);
            patientLogDescription = itemView.findViewById(R.id.patient_list_item_logDescription_textView);
        }
    }


}
