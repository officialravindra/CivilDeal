package com.civildeal.civildeal.api.ModelResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SearchResultResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<SearchResponse> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public SearchResultResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param status
     */
    public SearchResultResponse(Integer code, Boolean status, List<SearchResponse> data, String message) {
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

    public SearchResultResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SearchResultResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<SearchResponse> getData() {
        return data;
    }

    public void setData(List<SearchResponse> data) {
        this.data = data;
    }

    public SearchResultResponse withData(List<SearchResponse> data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SearchResultResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}