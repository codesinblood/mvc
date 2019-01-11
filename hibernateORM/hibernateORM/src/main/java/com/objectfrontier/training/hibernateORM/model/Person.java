package com.objectfrontier.training.hibernateORM.model;

import java.sql.Timestamp;


public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private String birthDate;
    private Timestamp createdDate;
    private String password;
    private Boolean isAdmin;

    public Person(long id, String first_name, String last_name, String email, Address address, String birth_date) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.address = address;
        this.birthDate = birth_date;
    }


    public Person(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }


    public Person(String first_name, String last_name, String email, Address address, String birth_date) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.address = address;
        this.birthDate = birth_date;
    }

    public Person(String first_name, String last_name, String email, Address address, String birth_date, String password, Boolean isAdmin) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.address = address;
        this.birthDate = birth_date;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getIsAdmin() {
        return isAdmin;
    }


    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBirth_date() {
        return birthDate;
    }

    public void setBirth_date(String birth_date) {
        this.birthDate = birth_date;
    }

    public Timestamp getCreated_date() {
        return createdDate;
    }

    public void setCreated_date(Timestamp created_date) {
        this.createdDate = created_date;
    }

    public String getBirthDate() {
        return birthDate;
    }


    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public Timestamp getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", address=" + address + ", birthDate=" + birthDate + ", createdDate=" + createdDate + ", password="
                + password + ", isAdmin=" + isAdmin + "]";
    }

}
