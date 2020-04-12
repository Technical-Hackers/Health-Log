package com.example.healthlog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthlog.R;
import com.example.healthlog.model.Doctor;
import com.example.healthlog.model.Patient;

import org.w3c.dom.Text;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;
    private Context mContext;

    public DoctorAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent, false);
        return new DoctorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.doctorName.setText(doctor.getName());
        holder.doctorStatus.setText(doctor.getStatus());
        holder.doctorLogDescription.setText(doctor.getLogDescription());
        holder.doctorRoom.setText(doctor.getLocation().toString());
        holder.doctorType.setText(doctor.getDepartment());
        if (doctor.getStatus().equals("Available")) {
            holder.doctorColorStatus.setBackgroundColor(0xFF00FF00);
        } else {
            holder.doctorColorStatus.setBackgroundColor(0xFFFF0000);
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public void add(Doctor d){
        doctorList.add(d);
        notifyDataSetChanged();
    }


    public class DoctorViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName;
        TextView doctorStatus;
        TextView doctorLogDescription;
        TextView doctorRoom;
        TextView doctorType;

        View doctorColorStatus;
        View view;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            doctorName = itemView.findViewById(R.id.doctor_list_item_name_textView);
            doctorStatus = itemView.findViewById(R.id.doctor_list_item_statusText_textView);
            doctorLogDescription = itemView.findViewById(R.id.doctor_list_item_logDescription_textView);
            doctorRoom = itemView.findViewById(R.id.doctor_list_item_dateAdded_textView);
            doctorColorStatus = itemView.findViewById(R.id.doctor_list_item_statusCircle_view);
            doctorType = itemView.findViewById(R.id.doctor_list_item_type_textView);
        }
    }
}
