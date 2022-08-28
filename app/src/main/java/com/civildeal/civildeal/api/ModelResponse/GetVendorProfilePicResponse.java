package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GetVendorProfilePicResponse {

    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("userimage")
    @Expose
    private String userimage;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetVendorProfilePicResponse() {
    }

    /**
     *
     * @param imagePath
     * @param userimage
     */
    public GetVendorProfilePicResponse(String imagePath, String userimage) {
        super();
        this.imagePath = imagePath;
        this.userimage = userimage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public GetVendorProfilePicResponse withImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public GetVendorProfilePicResponse withUserimage(String userimage) {
        this.userimage = userimage;
        return this;
    }

}
