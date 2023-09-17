package com.example.testcarappwithfragment.Model;

public class NewCars {
    private int image;
    private String offer;

    public NewCars(int image , String offer){
        this.image = image;
        this.offer = offer;
    }

    public int getImage() {
        return image;
    }

    public String getOffer() {
        return offer;
    }
}
