package com.civildeal.civildeal.Model;

public class MaintenanceData {

    private int maintenanceImage;
    private String maintenanceName;

    public MaintenanceData(int maintenanceImage, String maintenanceName) {
        this.maintenanceImage = maintenanceImage;
        this.maintenanceName = maintenanceName;
    }

    public int getMaintenanceImage() {
        return maintenanceImage;
    }

    public void setMaintenanceImage(int maintenanceImage) {
        this.maintenanceImage = maintenanceImage;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }
}
