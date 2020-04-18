package com.example.healthlog.model;

import android.graphics.Color;

import com.example.healthlog.R;
import com.google.firebase.Timestamp;
import java.util.List;

public class Patient {

    String id;
    String name;
    String address;
    String age;
    String status;
    String recentLog;
    String dob;

    int statusColor;
    int statusDrawable;

    Timestamp dateAdded;

    List<String> location;

    Integer type = 0;

    public Patient() {}

    public Patient(String name, String status, String recentLog) {
        this.name = name;
        this.status = status;
        this.recentLog = recentLog;
        setStatusColor();
        setStatusDrawable();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        setStatusColor();
        setStatusDrawable();
    }

    public void setRecentLog(String recentLog) {
        this.recentLog = recentLog;
    }

    public String getRecentLog() {
        return this.recentLog;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getStatusColor() {
        return statusColor;
    }

    private void setStatusColor() {
        if(status.equals("Active")){
            statusColor = R.color.activeLightText;
        }else if(status.equals("Cured")){
            statusColor = R.color.recoveredLightText;
        }else if(status.equals("Deceased")){
            statusColor = R.color.deceasedLightText;
        }else{
        }
    }

    public int getStatusDrawable() {
        return statusDrawable;
    }

    private void setStatusDrawable(){
        if(status.equals("Active")){
            statusDrawable = R.drawable.active_status_circle;
        }else if(status.equals("Cured")){
            statusDrawable = R.drawable.cured_status_circle;
        }else if(status.equals("Deceased")){
            statusDrawable = R.drawable.deceased_status_circle;
        }else{

        }
    }

    public void refreshStatus(){
        setStatusColor();
        setStatusDrawable();
    }
}
