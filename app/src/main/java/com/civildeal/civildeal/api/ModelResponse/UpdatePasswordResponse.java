package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class UpdatePasswordResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Object> result = null;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdatePasswordResponse() {
    }

    /**
     *
     * @param result
     * @param code
     * @param message
     * @param status
     */
    public UpdatePasswordResponse(Integer code, Boolean status, List<Object> result, String message) {
        super();
        this.code = code;
        this.status = status;
        this.result = result;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UpdatePasswordResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UpdatePasswordResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public UpdatePasswordResponse withResult(List<Object> result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UpdatePasswordResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
