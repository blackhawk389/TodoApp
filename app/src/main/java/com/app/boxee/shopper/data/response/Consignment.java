package com.app.boxee.shopper.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Consignment implements Parcelable{
    @SerializedName("consignment_id")
    @Expose
    private long consignmentId;
    @SerializedName("shipment_reference_number")
    @Expose
    private String shipmentReferenceNumber;
    @SerializedName("delivery_date")
    @Expose
    private String expectedDeliveryDate;

    @SerializedName("pop_at")
    @Expose
    private String popAt;

    @SerializedName("pod_at")
    @Expose
    private String podAt;
//
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPopAt() {
        return popAt;
    }

    public void setPopAt(String popAt) {
        this.popAt = popAt;
    }

    public String getPodAt() {
        return podAt;
    }

    public void setPodAt(String podAt) {
        this.podAt = podAt;
    }

    protected Consignment(Parcel in) {
        consignmentId = in.readLong();
        shipmentReferenceNumber = in.readString();
        expectedDeliveryDate = in.readString();
        returnPickupDate = in.readString();
        expectedDeliveryTime = in.readString();
        consignmentStatus = in.readString();
        webshopName = in.readString();
        rating = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        paymentType = in.readString();
        consignorAddress = in.readString();
        consigneeAddress = in.readString();
        shipmentActivitiesTimeline = in.createTypedArrayList(ShipmentActivitiesTimeline.CREATOR);
        isPaid = in.readInt();
        notfound = in.readByte() != 0;
        popAt = in.readString();
        podAt = in.readString();
        consignmentActiivityAr = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(consignmentId);
        dest.writeString(shipmentReferenceNumber);
        dest.writeString(expectedDeliveryDate);
        dest.writeString(returnPickupDate);
        dest.writeString(expectedDeliveryTime);
        dest.writeString(consignmentStatus);
        dest.writeString(webshopName);
        dest.writeString(rating);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(paymentType);
        dest.writeString(consignorAddress);
        dest.writeString(consigneeAddress);
        dest.writeTypedList(shipmentActivitiesTimeline);
        dest.writeByte((byte) (notfound ? 1 : 0));
        dest.writeInt(isPaid);
        dest.writeString(podAt);
        dest.writeString(popAt);
        dest.writeString(consignmentActiivityAr);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Consignment> CREATOR = new Creator<Consignment>() {
        @Override
        public Consignment createFromParcel(Parcel in) {
            return new Consignment(in);
        }

        @Override
        public Consignment[] newArray(int size) {
            return new Consignment[size];
        }
    };

    public String getReturnPickupDate() {
        return returnPickupDate;
    }

    public void setReturnPickupDate(String returnPickupDate) {
        this.returnPickupDate = returnPickupDate;
    }

    @SerializedName("return_pickup_date")
    @Expose
    private String returnPickupDate;

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    @SerializedName("delivery_time")
    @Expose
    private String expectedDeliveryTime;
    @SerializedName("consignment_status")
    @Expose
    private String consignmentStatus;
    @SerializedName("activity")
    @Expose
    private String consignmentActiivity;
    @SerializedName("webshop_name")
    @Expose
    private String webshopName;

    public String getConsignmentActiivityAr() {
        return consignmentActiivityAr;
    }

    public void setConsignmentActiivityAr(String consignmentActiivityAr) {
        this.consignmentActiivityAr = consignmentActiivityAr;
    }

    @SerializedName("activity_ar")
    @Expose
    private String consignmentActiivityAr;


    public String getConsignmentActiivity() {
        return consignmentActiivity;
    }

    public void setConsignmentActiivity(String consignmentActiivity) {
        this.consignmentActiivity = consignmentActiivity;
    }

    public boolean isNotfound() {
        return notfound;
    }

    public void setNotfound(boolean notfound) {
        this.notfound = notfound;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @SerializedName("shopper_rating")
    @Expose
    private String rating;
    @SerializedName("amount")
    @Expose
    private Double amount;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @SerializedName("billing_type")
    @Expose
    private String paymentType;
    @SerializedName("consignor_address")
    @Expose
    private String consignorAddress;
    @SerializedName("consignee_address")
    @Expose
    private String consigneeAddress;
    @SerializedName("shipment_activities_timeline")
    @Expose
    private List<ShipmentActivitiesTimeline> shipmentActivitiesTimeline = null;

    @SerializedName("is_paid")
    @Expose
    private Integer isPaid = null;

    public static Creator<Consignment> getCREATOR() {
        return CREATOR;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public boolean isFound() {
        return notfound;
    }

    public void setFound(boolean found) {
        this.notfound = found;
    }

    private boolean notfound;
    public Consignment(long consignmentId,boolean found) {
        this.consignmentId = consignmentId;
        this.notfound =found;
    }

    public long getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(long consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getShipmentReferenceNumber() {
        return shipmentReferenceNumber;
    }

    public void setShipmentReferenceNumber(String shipmentReferenceNumber) {
        this.shipmentReferenceNumber = shipmentReferenceNumber;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getConsignmentStatus() {
        return consignmentStatus;
    }

    public void setConsignmentStatus(String consignmentStatus) {
        this.consignmentStatus = consignmentStatus;
    }

    public String getWebshopName() {
        return webshopName;
    }

    public void setWebshopName(String webshopName) {
        this.webshopName = webshopName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getConsignorAddress() {
        return consignorAddress;
    }

    public void setConsignorAddress(String consignorAddress) {
        this.consignorAddress = consignorAddress;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public List<ShipmentActivitiesTimeline> getShipmentActivitiesTimeline() {
        return shipmentActivitiesTimeline;
    }

    public void setShipmentActivitiesTimeline(List<ShipmentActivitiesTimeline> shipmentActivitiesTimeline) {
        this.shipmentActivitiesTimeline = shipmentActivitiesTimeline;
    }
}
