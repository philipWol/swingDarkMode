package com.phili.business.entity;

public class Address{
    private String postCode;
    private String city;
    private Integer houseNumber;

    public Address() {
        this(null, null, null);
    }

    public Address(String postCode, String city, Integer houseNumber) {
        this.postCode = postCode;
        this.city = city;
        this.houseNumber = houseNumber;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }
}
