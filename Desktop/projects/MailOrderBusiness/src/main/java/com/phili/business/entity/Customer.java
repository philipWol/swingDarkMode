package com.phili.business.entity;

public class Customer {
    private String customerNumber;
    private String firstName;
    private String lastName;
    private Address address;

    public Customer() {
        this(null, null, null, null); //constructor chaining
    }

    public Customer(String customerNumber, String firstName, String lastName, Address address) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerNumber='" + customerNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", " + address +
                '}';
    }

    public String toCsv(){
        return customerNumber + "," + firstName + "," + lastName + "," + address;
    }

    public void print() {
       System.out.println(this);
    }
}
