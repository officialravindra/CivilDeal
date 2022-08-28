package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BidderBannerImagesResultResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private BidderBannerImagesResponse data;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public BidderBannerImagesResultResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param status
     */
    public BidderBannerImagesResultResponse(Integer code, Boolean status, BidderBannerImagesResponse data, String message) {
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

    public BidderBannerImagesResultResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BidderBannerImagesResultResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public BidderBannerImagesResponse getData() {
        return data;
    }

    public void setData(BidderBannerImagesResponse data) {
        this.data = data;
    }

    public BidderBannerImagesResultResponse withData(BidderBannerImagesResponse data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BidderBannerImagesResultResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
