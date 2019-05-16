package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadPDFRequest {
    @SerializedName("consignment_no")
    @Expose
    private String consignmentId;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public DownloadPDFRequest(String consignmentId, String deviceId) {
        this.consignmentId = consignmentId;
        this.deviceId = deviceId;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }
}
