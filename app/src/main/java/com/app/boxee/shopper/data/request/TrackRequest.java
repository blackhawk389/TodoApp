package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackRequest {
    @SerializedName("cn")
    @Expose
    protected String cn;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("country_name")
    @Expose
    private String country_name;
    @SerializedName("city_name")
    @Expose
    private String city_name;

    public TrackRequest(String cn, String type) {
        this.cn = cn;
        this.type = type;
    }

    //    public String getWarehouseId() {
//        return warehouseId;
//    }
//
//    public void setWarehouseId(String warehouseId) {
//        this.warehouseId = warehouseId;
//    }
//
//    @SerializedName("warehouse_id")
//    @Expose
//    private String warehouseId;
    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
