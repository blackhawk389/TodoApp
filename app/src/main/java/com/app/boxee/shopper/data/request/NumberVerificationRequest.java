package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NumberVerificationRequest {
    @SerializedName("phone")
    @Expose
    protected String phone;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("security_pin")
    @Expose
    private String securityPin;

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

    public NumberVerificationRequest(String phone, String type, String securityPin,String deviceId,String source) {
        this.phone = phone;
        this.type = type;
        this.securityPin = securityPin;
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

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }
}
