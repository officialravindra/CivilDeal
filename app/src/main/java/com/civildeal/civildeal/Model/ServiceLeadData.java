package com.civildeal.civildeal.Model;

public class ServiceLeadData {

    private int serviceImage;
    private String serviceName;
    private String serviceDesc;
    private String serviceLocation;
    private String price;

    public ServiceLeadData(int serviceImage, String serviceName, String serviceDesc, String serviceLocation, String price) {
        this.serviceImage = serviceImage;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.serviceLocation = serviceLocation;
        this.price = price;
    }

    public int getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(int serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
