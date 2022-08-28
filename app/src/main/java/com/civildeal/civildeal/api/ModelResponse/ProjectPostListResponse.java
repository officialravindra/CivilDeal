package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProjectPostListResponse {

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
    private String otherLocation;
    @SerializedName("images")
    @Expose
    private Object images;
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
    private String locationName;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("is_bid")
    @Expose
    private Integer isBid;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProjectPostListResponse() {
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
     * @param isBid
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
    public ProjectPostListResponse(Integer id, Integer userId, String name, String email, String title, String description, String selectSize, String size, String minBujet, String maxBujet, String location, String otherLocation, Object images, String postType, String isProfileVisited, Object expiredDate, String status, String createdAt, String updatedAt, String locationName, String imagePath, Integer isBid) {
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
        this.isBid = isBid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProjectPostListResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ProjectPostListResponse withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectPostListResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProjectPostListResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProjectPostListResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectPostListResponse withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSelectSize() {
        return selectSize;
    }

    public void setSelectSize(String selectSize) {
        this.selectSize = selectSize;
    }

    public ProjectPostListResponse withSelectSize(String selectSize) {
        this.selectSize = selectSize;
        return this;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ProjectPostListResponse withSize(String size) {
        this.size = size;
        return this;
    }

    public String getMinBujet() {
        return minBujet;
    }

    public void setMinBujet(String minBujet) {
        this.minBujet = minBujet;
    }

    public ProjectPostListResponse withMinBujet(String minBujet) {
        this.minBujet = minBujet;
        return this;
    }

    public String getMaxBujet() {
        return maxBujet;
    }

    public void setMaxBujet(String maxBujet) {
        this.maxBujet = maxBujet;
    }

    public ProjectPostListResponse withMaxBujet(String maxBujet) {
        this.maxBujet = maxBujet;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProjectPostListResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public String getOtherLocation() {
        return otherLocation;
    }

    public void setOtherLocation(String otherLocation) {
        this.otherLocation = otherLocation;
    }

    public ProjectPostListResponse withOtherLocation(String otherLocation) {
        this.otherLocation = otherLocation;
        return this;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public ProjectPostListResponse withImages(Object images) {
        this.images = images;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public ProjectPostListResponse withPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getIsProfileVisited() {
        return isProfileVisited;
    }

    public void setIsProfileVisited(String isProfileVisited) {
        this.isProfileVisited = isProfileVisited;
    }

    public ProjectPostListResponse withIsProfileVisited(String isProfileVisited) {
        this.isProfileVisited = isProfileVisited;
        return this;
    }

    public Object getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Object expiredDate) {
        this.expiredDate = expiredDate;
    }

    public ProjectPostListResponse withExpiredDate(Object expiredDate) {
        this.expiredDate = expiredDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectPostListResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ProjectPostListResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProjectPostListResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ProjectPostListResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ProjectPostListResponse withImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Integer getIsBid() {
        return isBid;
    }

    public void setIsBid(Integer isBid) {
        this.isBid = isBid;
    }

    public ProjectPostListResponse withIsBid(Integer isBid) {
        this.isBid = isBid;
        return this;
    }

}
