package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class RegisterResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_prouduct_name")
    @Expose
    private String serviceProuductName;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterResponse() {
    }

    /**
     *
     * @param vendorType
     * @param createdAt
     * @param serviceProuductName
     * @param locationId
     * @param name
     * @param mobile
     * @param id
     * @param serviceId
     * @param type
     * @param updatedAt
     */
    public RegisterResponse(String name, String mobile, String locationId, String vendorType, String serviceId, String serviceProuductName, Integer type, String updatedAt, String createdAt, Integer id) {
        super();
        this.name = name;
        this.mobile = mobile;
        this.locationId = locationId;
        this.vendorType = vendorType;
        this.serviceId = serviceId;
        this.serviceProuductName = serviceProuductName;
        this.type = type;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegisterResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public RegisterResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public RegisterResponse withLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public RegisterResponse withVendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public RegisterResponse withServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getServiceProuductName() {
        return serviceProuductName;
    }

    public void setServiceProuductName(String serviceProuductName) {
        this.serviceProuductName = serviceProuductName;
    }

    public RegisterResponse withServiceProuductName(String serviceProuductName) {
        this.serviceProuductName = serviceProuductName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public RegisterResponse withType(Integer type) {
        this.type = type;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public RegisterResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public RegisterResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RegisterResponse withId(Integer id) {
        this.id = id;
        return this;
    }

}
