package com.example.carstore;

public class Model {

    private int id;
    private byte[]proavatar;
    private String brand,model,year,price;

    public Model(int id, byte[] proavatar, String brand, String model, String year, String price) {
        this.id = id;
        this.proavatar = proavatar;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProavatar() {
        return proavatar;
    }

    public void setProavatar(byte[] proavatar) {
        this.proavatar = proavatar;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
