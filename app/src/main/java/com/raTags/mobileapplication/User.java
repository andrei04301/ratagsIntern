package com.raTags.mobileapplication;

import java.io.Serializable;

public class User implements Serializable {
    String Name, Address, Id, Spot;

    public User(){}

    public User(String name, String address, String id, String spot) {
        Name = name;
        Address = address;
        Id = id;
        Spot = spot;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSpot() {
        return Spot;
    }

    public void setSpot(String spot) {
        Spot = spot;
    }
}
