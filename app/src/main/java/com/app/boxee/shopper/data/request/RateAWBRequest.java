package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateAWBRequest {
    @SerializedName("consignment_id")
    @Expose
    private String consignmentId;
    @SerializedName("shopper_rating")
    @Expose
    private String shopperRating;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public RateAWBRequest(String consignmentId, String shopperRating,String deviceId) {
        this.consignmentId = consignmentId;
        this.shopperRating = shopperRating;
        this.deviceId = deviceId;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getDeliveryDate() {
        return shopperRating;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.shopperRating = deliveryDate;
    }
}
