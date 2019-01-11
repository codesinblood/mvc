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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + postal_code;
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (postal_code != other.postal_code)
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        return true;
    }

}
