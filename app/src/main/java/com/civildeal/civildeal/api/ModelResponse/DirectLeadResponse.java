package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DirectLeadResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("servicename")
    @Expose
    private String servicename;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;

    /**
     * No args constructor for use in serialization
     *
     */
    public DirectLeadResponse() {
    }

    /**
     *
     * @param vendorType
     * @param image
     * @param createdAt
     * @param locationName
     * @param phone
     * @param query
     * @param name
     * @param servicename
     * @param email
     */
    public DirectLeadResponse(String name, String email, String phone, String query, String servicename, String image, String locationName, String createdAt, String vendorType) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.query = query;
        this.servicename = servicename;
        this.image = image;
        this.locationName = locationName;
        this.createdAt = createdAt;
        this.vendorType = vendorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DirectLeadResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DirectLeadResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DirectLeadResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public DirectLeadResponse withQuery(String query) {
        this.query = query;
        return this;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public DirectLeadResponse withServicename(String servicename) {
        this.servicename = servicename;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DirectLeadResponse withImage(String image) {
        this.image = image;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public DirectLeadResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DirectLeadResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public DirectLeadResponse withVendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

}
