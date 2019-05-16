package com.app.boxee.shopper.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackingResponse {
    @SerializedName("consignments")
    @Expose
    private List<Consignment> consignments = null;

    public List<Consignment> getConsignments() {
        return consignments;
    }

    public void setConsignments(List<Consignment> consignments) {
        this.consignments = consignments;
    }
}
