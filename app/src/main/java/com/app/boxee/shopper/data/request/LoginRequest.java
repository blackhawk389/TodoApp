package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("type")
    @Expose
    private String type;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("source")
    @Expose
    private String source;

    public LoginRequest(String phone, String type,String deviceId,String source) {
        this.phone = phone;
        this.type = type;
        this.deviceId = deviceId;
        this.source = source;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
