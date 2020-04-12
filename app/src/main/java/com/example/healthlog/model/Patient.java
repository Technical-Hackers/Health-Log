package com.example.healthlog.model;

import com.google.firebase.Timestamp;
<<<<<<< HEAD
import com.google.firebase.firestore.FieldValue;
import com.google.firestore.v1.DocumentTransform;

import java.util.Date;
=======
>>>>>>> spinner
import java.util.List;

public class Patient {


    String id;
    String name;
    String address;
    String age;
    String status;
    String recentLog;

    Timestamp dob;// manual
    Timestamp dateAdded;//date of data entry, automatically
<<<<<<< HEAD

    List<String> location;//[floor, room_no, bed_no], manual

=======

    List<String> location;//[floor, room_no, bed_no], manual
>>>>>>> spinner

    public Patient() {
    }

    public Patient(String name, String status, String recentLog) {
        this.name = name;
        this.status = status;
        this.recentLog = recentLog;
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

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
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
    }

    public void setRecentLog(String recentLog) {
        this.recentLog = recentLog;
    }

    public String getRecentLog() {
        return this.recentLog;
    }
}
