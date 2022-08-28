package com.civildeal.civildeal.api.ModelResponse;


import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProjectPostBannerImagesResultResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ProjectPostBannerImagesResponse data;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProjectPostBannerImagesResultResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param status
     */
    public ProjectPostBannerImagesResultResponse(Integer code, Boolean status, ProjectPostBannerImagesResponse data, String message) {
        super();
        this.code = code;
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProjectPostBannerImagesResultResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ProjectPostBannerImagesResultResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public ProjectPostBannerImagesResponse getData() {
        return data;
    }

    public void setData(ProjectPostBannerImagesResponse data) {
        this.data = data;
    }

    public ProjectPostBannerImagesResultResponse withData(ProjectPostBannerImagesResponse data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProjectPostBannerImagesResultResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
