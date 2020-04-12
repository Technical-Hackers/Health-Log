package com.example.healthlog.model;

import java.util.List;

public class Doctor {

    String name;
    String department;
    String noOfPatient;
    String status;
    String logDescription;
    Integer type = 0;
    List<String> location;

    public Doctor() {
    }

    public Doctor(String name, String department, String NoOfPatient, String status) {
        this.name = name;
        this.department = department;
        this.noOfPatient = NoOfPatient;
        this.status = status;
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
        this.noOfPatient = noOfPatient;
    }

    public String getNoOfPatient() {
        return this.noOfPatient;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }
    public List<String> getLocation() {
        return this.location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public String getLogDescription() {
        return this.logDescription;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
