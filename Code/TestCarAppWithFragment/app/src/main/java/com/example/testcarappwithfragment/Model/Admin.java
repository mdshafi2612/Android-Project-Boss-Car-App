package com.example.testcarappwithfragment.Model;

public class Admin {
     private String adminEmail;
     private String adminName;
     private int phoneNumber;
     private String Address;
     private String dateOfBirth;
     private String password;

    public Admin(String adminEmail, String adminName, int phoneNumber, String address, String dateOfBirth, String password) {
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.phoneNumber = phoneNumber;
        Address = address;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public Admin(){

    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
