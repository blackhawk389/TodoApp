package com.app.boxee.shopper.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackResponseData {
    @SerializedName("tracking_id")
    @Expose
    private long trackingId;
    @SerializedName("consignment_no")
    @Expose
    private long consignmentNo;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("external_detail")
    @Expose
    private String externalDetail;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("from_status")
    @Expose
    private String fromStatus;
    @SerializedName("to_status")
    @Expose
    private String toStatus;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_trackable")
    @Expose
    private String isTrackable;
    @SerializedName("fk_warehouse")
    @Expose
    private Integer fkWarehouse;
    @SerializedName("fk_webshop")
    @Expose
    private Integer fkWebshop;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("deleted_by")
    @Expose
    private String deletedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;

    public long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(long trackingId) {
        this.trackingId = trackingId;
    }

    public long getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(long consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExternalDetail() {
        return externalDetail;
    }

    public void setExternalDetail(String externalDetail) {
        this.externalDetail = externalDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIsTrackable() {
        return isTrackable;
    }

    public void setIsTrackable(String isTrackable) {
        this.isTrackable = isTrackable;
    }

    public Integer getFkWarehouse() {
        return fkWarehouse;
    }

    public void setFkWarehouse(Integer fkWarehouse) {
        this.fkWarehouse = fkWarehouse;
    }

    public Integer getFkWebshop() {
        return fkWebshop;
    }

    public void setFkWebshop(Integer fkWebshop) {
        this.fkWebshop = fkWebshop;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

}
