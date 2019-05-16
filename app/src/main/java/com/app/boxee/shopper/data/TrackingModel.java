package com.app.boxee.shopper.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackingModel implements Parcelable {


    private long consignment_id;

    private String type;

    private String delivery_date;

    private Double amount;

    private String status;

    private String webshop;

    private Boolean found;
    private String addressTo;

    protected TrackingModel(Parcel in) {
        consignment_id = in.readLong();
        type = in.readString();
        delivery_date = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        status = in.readString();
        webshop = in.readString();
        byte tmpFound = in.readByte();
        found = tmpFound == 0 ? null : tmpFound == 1;
        addressTo = in.readString();
        addressFrom = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(consignment_id);
        dest.writeString(type);
        dest.writeString(delivery_date);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(status);
        dest.writeString(webshop);
        dest.writeByte((byte) (found == null ? 0 : found ? 1 : 2));
        dest.writeString(addressTo);
        dest.writeString(addressFrom);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrackingModel> CREATOR = new Creator<TrackingModel>() {
        @Override
        public TrackingModel createFromParcel(Parcel in) {
            return new TrackingModel(in);
        }

        @Override
        public TrackingModel[] newArray(int size) {
            return new TrackingModel[size];
        }
    };

    public long getConsignment_id() {
        return consignment_id;
    }

    public void setConsignment_id(long consignment_id) {
        this.consignment_id = consignment_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWebshop() {
        return webshop;
    }

    public void setWebshop(String webshop) {
        this.webshop = webshop;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    private String addressFrom;



}


