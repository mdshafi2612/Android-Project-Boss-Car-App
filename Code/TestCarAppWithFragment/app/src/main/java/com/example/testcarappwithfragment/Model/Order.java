package com.example.testcarappwithfragment.Model;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private String address;
    private String city;
    private int pincode;
    private String paymentType;
    private double totalPrice;
    private String paymentStatus;
    private int orderStatus;
    private String addedOn;

    public Order(int id, int userId, String address, String city, int pincode, String paymentType, double totalPrice, String paymentStatus, int orderStatus, String addedOn) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.paymentType = paymentType;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.addedOn = addedOn;
    }

    public Order(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
