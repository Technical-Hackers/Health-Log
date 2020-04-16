package com.example.healthlog.model;

// COMPLETED(SHANK) implement the class

public class SuspectedPatient {

    String name;
    String type;
    String address;

    String age;
    String email;
    String contact;

    public SuspectedPatient() {}

    public SuspectedPatient(String name, String age, String email, String contact) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
