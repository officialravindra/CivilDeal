package com.civildeal.civildeal.api.ModelResponse;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProjectPostListResultResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<ProjectPostListResponse> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProjectPostListResultResponse() {
    }

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param status
     */
    public ProjectPostListResultResponse(Integer code, Boolean status, List<ProjectPostListResponse> data, String message) {
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

    public ProjectPostListResultResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ProjectPostListResultResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<ProjectPostListResponse> getData() {
        return data;
    }

    public void setData(List<ProjectPostListResponse> data) {
        this.data = data;
    }

    public ProjectPostListResultResponse withData(List<ProjectPostListResponse> data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProjectPostListResultResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
