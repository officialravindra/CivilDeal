package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CheckVendorListResponse {

    @SerializedName("userimage")
    @Expose
    private String userimage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("estimate_time")
    @Expose
    private String estimateTime;
    @SerializedName("bid_amount")
    @Expose
    private String bidAmount;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("bid")
    @Expose
    private Integer bid;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public CheckVendorListResponse() {
    }

    /**
     *
     * @param createdAt
     * @param estimateTime
     * @param userimage
     * @param name
     * @param mobile
     * @param vendorId
     * @param bidAmount
     * @param description
     * @param id
     * @param postId
     * @param bid
     * @param userId
     */
    public CheckVendorListResponse(String userimage, String name, String mobile, Integer id, Integer userId, Integer vendorId, Integer postId, String estimateTime, String bidAmount, Object description, Integer bid, String createdAt) {
        super();
        this.userimage = userimage;
        this.name = name;
        this.mobile = mobile;
        this.id = id;
        this.userId = userId;
        this.vendorId = vendorId;
        this.postId = postId;
        this.estimateTime = estimateTime;
        this.bidAmount = bidAmount;
        this.description = description;
        this.bid = bid;
        this.createdAt = createdAt;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public CheckVendorListResponse withUserimage(String userimage) {
        this.userimage = userimage;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckVendorListResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public CheckVendorListResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CheckVendorListResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public CheckVendorListResponse withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public CheckVendorListResponse withVendorId(Integer vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public CheckVendorListResponse withPostId(Integer postId) {
        this.postId = postId;
        return this;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public CheckVendorListResponse withEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
        return this;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public CheckVendorListResponse withBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
        return this;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public CheckVendorListResponse withDescription(Object description) {
        this.description = description;
        return this;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public CheckVendorListResponse withBid(Integer bid) {
        this.bid = bid;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CheckVendorListResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

}

