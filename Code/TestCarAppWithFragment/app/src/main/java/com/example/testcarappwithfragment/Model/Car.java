package com.example.testcarappwithfragment.Model;

public class Car {
    private String plate_no;
    private String Brand;
    private String Model;
    private String Owner;
    private Double Price;
    private byte[] CarImage;
    private String shortDescription;
    private int BrandId;
    private int id;
    private int status;
    private int quantity;

    public Car() {
    }

    public Car(String plate_no, String brand, String model, String owner, Double price, byte[] carImage, String shortDescription, int brandId, int id, int status, int quantity) {
        this.plate_no = plate_no;
        Brand = brand;
        Model = model;
        Owner = owner;
        Price = price;
        CarImage = carImage;
        this.shortDescription = shortDescription;
        BrandId = brandId;
        this.id = id;
        this.status = status;
        this.quantity = quantity;
    }

    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public byte[] getCarImage() {
        return CarImage;
    }

    public void setCarImage(byte[] carImage) {
        CarImage = carImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
