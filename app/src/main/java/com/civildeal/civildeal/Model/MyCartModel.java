package com.civildeal.civildeal.Model;

public class MyCartModel {
    private String name;
    private String price;
    private String discountPrice;
    private String dicount;
    private int image;

    public MyCartModel(String name, String price, String discountPrice, String dicount, int image) {
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.dicount = dicount;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDicount() {
        return dicount;
    }

    public void setDicount(String dicount) {
        this.dicount = dicount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
