package com.example.testcarappwithfragment.Model;

public class WishlistCarProperty {
    private String carModelName;
    private byte[] carImage;
    private double carPrice;
    private int wishlistID;
    private int wishlistCarID;

    public WishlistCarProperty(String carModelName, byte[] carImage, double carPrice, int wishlistID, int wishlistCarID) {
        this.carModelName = carModelName;
        this.carImage = carImage;
        this.carPrice = carPrice;
        this.wishlistID = wishlistID;
        this.wishlistCarID = wishlistCarID;
    }

    public WishlistCarProperty() {
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

    public double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(double carPrice) {
        this.carPrice = carPrice;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public int getWishlistCarID() {
        return wishlistCarID;
    }

    public void setWishlistCarID(int wishlistCarID) {
        this.wishlistCarID = wishlistCarID;
    }
}
