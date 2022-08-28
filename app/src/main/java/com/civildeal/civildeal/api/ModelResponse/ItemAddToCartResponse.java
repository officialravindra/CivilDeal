package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ItemAddToCartResponse {

    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("servicename")
    @Expose
    private String servicename;
    @SerializedName("serviceImage")
    @Expose
    private String serviceImage;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("leadprice")
    @Expose
    private Integer leadprice;

    /**
     * No args constructor for use in serialization
     *
     */
    public ItemAddToCartResponse() {
    }

    /**
     *
     * @param cartId
     * @param query
     * @param servicename
     * @param serviceImage
     * @param leadprice
     */
    public ItemAddToCartResponse(Integer cartId, String servicename, String serviceImage, String query, Integer leadprice) {
        super();
        this.cartId = cartId;
        this.servicename = servicename;
        this.serviceImage = serviceImage;
        this.query = query;
        this.leadprice = leadprice;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getLeadprice() {
        return leadprice;
    }

    public void setLeadprice(Integer leadprice) {
        this.leadprice = leadprice;
    }

}
