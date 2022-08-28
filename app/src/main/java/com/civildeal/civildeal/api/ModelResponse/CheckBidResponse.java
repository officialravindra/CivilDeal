package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CheckBidResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("select_size")
    @Expose
    private String selectSize;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("min_bujet")
    @Expose
    private String minBujet;
    @SerializedName("max_bujet")
    @Expose
    private String maxBujet;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("other_location")
    @Expose
    private Object otherLocation;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("is_profile_visited")
    @Expose
    private String isProfileVisited;
    @SerializedName("expired_date")
    @Expose
    private Object expiredDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("locationName")
    @Expose
    private Object locationName;
    @SerializedName("image_path")
    @Expose
    private String imagePath;

    /**
     * No args constructor for use in serialization
     *
     */
    public CheckBidResponse() {
    }

    /**
     *
     * @param images
     * @param locationName
     * @param postType
     * @param imagePath
     * @param expiredDate
     * @param minBujet
     * @param description
     * @param title
     * @param userId
     * @param otherLocation
     * @param createdAt
     * @param size
     * @param selectSize
     * @param name
     * @param maxBujet
     * @param isProfileVisited
     * @param location
     * @param id
     * @param email
     * @param status
     * @param updatedAt
     */
    public CheckBidResponse(Integer id, Integer userId, String name, String email, String title, String description, String selectSize, String size, String minBujet, String maxBujet, String location, Object otherLocation, String images, String postType, String isProfileVisited, Object expiredDate, String status, String createdAt, String updatedAt, Object locationName, String imagePath) {
        super();
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.title = title;
        this.description = description;
        this.selectSize = selectSize;
        this.size = size;
        this.minBujet = minBujet;
        this.maxBujet = maxBujet;
        this.location = location;
        this.otherLocation = otherLocation;
        this.images = images;
        this.postType = postType;
        this.isProfileVisited = isProfileVisited;
        this.expiredDate = expiredDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.locationName = locationName;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CheckBidResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public CheckBidResponse withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckBidResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CheckBidResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CheckBidResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheckBidResponse withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSelectSize() {
        return selectSize;
    }

    public void setSelectSize(String selectSize) {
        this.selectSize = selectSize;
    }

    public CheckBidResponse withSelectSize(String selectSize) {
        this.selectSize = selectSize;
        return this;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public CheckBidResponse withSize(String size) {
        this.size = size;
        return this;
    }

    public String getMinBujet() {
        return minBujet;
    }

    public void setMinBujet(String minBujet) {
        this.minBujet = minBujet;
    }

    public CheckBidResponse withMinBujet(String minBujet) {
        this.minBujet = minBujet;
        return this;
    }

    public String getMaxBujet() {
        return maxBujet;
    }

    public void setMaxBujet(String maxBujet) {
        this.maxBujet = maxBujet;
    }

    public CheckBidResponse withMaxBujet(String maxBujet) {
        this.maxBujet = maxBujet;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CheckBidResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public Object getOtherLocation() {
        return otherLocation;
    }

    public void setOtherLocation(Object otherLocation) {
        this.otherLocation = otherLocation;
    }

    public CheckBidResponse withOtherLocation(Object otherLocation) {
        this.otherLocation = otherLocation;
        return this;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public CheckBidResponse withImages(String images) {
        this.images = images;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public CheckBidResponse withPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getIsProfileVisited() {
        return isProfileVisited;
    }

    public void setIsProfileVisited(String isProfileVisited) {
        this.isProfileVisited = isProfileVisited;
    }

    public CheckBidResponse withIsProfileVisited(String isProfileVisited) {
        this.isProfileVisited = isProfileVisited;
        return this;
    }

    public Object getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Object expiredDate) {
        this.expiredDate = expiredDate;
    }

    public CheckBidResponse withExpiredDate(Object expiredDate) {
        this.expiredDate = expiredDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CheckBidResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CheckBidResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CheckBidResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Object getLocationName() {
        return locationName;
    }

    public void setLocationName(Object locationName) {
        this.locationName = locationName;
    }

    public CheckBidResponse withLocationName(Object locationName) {
        this.locationName = locationName;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public CheckBidResponse withImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

}
