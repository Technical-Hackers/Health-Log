package com.example.healthlog.model;

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {

    // COMPLETED(DJ) add verify field

    String id;
    String name;
    String department;
    String status;
    List<String> location;
    Integer noOfPatients;
    String verifyKey;

    public Doctor() {}

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public Integer getNoOfPatients() {
        return noOfPatients;
    }

    public void setNoOfPatients(Integer noOfPatients) {
        this.noOfPatients = noOfPatients;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }
}
