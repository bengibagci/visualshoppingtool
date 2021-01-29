package com.bengibagci.visualshoppingtool;

import android.graphics.Bitmap;

public class ProductModel {
    private Bitmap image;
    private String market;
    private String title;
    private String price;

    public ProductModel(Bitmap image, String market, String title, String price) {
        this.image = image;
        this.market = market;
        this.title = title;
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImageBitmap(Bitmap image) {
        this.image = image;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return image + market + title + price;
    }
}
