package com.amostra.net.data.model.product;

import java.util.Date;

public class Historic {
    private int id;
    private String title;
    private String description;
    private int categoryCod;
    private int typeProductCod;
    private Date control;

    public Historic() {
    }

    public Historic(int id, String title, String description, int categoryCod, int typeProductCod, Date control) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryCod = categoryCod;
        this.typeProductCod = typeProductCod;
        this.control = control;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCategoryCod() {
        return categoryCod;
    }

    public void setCategoryCod(int categoryCod) {
        this.categoryCod = categoryCod;
    }

    public int getTypeProductCod() {
        return typeProductCod;
    }

    public void setTypeProductCod(int typeProductCod) {
        this.typeProductCod = typeProductCod;
    }

    public Date getControl() {
        return control;
    }

    public void setControl(Date control) {
        this.control = control;
    }
}
