package com.app.boxee.shopper.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsByStatusRespose implements Parcelable{
    @SerializedName("consignment_id")
    @Expose
    private long consignmentId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("consignor")
    @Expose
    private String consignor;
    @SerializedName("consignor_address_one")
    @Expose
    private String consignorAddressOne;
    @SerializedName("consignor_address_two")
    @Expose
    private String consignorAddressTwo;
    @SerializedName("consignor_address_house_appartment")
    @Expose
    private String consignorAddressHouseAppartment;
    @SerializedName("consignor_address_landmark")
    @Expose
    private String consignorAddressLandmark;
    @SerializedName("consignor_phone")
    @Expose
    private String consignorPhone;
    @SerializedName("consignor_alternate_phone")
    @Expose
    private String consignorAlternatePhone;
    @SerializedName("consignor_email")
    @Expose
    private String consignorEmail;
    @SerializedName("consignee")
    @Expose
    private String consignee;
    @SerializedName("consignee_address_one")
    @Expose
    private String consigneeAddressOne;
    @SerializedName("consignee_address_two")
    @Expose
    private String consigneeAddressTwo;
    @SerializedName("consignee_address_house_appartment")
    @Expose
    private String consigneeAddressHouseAppartment;
    @SerializedName("consignee_address_landmark")
    @Expose
    private String consigneeAddressLandmark;
    @SerializedName("consignee_phone")
    @Expose
    private String consigneePhone;
    @SerializedName("consignee_alternate_phone")
    @Expose
    private String consigneeAlternatePhone;
    @SerializedName("consignee_email")
    @Expose
    private String consigneeEmail;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("return_pickup_date")
    @Expose
    private String returnPickupDate;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("billing_type")
    @Expose
    private String billingType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("webshop_name")
    @Expose
    private String webshopName;

    @SerializedName("is_paid")
    @Expose
    private Integer isPaid;

    @SerializedName("pod_at")
    @Expose
    private String podAt;

    @SerializedName("pop_at")
    @Expose
    private String popAt;


    public String getPodAt() {
        return podAt;
    }

    public void setPodAt(String podAt) {
        this.podAt = podAt;
    }

    public String getPopAt() {
        return popAt;
    }

    public void setPopAt(String popAt) {
        this.popAt = popAt;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public static Creator<OrderDetailsByStatusRespose> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("activity")
    @Expose
    private String activity;

    public String getActivityAr() {
        return activityAr;
    }

    public void setActivityAr(String activityAr) {
        this.activityAr = activityAr;
    }

    @SerializedName("activity_ar")
    @Expose
    private String activityAr;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    protected OrderDetailsByStatusRespose(Parcel in) {
        consignmentId = in.readLong();
        type = in.readString();
        orderId = in.readString();
        consignor = in.readString();
        consignorAddressOne = in.readString();
        consignorAddressTwo = in.readString();
        consignorAddressHouseAppartment = in.readString();
        consignorAddressLandmark = in.readString();
        consignorPhone = in.readString();
        consignorAlternatePhone = in.readString();
        consignorEmail = in.readString();
        consignee = in.readString();
        consigneeAddressOne = in.readString();
        consigneeAddressTwo = in.readString();
        consigneeAddressHouseAppartment = in.readString();
        consigneeAddressLandmark = in.readString();
        consigneePhone = in.readString();
        consigneeAlternatePhone = in.readString();
        consigneeEmail = in.readString();
        deliveryDate = in.readString();
        returnPickupDate = in.readString();
        deliveryTime = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        billingType = in.readString();
        status = in.readString();
        webshopName = in.readString();
        isPaid = in.readInt();
        popAt = in.readString();
        podAt = in.readString();
        activityAr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(consignmentId);
        dest.writeString(type);
        dest.writeString(orderId);
        dest.writeString(consignor);
        dest.writeString(consignorAddressOne);
        dest.writeString(consignorAddressTwo);
        dest.writeString(consignorAddressHouseAppartment);
        dest.writeString(consignorAddressLandmark);
        dest.writeString(consignorPhone);
        dest.writeString(consignorAlternatePhone);
        dest.writeString(consignorEmail);
        dest.writeString(consignee);
        dest.writeString(consigneeAddressOne);
        dest.writeString(consigneeAddressTwo);
        dest.writeString(consigneeAddressHouseAppartment);
        dest.writeString(consigneeAddressLandmark);
        dest.writeString(consigneePhone);
        dest.writeString(consigneeAlternatePhone);
        dest.writeString(consigneeEmail);
        dest.writeString(deliveryDate);
        dest.writeString(returnPickupDate);
        dest.writeString(deliveryTime);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(billingType);
        dest.writeString(status);
        dest.writeString(webshopName);
        dest.writeInt(isPaid);
        dest.writeString(popAt);
        dest.writeString(podAt);
        dest.writeString(activityAr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDetailsByStatusRespose> CREATOR = new Creator<OrderDetailsByStatusRespose>() {
        @Override
        public OrderDetailsByStatusRespose createFromParcel(Parcel in) {
            return new OrderDetailsByStatusRespose(in);
        }

        @Override
        public OrderDetailsByStatusRespose[] newArray(int size) {
            return new OrderDetailsByStatusRespose[size];
        }
    };

    public long getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(long consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getConsignorAddressOne() {
        return consignorAddressOne;
    }

    public void setConsignorAddressOne(String consignorAddressOne) {
        this.consignorAddressOne = consignorAddressOne;
    }

    public String getConsignorAddressTwo() {
        return consignorAddressTwo;
    }

    public void setConsignorAddressTwo(String consignorAddressTwo) {
        this.consignorAddressTwo = consignorAddressTwo;
    }

    public String getConsignorAddressHouseAppartment() {
        return consignorAddressHouseAppartment;
    }

    public void setConsignorAddressHouseAppartment(String consignorAddressHouseAppartment) {
        this.consignorAddressHouseAppartment = consignorAddressHouseAppartment;
    }

    public String getConsignorAddressLandmark() {
        return consignorAddressLandmark;
    }

    public void setConsignorAddressLandmark(String consignorAddressLandmark) {
        this.consignorAddressLandmark = consignorAddressLandmark;
    }

    public String getConsignorPhone() {
        return consignorPhone;
    }

    public void setConsignorPhone(String consignorPhone) {
        this.consignorPhone = consignorPhone;
    }

    public String getConsignorAlternatePhone() {
        return consignorAlternatePhone;
    }

    public void setConsignorAlternatePhone(String consignorAlternatePhone) {
        this.consignorAlternatePhone = consignorAlternatePhone;
    }

    public String getConsignorEmail() {
        return consignorEmail;
    }

    public void setConsignorEmail(String consignorEmail) {
        this.consignorEmail = consignorEmail;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeAddressOne() {
        return consigneeAddressOne;
    }

    public void setConsigneeAddressOne(String consigneeAddressOne) {
        this.consigneeAddressOne = consigneeAddressOne;
    }

    public String getConsigneeAddressTwo() {
        return consigneeAddressTwo;
    }

    public void setConsigneeAddressTwo(String consigneeAddressTwo) {
        this.consigneeAddressTwo = consigneeAddressTwo;
    }

    public String getConsigneeAddressHouseAppartment() {
        return consigneeAddressHouseAppartment;
    }

    public void setConsigneeAddressHouseAppartment(String consigneeAddressHouseAppartment) {
        this.consigneeAddressHouseAppartment = consigneeAddressHouseAppartment;
    }

    public String getConsigneeAddressLandmark() {
        return consigneeAddressLandmark;
    }

    public void setConsigneeAddressLandmark(String consigneeAddressLandmark) {
        this.consigneeAddressLandmark = consigneeAddressLandmark;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAlternatePhone() {
        return consigneeAlternatePhone;
    }

    public void setConsigneeAlternatePhone(String consigneeAlternatePhone) {
        this.consigneeAlternatePhone = consigneeAlternatePhone;
    }

    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getReturnPickupDate() {
        return returnPickupDate;
    }

    public void setReturnPickupDate(String returnPickupDate) {
        this.returnPickupDate = returnPickupDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWebshopName() {
        return webshopName;
    }

    public void setWebshopName(String webshopName) {
        this.webshopName = webshopName;
    }

}
