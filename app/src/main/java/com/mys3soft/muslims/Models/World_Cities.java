package com.mys3soft.muslims.Models;

public class World_Cities {
    private int id;
    private String city;
    private String country;
    private String subcountry;

    public World_Cities(int id, String city, String country, String subcountry){
        this.id = id;
        this.city = city;
        this.country = country;
        this.subcountry = subcountry;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getSubcountry() {
        return subcountry;
    }
}
