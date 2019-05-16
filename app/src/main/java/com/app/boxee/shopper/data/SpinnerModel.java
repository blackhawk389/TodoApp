package com.app.boxee.shopper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpinnerModel {
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("has_operation")
    @Expose
    private String hasOperation;
    @SerializedName("id_parent")
    @Expose
    private Integer idParent;

    public SpinnerModel(Integer locationId, String code, String name, String type, String hasOperation, Integer idParent) {
        this.locationId = locationId;
        this.code = code;
        this.name = name;
        this.type = type;
        this.hasOperation = hasOperation;
        this.idParent = idParent;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(String hasOperation) {
        this.hasOperation = hasOperation;
    }

    public Integer getIdParent() {
        return idParent;
    }
}
