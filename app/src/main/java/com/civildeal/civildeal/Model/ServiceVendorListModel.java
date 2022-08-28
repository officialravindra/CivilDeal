package com.civildeal.civildeal.Model;

public class ServiceVendorListModel {

    private String vendorName;
    private String vendorDesc;
    private String vendorExperience;
    private String vendorLocation;
    private int vendorimage;

    public ServiceVendorListModel(String vendorName, String vendorDesc, String vendorExperience, String vendorLocation, int vendorimage) {
        this.vendorName = vendorName;
        this.vendorDesc = vendorDesc;
        this.vendorExperience = vendorExperience;
        this.vendorLocation = vendorLocation;
        this.vendorimage = vendorimage;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorDesc() {
        return vendorDesc;
    }

    public void setVendorDesc(String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }

    public String getVendorExperience() {
        return vendorExperience;
    }

    public void setVendorExperience(String vendorExperience) {
        this.vendorExperience = vendorExperience;
    }

    public String getVendorLocation() {
        return vendorLocation;
    }

    public void setVendorLocation(String vendorLocation) {
        this.vendorLocation = vendorLocation;
    }

    public int getVendorimage() {
        return vendorimage;
    }

    public void setVendorimage(int vendorimage) {
        this.vendorimage = vendorimage;
    }
}
