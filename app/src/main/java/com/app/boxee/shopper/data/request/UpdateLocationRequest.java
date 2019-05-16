package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLocationRequest {
    @SerializedName("consignment_id")
    @Expose
    private long consignmentId;
    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("action")
    @Expose
    private String action;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @SerializedName("address_id")
    @Expose
    private String addressId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public UpdateLocationRequest(long consignmentId, String mode, String action,String addressId) {
        this.consignmentId = consignmentId;
        this.mode = mode;
        this.action = action;
        this.addressId = addressId;

    }

    public long getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(long consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
