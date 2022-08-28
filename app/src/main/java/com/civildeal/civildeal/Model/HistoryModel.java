package com.civildeal.civildeal.Model;

public class HistoryModel {
    private int image;
    private String name;
    private String Desc;

    public HistoryModel(int image, String name, String desc) {
        this.image = image;
        this.name = name;
        Desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
