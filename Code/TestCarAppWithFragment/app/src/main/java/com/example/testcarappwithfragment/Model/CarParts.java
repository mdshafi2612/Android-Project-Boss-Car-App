package com.example.testcarappwithfragment.Model;

public class CarParts {
    private int carPartsId;
    private String carPartsName;
    private int quantity;
    private double price;
    private String voucher;
    private byte[] carPartsImage;

    public CarParts(int carPartsId, String carPartsName, int quantity, double price, String voucher, byte[] carPartsImage) {
        this.carPartsId = carPartsId;
        this.carPartsName = carPartsName;
        this.quantity = quantity;
        this.price = price;
        this.voucher = voucher;
        this.carPartsImage = carPartsImage;
    }

    public CarParts() {
    }

    public int getCarPartsId() {
        return carPartsId;
    }

    public void setCarPartsId(int carPartsId) {
        this.carPartsId = carPartsId;
    }

    public String getCarPartsName() {
        return carPartsName;
    }

    public void setCarPartsName(String carPartsName) {
        this.carPartsName = carPartsName;
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public byte[] getCarPartsImage() {
        return carPartsImage;
    }

    public void setCarPartsImage(byte[] carPartsImage) {
        this.carPartsImage = carPartsImage;
    }
}
