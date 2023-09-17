package com.example.testcarappwithfragment.Model;

public class CarBrand {
    private int carBrandId;
    private String carBrandName;
    private int status;

    public CarBrand(){

    }

    public CarBrand(int carBrandId, String carBrandName, int status) {
        this.carBrandId = carBrandId;
        this.carBrandName = carBrandName;
        this.status = status;
    }

    public int getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(int carBrandId) {
        this.carBrandId = carBrandId;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
