package com.civildeal.civildeal.Model;

public class TestimonialData {

    private int image;
    private String quote;
    private String name;

    public TestimonialData(int image, String quote, String name) {
        this.image = image;
        this.quote = quote;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
