package com.example.testcarappwithfragment.Model;

public class Cart {
    private int cartId;
    private int userId;
    private String userEmail;
    private String userPassword;
    private int carId;
    private String carBrand;
    private String carModel;
    private byte[] carImage;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

    public Cart(int cartId, int userId, String userEmail, String userPassword, int carId, String carBrand, String carModel,byte[] carImage, double unitPrice, int quantity, double totalPrice) {
        this.cartId = cartId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carImage = carImage;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Cart(){

    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public byte[] getCarImage() {
        return carImage;
    }

    public void setCarImage(byte[] carImage) {
        this.carImage = carImage;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
