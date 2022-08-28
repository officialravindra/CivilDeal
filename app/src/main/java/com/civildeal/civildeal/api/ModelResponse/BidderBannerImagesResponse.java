package com.civildeal.civildeal.api.ModelResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BidderBannerImagesResponse {

    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("images")
    @Expose
    private List<String> images = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public BidderBannerImagesResponse() {
    }

    /**
     *
     * @param images
     * @param imagePath
     */
    public BidderBannerImagesResponse(String imagePath, List<String> images) {
        super();
        this.imagePath = imagePath;
        this.images = images;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BidderBannerImagesResponse withImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public BidderBannerImagesResponse withImages(List<String> images) {
        this.images = images;
        return this;
    }

}