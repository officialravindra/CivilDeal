package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class OtpResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("otp")
    @Expose
    private Integer otp;
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
    public OtpResponse() {
    }

    /**
     *
     * @param result
     * @param code
     * @param mobile
     * @param otp
     * @param message
     * @param status
     */
    public OtpResponse(Integer code, Boolean status, String mobile, Integer otp, List<Object> result, String message) {
        super();
        this.code = code;
        this.status = status;
        this.mobile = mobile;
        this.otp = otp;
        this.result = result;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public OtpResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public OtpResponse withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public OtpResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public OtpResponse withOtp(Integer otp) {
        this.otp = otp;
        return this;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public OtpResponse withResult(List<Object> result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OtpResponse withMessage(String message) {
        this.message = message;
        return this;
    }

}
