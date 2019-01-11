package com.objectfrontier.training.java.service.pojo;

public class Address {

    public Address(long id, String city, String street, int postal_code) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.postal_code = postal_code;
    }

    public Address(String city, String street, int postal_code) {
        this.city = city;
        this.street = street;
        this.postal_code = postal_code;
    }


    public Address() {
        // TODO Auto-generated constructor stub
    }


    private long id;

    private String city;

    private String street;

    private int postal_code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", city=" + city + ", street=" + street + ", postal_code=" + postal_code + "]";
    }

}
