package com.amostra.comanda.ui.model;

public class MenuDrawerItem {

    private int id;
    private int imageIconResource;
    private String title;
    private int colorImage;
    private Class fragment ;


    public MenuDrawerItem(int id, String title, int imageIconResource, int colorImage, Class fragment) {
        this.id = id;
        this.imageIconResource = imageIconResource;
        this.title = title;
        this.colorImage = colorImage;
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageIconResource() {
        return imageIconResource;
    }

    public void setImageIconResource(int imageIconResource) {
        this.imageIconResource = imageIconResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getColorImage() {
        return colorImage;
    }

    public void setColorImage(int colorImage) {
        this.colorImage = colorImage;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
