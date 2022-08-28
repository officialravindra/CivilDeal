package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class LoginResponse {

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
    private Object serviceId;
    @SerializedName("product_id")
    @Expose
    private Object productId;
    @SerializedName("service_prouduct_name")
    @Expose
    private Object serviceProuductName;
    @SerializedName("userimage")
    @Expose
    private String userimage;
    @SerializedName("work_image1")
    @Expose
    private Object workImage1;
    @SerializedName("work_image2")
    @Expose
    private Object workImage2;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("short_description")
    @Expose
    private Object shortDescription;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("pdf_profile")
    @Expose
    private Object pdfProfile;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("leadlimit")
    @Expose
    private Object leadlimit;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("pan")
    @Expose
    private Object pan;
    @SerializedName("gst")
    @Expose
    private Object gst;
    @SerializedName("experience")
    @Expose
    private Object experience;
    @SerializedName("quality")
    @Expose
    private Object quality;
    @SerializedName("team")
    @Expose
    private Object team;
    @SerializedName("project_detail")
    @Expose
    private Object projectDetail;
    @SerializedName("company_name")
    @Expose
    private Object companyName;
    @SerializedName("service_amount")
    @Expose
    private Object serviceAmount;
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
    public LoginResponse() {
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
    public LoginResponse(Integer id, String vendorCode, String name, String lastname, String email, String mobile, String password, String type, String vendorType, Object serviceId, Object productId, Object serviceProuductName, String userimage, Object workImage1, Object workImage2, Integer locationId, Object shortDescription, Object description, Object pdfProfile, Object status, Integer otp, Object leadlimit, Object address, Object pan, Object gst, Object experience, Object quality, Object team, Object projectDetail, Object companyName, Object serviceAmount, Object rememberToken, String google2faSecret, String createdAt, String updatedAt, String locationName) {
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

    public LoginResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public LoginResponse withVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LoginResponse withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LoginResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginResponse withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoginResponse withType(String type) {
        this.type = type;
        return this;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public LoginResponse withVendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public Object getServiceId() {
        return serviceId;
    }

    public void setServiceId(Object serviceId) {
        this.serviceId = serviceId;
    }

    public LoginResponse withServiceId(Object serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public LoginResponse withProductId(Object productId) {
        this.productId = productId;
        return this;
    }

    public Object getServiceProuductName() {
        return serviceProuductName;
    }

    public void setServiceProuductName(Object serviceProuductName) {
        this.serviceProuductName = serviceProuductName;
    }

    public LoginResponse withServiceProuductName(Object serviceProuductName) {
        this.serviceProuductName = serviceProuductName;
        return this;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public LoginResponse withUserimage(String userimage) {
        this.userimage = userimage;
        return this;
    }

    public Object getWorkImage1() {
        return workImage1;
    }

    public void setWorkImage1(Object workImage1) {
        this.workImage1 = workImage1;
    }

    public LoginResponse withWorkImage1(Object workImage1) {
        this.workImage1 = workImage1;
        return this;
    }

    public Object getWorkImage2() {
        return workImage2;
    }

    public void setWorkImage2(Object workImage2) {
        this.workImage2 = workImage2;
    }

    public LoginResponse withWorkImage2(Object workImage2) {
        this.workImage2 = workImage2;
        return this;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public LoginResponse withLocationId(Integer locationId) {
        this.locationId = locationId;
        return this;
    }

    public Object getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Object shortDescription) {
        this.shortDescription = shortDescription;
    }

    public LoginResponse withShortDescription(Object shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public LoginResponse withDescription(Object description) {
        this.description = description;
        return this;
    }

    public Object getPdfProfile() {
        return pdfProfile;
    }

    public void setPdfProfile(Object pdfProfile) {
        this.pdfProfile = pdfProfile;
    }

    public LoginResponse withPdfProfile(Object pdfProfile) {
        this.pdfProfile = pdfProfile;
        return this;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public LoginResponse withStatus(Object status) {
        this.status = status;
        return this;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public LoginResponse withOtp(Integer otp) {
        this.otp = otp;
        return this;
    }

    public Object getLeadlimit() {
        return leadlimit;
    }

    public void setLeadlimit(Object leadlimit) {
        this.leadlimit = leadlimit;
    }

    public LoginResponse withLeadlimit(Object leadlimit) {
        this.leadlimit = leadlimit;
        return this;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public LoginResponse withAddress(Object address) {
        this.address = address;
        return this;
    }

    public Object getPan() {
        return pan;
    }

    public void setPan(Object pan) {
        this.pan = pan;
    }

    public LoginResponse withPan(Object pan) {
        this.pan = pan;
        return this;
    }

    public Object getGst() {
        return gst;
    }

    public void setGst(Object gst) {
        this.gst = gst;
    }

    public LoginResponse withGst(Object gst) {
        this.gst = gst;
        return this;
    }

    public Object getExperience() {
        return experience;
    }

    public void setExperience(Object experience) {
        this.experience = experience;
    }

    public LoginResponse withExperience(Object experience) {
        this.experience = experience;
        return this;
    }

    public Object getQuality() {
        return quality;
    }

    public void setQuality(Object quality) {
        this.quality = quality;
    }

    public LoginResponse withQuality(Object quality) {
        this.quality = quality;
        return this;
    }

    public Object getTeam() {
        return team;
    }

    public void setTeam(Object team) {
        this.team = team;
    }

    public LoginResponse withTeam(Object team) {
        this.team = team;
        return this;
    }

    public Object getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(Object projectDetail) {
        this.projectDetail = projectDetail;
    }

    public LoginResponse withProjectDetail(Object projectDetail) {
        this.projectDetail = projectDetail;
        return this;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public LoginResponse withCompanyName(Object companyName) {
        this.companyName = companyName;
        return this;
    }

    public Object getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(Object serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public LoginResponse withServiceAmount(Object serviceAmount) {
        this.serviceAmount = serviceAmount;
        return this;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public LoginResponse withRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
        return this;
    }

    public String getGoogle2faSecret() {
        return google2faSecret;
    }

    public void setGoogle2faSecret(String google2faSecret) {
        this.google2faSecret = google2faSecret;
    }

    public LoginResponse withGoogle2faSecret(String google2faSecret) {
        this.google2faSecret = google2faSecret;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public LoginResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LoginResponse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LoginResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

}
