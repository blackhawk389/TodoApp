package com.app.boxee.shopper.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsByStatusList {
    @SerializedName("consignments")
    @Expose
    private List<OrderDetailsByStatusRespose> consignments = null;

    public List<OrderDetailsByStatusRespose> getConsignments() {
        return consignments;
    }

    public void setConsignments(List<OrderDetailsByStatusRespose> consignments) {
        this.consignments = consignments;
    }
}
