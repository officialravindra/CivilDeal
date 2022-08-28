package com.civildeal.civildeal.api.ModelResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProjectPostBannerImagesResponse {

    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("images")
    @Expose
    private List<String> images;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProjectPostBannerImagesResponse() {
    }

    /**
     *
     * @param images
     * @param imagePath
     */
    public ProjectPostBannerImagesResponse(String imagePath, List<String> images) {
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

    public ProjectPostBannerImagesResponse withImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ProjectPostBannerImagesResponse withImages(List<String> images) {
        this.images = images;
        return this;
    }

}
