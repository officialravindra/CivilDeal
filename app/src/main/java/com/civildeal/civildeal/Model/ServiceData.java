package com.civildeal.civildeal.Model;

public class ServiceData {

    private int serviceImage;
    private String serviceName;

    public ServiceData(int serviceImage, String serviceName) {
        this.serviceImage = serviceImage;
        this.serviceName = serviceName;
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
}
