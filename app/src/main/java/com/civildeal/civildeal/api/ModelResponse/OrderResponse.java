package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class OrderResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("razorpay_id")
    @Expose
    private Object razorpayId;
    @SerializedName("lead_id")
    @Expose
    private Integer leadId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_type_id")
    @Expose
    private Integer userTypeId;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("phone")
    @Expose
    private Long phone;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("service_request_name")
    @Expose
    private String serviceRequestName;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("wallet_amount")
    @Expose
    private Integer walletAmount;
    @SerializedName("order_status")
    @Expose
    private Integer orderStatus;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("lead_date")
    @Expose
    private String leadDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderResponse() {
    }

    /**
     *
     * @param userTypeId
     * @param vendorType
     * @param orderId
     * @param orderStatus
     * @param createdAt
     * @param leadDate
     * @param serviceRequestName
     * @param id
     * @param email
     * @param paymentStatus
     * @param leadId
     * @param updatedAt
     * @param image
     * @param amount
     * @param query
     * @param mobile
     * @param userName
     * @param serviceName
     * @param userId
     * @param walletAmount
     * @param razorpayId
     * @param phone
     * @param name
     * @param location
     * @param status
     */
    public OrderResponse(Integer id, String orderId, Object razorpayId, Integer leadId, Integer userId, Integer userTypeId, String vendorType, String userName, Object email, Long phone, String location, String query, String serviceRequestName, Integer amount, Integer walletAmount, Integer orderStatus, String paymentStatus, Integer status, String createdAt, String updatedAt, String name, String mobile, String image, String serviceName, String leadDate) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.razorpayId = razorpayId;
        this.leadId = leadId;
        this.userId = userId;
        this.userTypeId = userTypeId;
        this.vendorType = vendorType;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.query = query;
        this.serviceRequestName = serviceRequestName;
        this.amount = amount;
        this.walletAmount = walletAmount;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.mobile = mobile;
        this.image = image;
        this.serviceName = serviceName;
        this.leadDate = leadDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderResponse withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Object getRazorpayId() {
        return razorpayId;
    }

    public void setRazorpayId(Object razorpayId) {
        this.razorpayId = razorpayId;
    }

    public OrderResponse withRazorpayId(Object razorpayId) {
        this.razorpayId = razorpayId;
        return this;
    }

    public Integer getLeadId() {
        return leadId;
    }

    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public OrderResponse withLeadId(Integer leadId) {
        this.leadId = leadId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderResponse withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public OrderResponse withUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
        return this;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public OrderResponse withVendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public OrderResponse withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public OrderResponse withEmail(Object email) {
        this.email = email;
        return this;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public OrderResponse withPhone(Long phone) {
        this.phone = phone;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OrderResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public OrderResponse withQuery(String query) {
        this.query = query;
        return this;
    }

    public String getServiceRequestName() {
        return serviceRequestName;
    }

    public void setServiceRequestName(String serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
    }

    public OrderResponse withServiceRequestName(String serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public OrderResponse withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Integer getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Integer walletAmount) {
        this.walletAmount = walletAmount;
    }

    public OrderResponse withWalletAmount(Integer walletAmount) {
        this.walletAmount = walletAmount;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderResponse withOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderResponse withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OrderResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public OrderResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrderResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public OrderResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public OrderResponse withImage(String image) {
        this.image = image;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public OrderResponse withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getLeadDate() {
        return leadDate;
    }

    public void setLeadDate(String leadDate) {
        this.leadDate = leadDate;
    }

    public OrderResponse withLeadDate(String leadDate) {
        this.leadDate = leadDate;
        return this;
    }

}