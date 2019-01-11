package com.objectfrontier.training.hibernate.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="person")
@Table(name="person_service")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="fname")
    private String first_name;
    @Column(name="lname")
    private String last_name;
    @Column(name="email")
    private String email;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;
    @Column(name="birth_date")
    private String birth_date;
    @Column(name="created_date")
    private Timestamp created_date;
    @Column(name="password")
    private String password;
    @Column(name="isAdmin")
    private Boolean isAdmin;

    public Person(long id, String first_name, String last_name, String email, Address address, String birth_date) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.birth_date = birth_date;
    }


    public Person(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }


    public Person(String first_name, String last_name, String email, Address address, String birth_date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.birth_date = birth_date;
    }

    public Person(String first_name, String last_name, String email, Address address, String birth_date, String password, Boolean isAdmin) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.birth_date = birth_date;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Person() {
        super();
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
        return "Person [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
                + ", address=" + address + ", birth_date=" + birth_date + ", created_date=" + created_date
                + ", password=" + password + ", isAdmin=" + isAdmin + "]";
    }


}
