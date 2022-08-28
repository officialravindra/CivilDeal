package com.civildeal.civildeal.Model;

public class ProductLeadModel {

    private int productImage;
    private String productName;
    private String productDesc;
    private String productLocation;
    private String productprice;

    public ProductLeadModel(int productImage, String productName, String productDesc, String productLocation, String productprice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productLocation = productLocation;
        this.productprice = productprice;
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

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
