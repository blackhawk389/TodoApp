package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackingRequest {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("cn")
    @Expose
    private List<String> cn = null;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public TrackingRequest(String type, List<String> cn, String deviceId) {
        this.type = type;
        this.cn = cn;
        this.deviceId = deviceId;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCn() {
        return cn;
    }

    public void setCn(List<String> cn) {
        this.cn = cn;
    }
}
