package com.example.testcarappwithfragment.Model;

public class Wishlist {
    private int id;
    private int userID;
    private int carID;
    private String addedOn;

    public Wishlist(int id, int userID, int carID, String addedOn) {
        this.id = id;
        this.userID = userID;
        this.carID = carID;
        this.addedOn = addedOn;
    }

    public Wishlist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
