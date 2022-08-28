package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MaintenanceLeadByPromoterResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("promoter_id")
    @Expose
    private Integer promoterId;
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
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("leadprice")
    @Expose
    private Integer leadprice;
    @SerializedName("leadtype")
    @Expose
    private String leadtype;
    @SerializedName("service_request_name")
    @Expose
    private String serviceRequestName;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("purchase_count")
    @Expose
    private Integer purchaseCount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public MaintenanceLeadByPromoterResponse() {
    }

    /**
     *
     * @param leadtype
     * @param purchaseCount
     * @param query
     * @param vendorId
     * @param type
     * @param vendorName
     * @param promoterId
     * @param createdAt
     * @param phone
     * @param locationId
     * @param serviceRequestName
     * @param name
     * @param location
     * @param id
     * @param leadprice
     * @param serviceId
     * @param email
     * @param status
     * @param updatedAt
     */
    public MaintenanceLeadByPromoterResponse(Integer id, Integer promoterId, String name, String email, String phone, String query, String location, Integer locationId, Integer leadprice, String leadtype, String serviceRequestName, Integer serviceId, String type, Integer status, Integer vendorId, String vendorName, Integer purchaseCount, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.promoterId = promoterId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.query = query;
        this.location = location;
        this.locationId = locationId;
        this.leadprice = leadprice;
        this.leadtype = leadtype;
        this.serviceRequestName = serviceRequestName;
        this.serviceId = serviceId;
        this.type = type;
        this.status = status;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.purchaseCount = purchaseCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MaintenanceLeadByPromoterResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public MaintenanceLeadByPromoterResponse withPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaintenanceLeadByPromoterResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MaintenanceLeadByPromoterResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MaintenanceLeadByPromoterResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public MaintenanceLeadByPromoterResponse withQuery(String query) {
        this.query = query;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MaintenanceLeadByPromoterResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public MaintenanceLeadByPromoterResponse withLocationId(Integer locationId) {
        this.locationId = locationId;
        return this;
    }

    public Integer getLeadprice() {
        return leadprice;
    }

    public void setLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
    }

    public MaintenanceLeadByPromoterResponse withLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
        return this;
    }

    public String getLeadtype() {
        return leadtype;
    }

    public void setLeadtype(String leadtype) {
        this.leadtype = leadtype;
    }

    public MaintenanceLeadByPromoterResponse withLeadtype(String leadtype) {
        this.leadtype = leadtype;
        return this;
    }

    public String getServiceRequestName() {
        return serviceRequestName;
    }

    public void setServiceRequestName(String serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
    }

    public MaintenanceLeadByPromoterResponse withServiceRequestName(String serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public MaintenanceLeadByPromoterResponse withServiceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MaintenanceLeadByPromoterResponse withType(String type) {
        this.type = type;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MaintenanceLeadByPromoterResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public MaintenanceLeadByPromoterResponse withVendorId(Integer vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public MaintenanceLeadByPromoterResponse withVendorName(String vendorName) {
        this.vendorName = vendorName;
        return this;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public MaintenanceLeadByPromoterResponse withPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public MaintenanceLeadByPromoterResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MaintenanceLeadByPromoterResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
