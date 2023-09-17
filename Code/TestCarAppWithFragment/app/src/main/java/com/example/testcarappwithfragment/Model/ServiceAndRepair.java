package com.example.testcarappwithfragment.Model;

public class ServiceAndRepair {
    private int image;
    private String offer;

    public ServiceAndRepair(int image , String offer){
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
