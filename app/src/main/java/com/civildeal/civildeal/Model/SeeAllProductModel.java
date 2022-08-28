package com.civildeal.civildeal.Model;

public class SeeAllProductModel {

    private int productImage;
    private String productName;

    public SeeAllProductModel(int productImage, String productName) {
        this.productImage = productImage;
        this.productName = productName;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
