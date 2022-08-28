package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GetvendorProfilePicResultResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private GetVendorProfilePicResponse data;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetvendorProfilePicResultResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param status
     */
    public GetvendorProfilePicResultResponse(Integer code, Boolean status, GetVendorProfilePicResponse data, String message) {
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

    public GetvendorProfilePicResultResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public GetvendorProfilePicResultResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public GetVendorProfilePicResponse getData() {
        return data;
    }

    public void setData(GetVendorProfilePicResponse data) {
        this.data = data;
    }

    public GetvendorProfilePicResultResponse withData(GetVendorProfilePicResponse data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetvendorProfilePicResultResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
