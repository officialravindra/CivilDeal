package com.civildeal.civildeal.api.ModelResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SwitchPromoterResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Object> data = null;
    @SerializedName("promoter_id")
    @Expose
    private String promoterId;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public SwitchPromoterResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param promoterId
     * @param status
     */
    public SwitchPromoterResponse(Integer code, Boolean status, List<Object> data, String promoterId, String message) {
        super();
        this.code = code;
        this.status = status;
        this.data = data;
        this.promoterId = promoterId;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SwitchPromoterResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SwitchPromoterResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public SwitchPromoterResponse withData(List<Object> data) {
        this.data = data;
        return this;
    }

    public String getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(String promoterId) {
        this.promoterId = promoterId;
    }

    public SwitchPromoterResponse withPromoterId(String promoterId) {
        this.promoterId = promoterId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SwitchPromoterResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
