package com.civildeal.civildeal.api.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class WalletResponse {

    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;

    /**
     * No args constructor for use in serialization
     *
     */
    public WalletResponse() {
    }

    /**
     *
     * @param walletAmount
     */
    public WalletResponse(String walletAmount) {
        super();
        this.walletAmount = walletAmount;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

}
