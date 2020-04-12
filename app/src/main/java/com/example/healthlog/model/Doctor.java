package com.example.healthlog.model;

import java.util.List;

public class Doctor {

    String name;
    String department;
    String NoOfPatient;

    List<String> location;

    public Doctor() {
    }

    public Doctor(String name, String department, String NoOfPatient) {
        this.name = name;
        this.department = department;
        this.NoOfPatient = NoOfPatient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setNoOfPatient(String noOfPatient) {
        this.NoOfPatient = noOfPatient;
    }

    public String getNoOfPatient() {
        return this.NoOfPatient;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }
    public List<String> getLocation() {
        return this.location;
    }
}
