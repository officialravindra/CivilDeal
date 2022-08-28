package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProductLeadResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("promoter_id")
    @Expose
    private Integer promoterId;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
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
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("leadtype")
    @Expose
    private String leadtype;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("purchase_count")
    @Expose
    private Integer purchaseCount;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("productImage")
    @Expose
    private String productImage;
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
    public ProductLeadResponse() {
    }

    /**
     *
     * @param locationName
     * @param leadtype
     * @param productId
     * @param orderId
     * @param purchaseCount
     * @param query
     * @param vendorId
     * @param vendorName
     * @param promoterId
     * @param isPurchased
     * @param productName
     * @param createdAt
     * @param productImage
     * @param phone
     * @param locationId
     * @param name
     * @param isAdded
     * @param location
     * @param id
     * @param leadprice
     * @param serviceId
     * @param email
     * @param status
     * @param updatedAt
     */
    public ProductLeadResponse(Integer id, Integer promoterId, Integer orderId, Integer vendorId, Integer serviceId, String name, String phone, String email, String query, String location, Integer locationId, Integer leadprice, Integer status, String createdAt, String updatedAt, String vendorName, String leadtype, Integer productId, Integer purchaseCount, String productName, String productImage, String locationName, Integer isAdded, Integer isPurchased) {
        super();
        this.id = id;
        this.promoterId = promoterId;
        this.orderId = orderId;
        this.vendorId = vendorId;
        this.serviceId = serviceId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.query = query;
        this.location = location;
        this.locationId = locationId;
        this.leadprice = leadprice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.vendorName = vendorName;
        this.leadtype = leadtype;
        this.productId = productId;
        this.purchaseCount = purchaseCount;
        this.productName = productName;
        this.productImage = productImage;
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

    public ProductLeadResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public ProductLeadResponse withPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public ProductLeadResponse withOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public ProductLeadResponse withVendorId(Integer vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public ProductLeadResponse withServiceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductLeadResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProductLeadResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProductLeadResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ProductLeadResponse withQuery(String query) {
        this.query = query;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProductLeadResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public ProductLeadResponse withLocationId(Integer locationId) {
        this.locationId = locationId;
        return this;
    }

    public Integer getLeadprice() {
        return leadprice;
    }

    public void setLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
    }

    public ProductLeadResponse withLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ProductLeadResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ProductLeadResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProductLeadResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public ProductLeadResponse withVendorName(String vendorName) {
        this.vendorName = vendorName;
        return this;
    }

    public String getLeadtype() {
        return leadtype;
    }

    public void setLeadtype(String leadtype) {
        this.leadtype = leadtype;
    }

    public ProductLeadResponse withLeadtype(String leadtype) {
        this.leadtype = leadtype;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public ProductLeadResponse withProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public ProductLeadResponse withPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductLeadResponse withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public ProductLeadResponse withProductImage(String productImage) {
        this.productImage = productImage;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ProductLeadResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public Integer getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(Integer isAdded) {
        this.isAdded = isAdded;
    }

    public ProductLeadResponse withIsAdded(Integer isAdded) {
        this.isAdded = isAdded;
        return this;
    }

    public Integer getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Integer isPurchased) {
        this.isPurchased = isPurchased;
    }

    public ProductLeadResponse withIsPurchased(Integer isPurchased) {
        this.isPurchased = isPurchased;
        return this;
    }

}
