package com.example.testcarappwithfragment.Model;

public class OrderMasterDetailList {
    private String carModelName;
    private byte[] carImage;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public OrderMasterDetailList(String carModelName, byte[] carImage, int quantity, double unitPrice, double totalPrice) {
        this.carModelName = carModelName;
        this.carImage = carImage;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public OrderMasterDetailList() {
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public byte[] getCarImage() {
        return carImage;
    }

    public void setCarImage(byte[] carImage) {
        this.carImage = carImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
