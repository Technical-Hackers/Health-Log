package com.example.healthlog.model;

// COMPLETED(SHANK) implement the class

public class SuspectedPatient {

    String name;
    String age;
    String email;
    String contact;

    public SuspectedPatient(String name, String age, String email, String contact) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.contact = contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
