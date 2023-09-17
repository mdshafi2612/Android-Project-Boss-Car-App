package com.example.testcarappwithfragment.Model;

public class OrderStatus {
    private int orderStatusId;
    private String orderStatusName;

    public OrderStatus(int orderStatusId, String orderStatusName) {
        this.orderStatusId = orderStatusId;
        this.orderStatusName = orderStatusName;
    }

    public OrderStatus(){

    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
