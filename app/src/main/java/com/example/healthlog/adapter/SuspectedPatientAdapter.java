package com.example.healthlog.adapter;

// COMPLETED(SHANK) implement suspectedPatientAdapter using @SuspectedPatient model

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthlog.R;
import com.example.healthlog.interfaces.OnItemClickListener;
import com.example.healthlog.model.SuspectedPatient;
import java.util.List;

public class SuspectedPatientAdapter
        extends RecyclerView.Adapter<SuspectedPatientAdapter.SuspectedPatientViewHolder> {

    private List<SuspectedPatient> suspectedPatientList;

    OnItemClickListener listener;

    public SuspectedPatientAdapter(List<SuspectedPatient> suspectedPatientList) {
        this.suspectedPatientList = suspectedPatientList;
    }

    @NonNull
    @Override
    public SuspectedPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.suspected_patient_list_item, parent, false);
        return new SuspectedPatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SuspectedPatientViewHolder holder, int position) {
        SuspectedPatient suspectedPatient = suspectedPatientList.get(position);
        holder.suspectName.setText(suspectedPatient.getName());
        holder.suspectAge.setText(suspectedPatient.getAge());
        holder.suspectMobile.setText(suspectedPatient.getContact());
        holder.bind(suspectedPatient, listener);
    }

    public void add(SuspectedPatient suspectedPatient) {
        suspectedPatientList.add(suspectedPatient);
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return suspectedPatientList.size();
    }

    public class SuspectedPatientViewHolder extends RecyclerView.ViewHolder {

        TextView suspectName, suspectAge, suspectMobile;
        View view;

        public SuspectedPatientViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            suspectName = itemView.findViewById(R.id.suspect_list_item_name_textView);
            suspectAge = itemView.findViewById(R.id.suspect_list_item_age_textView);
            suspectMobile = itemView.findViewById(R.id.suspect_list_item_mobile_textView);
        }

        void bind(
                final SuspectedPatient suspectedPatient, @Nullable final OnItemClickListener listener) {
            view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onItemClicked(suspectedPatient, view);
                        }
                    });
        }
    }
}
