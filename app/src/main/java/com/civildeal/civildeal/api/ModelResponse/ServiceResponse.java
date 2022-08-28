package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ServiceResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("inner_image")
    @Expose
    private String innerImage;
    @SerializedName("meta_title")
    @Expose
    private String metaTitle;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;
    @SerializedName("meta_keywords")
    @Expose
    private String metaKeywords;
    @SerializedName("service_slug")
    @Expose
    private String serviceSlug;
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("serviceorder")
    @Expose
    private Integer serviceorder;
    @SerializedName("servicetype")
    @Expose
    private String servicetype;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    /**
     * No args constructor for use in serialization
     */
    public ServiceResponse() {
    }

    /**
     * @param description
     * @param metaDescription
     * @param userId
     * @param parentId
     * @param serviceSlug
     * @param serviceorder
     * @param bannerImage
     * @param createdAt
     * @param servicetype
     * @param metaKeywords
     * @param metaTitle
     * @param imageUrl
     * @param name
     * @param innerImage
     * @param location
     * @param id
     * @param status
     * @param updatedAt
     */
    public ServiceResponse(Integer id, Object parentId, String name, String description, String bannerImage, String innerImage, String metaTitle, String metaDescription, String metaKeywords, String serviceSlug, Object userId, Integer status, String location, Integer serviceorder, String servicetype, String createdAt, String updatedAt, String imageUrl) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.bannerImage = bannerImage;
        this.innerImage = innerImage;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.serviceSlug = serviceSlug;
        this.userId = userId;
        this.status = status;
        this.location = location;
        this.serviceorder = serviceorder;
        this.servicetype = servicetype;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public ServiceResponse withParentId(Object parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceResponse withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public ServiceResponse withBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
        return this;
    }

    public String getInnerImage() {
        return innerImage;
    }

    public void setInnerImage(String innerImage) {
        this.innerImage = innerImage;
    }

    public ServiceResponse withInnerImage(String innerImage) {
        this.innerImage = innerImage;
        return this;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public ServiceResponse withMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
        return this;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public ServiceResponse withMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
        return this;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public ServiceResponse withMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
        return this;
    }

    public String getServiceSlug() {
        return serviceSlug;
    }

    public void setServiceSlug(String serviceSlug) {
        this.serviceSlug = serviceSlug;
    }

    public ServiceResponse withServiceSlug(String serviceSlug) {
        this.serviceSlug = serviceSlug;
        return this;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public ServiceResponse withUserId(Object userId) {
        this.userId = userId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ServiceResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ServiceResponse withLocation(String location) {
        this.location = location;
        return this;
    }

    public Integer getServiceorder() {
        return serviceorder;
    }

    public void setServiceorder(Integer serviceorder) {
        this.serviceorder = serviceorder;
    }

    public ServiceResponse withServiceorder(Integer serviceorder) {
        this.serviceorder = serviceorder;
        return this;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public ServiceResponse withServicetype(String servicetype) {
        this.servicetype = servicetype;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ServiceResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ServiceResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ServiceResponse withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
