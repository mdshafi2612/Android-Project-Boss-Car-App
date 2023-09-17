package com.example.testcarappwithfragment.Model;

public class Customer {
     private int customerId;
     private String email;
     private String customerName;
     private int phoneNumber;
     private String Address;
     private String dateOfBirth;
     private String password;

    public Customer(int customerId, String email, String customerName, int phoneNumber, String address, String password) {
        this.customerId = customerId;
        this.email = email;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        Address = address;
        this.password = password;
    }

    public Customer(){

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

   /* public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
