package com.example.testcarappwithfragment.Model;

public class OrderMasterList {
    private int orderID;
    private String orderDate;
    private String address;
    private String paymentType;
    private String paymentStatus;
    private String orderStatus;

    public OrderMasterList(int orderID, String orderDate, String address, String paymentType, String paymentStatus, String orderStatus) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.address = address;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
    }

    public OrderMasterList(){

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
