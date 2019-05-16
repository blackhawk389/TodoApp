package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePickupDateRequest {
    @SerializedName("consignment_id")
    @Expose
    private String consignmentId;
    @SerializedName("pickup_date")
    @Expose
    private String deliveryDate;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @SerializedName("action")
    @Expose
    private String action;

    public UpdatePickupDateRequest(String consignmentId, String deliveryDate, String action, String deviceId) {
        this.consignmentId = consignmentId;
        this.deliveryDate = deliveryDate;
        this.action = action;
        this.deviceId = deviceId;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
