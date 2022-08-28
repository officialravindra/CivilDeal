package com.civildeal.civildeal.api.ModelResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PromoterMyLeadResultResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<PromoterMyLeadResponse> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public PromoterMyLeadResultResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param status
     */
    public PromoterMyLeadResultResponse(Integer code, Boolean status, List<PromoterMyLeadResponse> data, String message) {
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

    public PromoterMyLeadResultResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PromoterMyLeadResultResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<PromoterMyLeadResponse> getData() {
        return data;
    }

    public void setData(List<PromoterMyLeadResponse> data) {
        this.data = data;
    }

    public PromoterMyLeadResultResponse withData(List<PromoterMyLeadResponse> data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PromoterMyLeadResultResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}