package com.civildeal.civildeal.api.ModelResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PromoterProfileResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("switch_user_id")
    @Expose
    private Integer switchUserId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("location")
    @Expose
    private Integer location;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;
    @SerializedName("pan_card")
    @Expose
    private String panCard;
    @SerializedName("aadhar_card")
    @Expose
    private String aadharCard;
    @SerializedName("bank_account")
    @Expose
    private String bankAccount;
    @SerializedName("bank_ifsc_code")
    @Expose
    private String bankIfscCode;
    @SerializedName("bank_holder_name")
    @Expose
    private String bankHolderName;
    @SerializedName("bank_account_number")
    @Expose
    private String bankAccountNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("location_name")
    @Expose
    private String locationName;

    /**
     * No args constructor for use in serialization
     *
     */
    public PromoterProfileResponse() {
    }

    /**
     *
     * @param vendorType
     * @param bankAccount
     * @param panCard
     * @param locationName
     * @param gender
     * @param bankIfscCode
     * @param mobile
     * @param aadharCard
     * @param updateAt
     * @param bankHolderName
     * @param userId
     * @param switchUserId
     * @param createdAt
     * @param dob
     * @param name
     * @param location
     * @param bankAccountNumber
     * @param id
     * @param email
     * @param age
     * @param status
     */
    public PromoterProfileResponse(Integer id, Integer userId, Integer switchUserId, String name, String email, String mobile, String gender, Integer age, String dob, Integer location, String vendorType, String panCard, String aadharCard, String bankAccount, String bankIfscCode, String bankHolderName, String bankAccountNumber, String status, String createdAt, String updateAt, String locationName) {
        super();
        this.id = id;
        this.userId = userId;
        this.switchUserId = switchUserId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.age = age;
        this.dob = dob;
        this.location = location;
        this.vendorType = vendorType;
        this.panCard = panCard;
        this.aadharCard = aadharCard;
        this.bankAccount = bankAccount;
        this.bankIfscCode = bankIfscCode;
        this.bankHolderName = bankHolderName;
        this.bankAccountNumber = bankAccountNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.locationName = locationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PromoterProfileResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PromoterProfileResponse withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getSwitchUserId() {
        return switchUserId;
    }

    public void setSwitchUserId(Integer switchUserId) {
        this.switchUserId = switchUserId;
    }

    public PromoterProfileResponse withSwitchUserId(Integer switchUserId) {
        this.switchUserId = switchUserId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PromoterProfileResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PromoterProfileResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public PromoterProfileResponse withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PromoterProfileResponse withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PromoterProfileResponse withAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public PromoterProfileResponse withDob(String dob) {
        this.dob = dob;
        return this;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public PromoterProfileResponse withLocation(Integer location) {
        this.location = location;
        return this;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public PromoterProfileResponse withVendorType(String vendorType) {
        this.vendorType = vendorType;
        return this;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public PromoterProfileResponse withPanCard(String panCard) {
        this.panCard = panCard;
        return this;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public PromoterProfileResponse withAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
        return this;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public PromoterProfileResponse withBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public String getBankIfscCode() {
        return bankIfscCode;
    }

    public void setBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
    }

    public PromoterProfileResponse withBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
        return this;
    }

    public String getBankHolderName() {
        return bankHolderName;
    }

    public void setBankHolderName(String bankHolderName) {
        this.bankHolderName = bankHolderName;
    }

    public PromoterProfileResponse withBankHolderName(String bankHolderName) {
        this.bankHolderName = bankHolderName;
        return this;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public PromoterProfileResponse withBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PromoterProfileResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PromoterProfileResponse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public PromoterProfileResponse withUpdateAt(String updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public PromoterProfileResponse withLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

}
