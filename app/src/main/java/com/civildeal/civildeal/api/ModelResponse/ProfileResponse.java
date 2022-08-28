package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ProfileResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vendor_code")
    @Expose
    private String vendorCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("service_prouduct_name")
    @Expose
    private String serviceProuductName;
    @SerializedName("userimage")
    @Expose
    private String userimage;
    @SerializedName("work_image1")
    @Expose
    private String workImage1;
    @SerializedName("work_image2")
    @Expose
    private String workImage2;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pdf_profile")
    @Expose
    private String pdfProfile;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("leadlimit")
    @Expose
    private Object leadlimit;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("project_detail")
    @Expose
    private String projectDetail;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("service_amount")
    @Expose
    private String serviceAmount;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("google2fa_secret")
    @Expose
    private String google2faSecret;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("locationName")
    @Expose
    private String locationName;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProfileResponse() {
    }

    /**
     *
     * @param vendorType
     * @param google2faSecret
     * @param companyName
     * @param description
     * @param gst
     * @param type
     * @param pdfProfile
     * @param experience
     * @param vendorCode
     * @param createdAt
     * @param password
     * @param serviceAmount
     * @param locationId
     * @param id
     * @param serviceId
     * @param pan
     * @param email
     * @param updatedAt
     * @param address
     * @param locationName
     * @param productId
     * @param serviceProuductName
     * @param leadlimit
     * @param workImage2
     * @param mobile
     * @param workImage1
     * @param otp
     * @param shortDescription
     * @param team
     * @param lastname
     * @param quality
     * @param userimage
     * @param name
     * @param projectDetail
     * @param rememberToken
     * @param status
     */
    public ProfileResponse(Integer id, String vendorCode, String name, String lastname, String email, String mobile, String password, String type, String vendorType, Integer serviceId, String productId, String serviceProuductName, String userimage, String workImage1, String workImage2, Integer locationId, String shortDescription, String description, String pdfProfile, String status, Integer otp, Object leadlimit, String address, String pan, String gst, String experience, String quality, String team, String projectDetail, String companyName, String serviceAmount, Object rememberToken, String google2faSecret, String createdAt, String updatedAt, String locationName) {
        super();
        this.id = id;
        this.vendorCode = vendorCode;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.type = type;
        this.vendorType = vendorType;
        this.serviceId = serviceId;
        this.productId = productId;
        this.serviceProuductName = serviceProuductName;
        this.userimage = userimage;
        this.workImage1 = workImage1;
        this.workImage2 = workImage2;
        this.locationId = locationId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.pdfProfile = pdfProfile;
        this.status = status;
        this.otp = otp;
        this.leadlimit = leadlimit;
        this.address = address;
        this.pan = pan;
        this.gst = gst;
        this.experience = experience;
        this.quality = quality;
        this.team = team;
        this.projectDetail = projectDetail;
        this.companyName = companyName;
        this.serviceAmount = serviceAmount;
        this.rememberToken = rememberToken;
        this.google2faSecret = google2faSecret;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.locationName = locationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfileResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public ProfileResponse withVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ProfileResponse withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ProfileResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfileResponse withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProfileResponse withType(String type) {
        this.type = type;
        return this;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public ProfileResponse withVendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public ProfileResponse withServiceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProfileResponse withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getServiceProuductName() {
        return serviceProuductName;
    }

    public void setServiceProuductName(String serviceProuductName) {
        this.serviceProuductName = serviceProuductName;
    }

    public ProfileResponse withServiceProuductName(String serviceProuductName) {
        this.serviceProuductName = serviceProuductName;
        return this;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public ProfileResponse withUserimage(String userimage) {
        this.userimage = userimage;
        return this;
    }

    public String getWorkImage1() {
        return workImage1;
    }

    public void setWorkImage1(String workImage1) {
        this.workImage1 = workImage1;
    }

    public ProfileResponse withWorkImage1(String workImage1) {
        this.workImage1 = workImage1;
        return this;
    }

    public String getWorkImage2() {
        return workImage2;
    }

    public void setWorkImage2(String workImage2) {
        this.workImage2 = workImage2;
    }

    public ProfileResponse withWorkImage2(String workImage2) {
        this.workImage2 = workImage2;
        return this;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public ProfileResponse withLocationId(Integer locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ProfileResponse withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProfileResponse withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPdfProfile() {
        return pdfProfile;
    }

    public void setPdfProfile(String pdfProfile) {
        this.pdfProfile = pdfProfile;
    }

    public ProfileResponse withPdfProfile(String pdfProfile) {
        this.pdfProfile = pdfProfile;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProfileResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public ProfileResponse withOtp(Integer otp) {
        this.otp = otp;
        return this;
    }

    public Object getLeadlimit() {
        return leadlimit;
    }

    public void setLeadlimit(Object leadlimit) {
        this.leadlimit = leadlimit;
    }

    public ProfileResponse withLeadlimit(Object leadlimit) {
        this.leadlimit = leadlimit;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ProfileResponse withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public ProfileResponse withPan(String pan) {
        this.pan = pan;
        return this;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public ProfileResponse withGst(String gst) {
        this.gst = gst;
        return this;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ProfileResponse withExperience(String experience) {
        this.experience = experience;
        return this;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public ProfileResponse withQuality(String quality) {
        this.quality = quality;
        return this;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public ProfileResponse withTeam(String team) {
        this.team = team;
        return this;
    }

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }

    public ProfileResponse withProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ProfileResponse withCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public ProfileResponse withServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
        return this;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public ProfileResponse withRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
        return this;
    }

    public String getGoogle2faSecret() {
        return google2faSecret;
    }

    public void setGoogle2faSecret(String google2faSecret) {
        this.google2faSecret = google2faSecret;
    }

    public ProfileResponse withGoogle2faSecret(String google2faSecret) {
        this.google2faSecret = google2faSecret;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ProfileResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProfileResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ProfileResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

}
