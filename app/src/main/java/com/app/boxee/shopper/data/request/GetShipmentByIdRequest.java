package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShipmentByIdRequest {
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public GetShipmentByIdRequest(String phone,String deviceId) {
        this.phone = phone;
        this.deviceId = deviceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
