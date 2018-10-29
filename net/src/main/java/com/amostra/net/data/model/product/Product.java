package com.amostra.net.data.model.product;

import java.util.Date;

public class Product {
    private String id;
    private String image;
    private String title;
    private String description;
    private int categoryCod;
    private double price;
    private Date control;

    public Product(String id, String image, String title, String description, int categoryCod, double price, Date control) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.categoryCod = categoryCod;
        this.price = price;
        this.control = control;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryCod() {
        return categoryCod;
    }

    public void setCategoryCod(int categoryCod) {
        this.categoryCod = categoryCod;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getControl() {
        return control;
    }

    public void setControl(Date control) {
        this.control = control;
    }

    public Product() {
        id ="";
        image = "";
        title = "";
        description = "";
        categoryCod = 0;
        price = 0;
        control = new Date();
    }
}
