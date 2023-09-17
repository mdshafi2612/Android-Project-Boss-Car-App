package com.example.testcarappwithfragment.Model;

import java.math.BigInteger;

public class contact_us {
    private int id;
    private String name;
    private String email;
    private int mobile;
    private String comment;
    private String addedOn;

    public contact_us(int id, String name, String email, int mobile, String comment, String addedOn) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.comment = comment;
        this.addedOn = addedOn;
    }

    public contact_us() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
