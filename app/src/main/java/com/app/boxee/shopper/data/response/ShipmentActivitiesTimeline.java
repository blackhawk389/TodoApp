package com.app.boxee.shopper.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipmentActivitiesTimeline implements Parcelable{
    @SerializedName("tracking_id")
    @Expose
    private Integer trackingId;
    @SerializedName("consignment_no")
    @Expose
    private long consignmentNo;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("external_detail")
    @Expose
    private String externalDetail;

    public String getExternalDetailAr() {
        return externalDetailAr;
    }

    public void setExternalDetailAr(String externalDetailAr) {
        this.externalDetailAr = externalDetailAr;
    }

    @SerializedName("external_detail_ar")
    @Expose
    private String externalDetailAr;

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

    protected ShipmentActivitiesTimeline(Parcel in) {
        if (in.readByte() == 0) {
            trackingId = null;
        } else {
            trackingId = in.readInt();
        }
        consignmentNo = in.readLong();
        orderId = in.readString();
        department = in.readString();
        detail = in.readString();
        externalDetail = in.readString();
        code = in.readString();
        countryName = in.readString();
        cityName = in.readString();
        location = in.readString();
        fromStatus = in.readString();
        toStatus = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        isTrackable = in.readString();
        if (in.readByte() == 0) {
            fkWarehouse = null;
        } else {
            fkWarehouse = in.readInt();
        }
        if (in.readByte() == 0) {
            fkWebshop = null;
        } else {
            fkWebshop = in.readInt();
        }
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        updatedBy = in.readString();
        deletedBy = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
        externalDetailAr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (trackingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trackingId);
        }
        dest.writeLong(consignmentNo);
        dest.writeString(orderId);
        dest.writeString(department);
        dest.writeString(detail);
        dest.writeString(externalDetail);
        dest.writeString(code);
        dest.writeString(countryName);
        dest.writeString(cityName);
        dest.writeString(location);
        dest.writeString(fromStatus);
        dest.writeString(toStatus);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(isTrackable);
        if (fkWarehouse == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fkWarehouse);
        }
        if (fkWebshop == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fkWebshop);
        }
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        dest.writeString(updatedBy);
        dest.writeString(deletedBy);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(deletedAt);
        dest.writeString(externalDetailAr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShipmentActivitiesTimeline> CREATOR = new Creator<ShipmentActivitiesTimeline>() {
        @Override
        public ShipmentActivitiesTimeline createFromParcel(Parcel in) {
            return new ShipmentActivitiesTimeline(in);
        }

        @Override
        public ShipmentActivitiesTimeline[] newArray(int size) {
            return new ShipmentActivitiesTimeline[size];
        }
    };

    public Integer getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    public long getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(long consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Object getUpdatedBy() {
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
