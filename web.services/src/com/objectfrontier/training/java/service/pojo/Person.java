package com.objectfrontier.training.java.service.pojo;

import java.sql.Timestamp;

public class Person {

    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private Address address;
    private String birth_date;
    private Timestamp created_date;

    public Person(long id, String first_name, String last_name, String email, Address address, String birth_date) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.birth_date = birth_date;
    }


    public Person(String first_name, String last_name, String email, Address address, String birth_date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.birth_date = birth_date;
    }




    public Person() {
        // TODO Auto-generated constructor stub
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String fname) {
        this.first_name = fname;
    }


    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lname) {
        this.last_name = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    @Override
    public String toString() {
        return "Person [first_name=" + first_name + ", last_name=" + last_name + ", email=" + email + ", address="
                + address + ", birth_date=" + birth_date + ", created_date=" + created_date + "]";
    }

}
