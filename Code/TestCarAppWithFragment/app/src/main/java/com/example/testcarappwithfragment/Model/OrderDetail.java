package com.example.testcarappwithfragment.Model;

import java.util.Date;

public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int carId;
    private int quantity;
    private double price;
    private String addedOn;

    public OrderDetail(int orderDetailId, int orderId, int carId, int quantity, double price, String addedOn) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.carId = carId;
        this.quantity = quantity;
        this.price = price;
        this.addedOn = addedOn;
    }

    public OrderDetail(){

    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
