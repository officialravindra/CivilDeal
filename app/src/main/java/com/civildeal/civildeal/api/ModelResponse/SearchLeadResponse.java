package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SearchLeadResponse {

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
    private Object email;
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
    private Object serviceRequestName;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("form_name")
    @Expose
    private Object formName;
    @SerializedName("type")
    @Expose
    private Object type;
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
    @SerializedName("servicename")
    @Expose
    private String servicename;
    @SerializedName("serviceImage")
    @Expose
    private String serviceImage;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("is_added")
    @Expose
    private Integer isAdded;
    @SerializedName("is_purchased")
    @Expose
    private Integer isPurchased;

    /**
     * No args constructor for use in serialization
     *
     */
    public SearchLeadResponse() {
    }

    /**
     *
     * @param leadtype
     * @param purchaseCount
     * @param vendorId
     * @param type
     * @param isPurchased
     * @param createdAt
     * @param locationId
     * @param serviceRequestName
     * @param formName
     * @param isAdded
     * @param servicename
     * @param serviceImage
     * @param id
     * @param leadprice
     * @param serviceId
     * @param email
     * @param updatedAt
     * @param locationName
     * @param query
     * @param vendorName
     * @param promoterId
     * @param phone
     * @param name
     * @param location
     * @param status
     */
    public SearchLeadResponse(Integer id, Integer promoterId, String name, Object email, String phone, String query, String location, Integer locationId, Integer leadprice, String leadtype, Object serviceRequestName, Integer serviceId, Object formName, Object type, Integer status, Integer vendorId, String vendorName, Integer purchaseCount, String createdAt, String updatedAt, String servicename, String serviceImage, String locationName, Integer isAdded, Integer isPurchased) {
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
        this.formName = formName;
        this.type = type;
        this.status = status;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.purchaseCount = purchaseCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.servicename = servicename;
        this.serviceImage = serviceImage;
        this.locationName = locationName;
        this.isAdded = isAdded;
        this.isPurchased = isPurchased;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SearchLeadResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public SearchLeadResponse withPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchLeadResponse withName(String name) {
        this.name = name;
        return this;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public SearchLeadResponse withEmail(Object email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SearchLeadResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchLeadResponse withQuery(String query) {
        this.query = query;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SearchLeadResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public SearchLeadResponse withLocationId(Integer locationId) {
        this.locationId = locationId;
        return this;
    }

    public Integer getLeadprice() {
        return leadprice;
    }

    public void setLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
    }

    public SearchLeadResponse withLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
        return this;
    }

    public String getLeadtype() {
        return leadtype;
    }

    public void setLeadtype(String leadtype) {
        this.leadtype = leadtype;
    }

    public SearchLeadResponse withLeadtype(String leadtype) {
        this.leadtype = leadtype;
        return this;
    }

    public Object getServiceRequestName() {
        return serviceRequestName;
    }

    public void setServiceRequestName(Object serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
    }

    public SearchLeadResponse withServiceRequestName(Object serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public SearchLeadResponse withServiceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Object getFormName() {
        return formName;
    }

    public void setFormName(Object formName) {
        this.formName = formName;
    }

    public SearchLeadResponse withFormName(Object formName) {
        this.formName = formName;
        return this;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public SearchLeadResponse withType(Object type) {
        this.type = type;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public SearchLeadResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public SearchLeadResponse withVendorId(Integer vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public SearchLeadResponse withVendorName(String vendorName) {
        this.vendorName = vendorName;
        return this;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public SearchLeadResponse withPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public SearchLeadResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SearchLeadResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public SearchLeadResponse withServicename(String servicename) {
        this.servicename = servicename;
        return this;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public SearchLeadResponse withServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public SearchLeadResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public Integer getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(Integer isAdded) {
        this.isAdded = isAdded;
    }

    public SearchLeadResponse withIsAdded(Integer isAdded) {
        this.isAdded = isAdded;
        return this;
    }

    public Integer getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Integer isPurchased) {
        this.isPurchased = isPurchased;
    }

    public SearchLeadResponse withIsPurchased(Integer isPurchased) {
        this.isPurchased = isPurchased;
        return this;
    }

}
