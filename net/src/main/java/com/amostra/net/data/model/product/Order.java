package com.amostra.net.data.model.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private String id;
    private String title;
    private String description;
    private List<Product> productList;
    private Date control;

    public Order(String id, String title, String description, List<Product> productList, Date control) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.productList = productList;
        this.control = control;
    }

    public Order() {
        productList = new ArrayList<Product>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Date getControl() {
        return control;
    }

    public void setControl(Date control) {
        this.control = control;
    }

    public void addProduct(Product product){
        productList.add(product);
    }
}
