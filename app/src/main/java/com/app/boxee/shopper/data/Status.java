package com.app.boxee.shopper.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status implements Parcelable{
    public Status(){

    }
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("consignment_status_id")
    @Expose
    private Integer consignment_status_id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_active")
    @Expose
    private String is_active;
    @SerializedName("primary_key")
    @Expose
    private Integer primary_key;
    @SerializedName("sort_order")
    @Expose
    private Integer sort_order;

    protected Status(Parcel in) {
        code = in.readString();
        if (in.readByte() == 0) {
            consignment_status_id = null;
        } else {
            consignment_status_id = in.readInt();
        }
        description = in.readString();
        is_active = in.readString();
        if (in.readByte() == 0) {
            primary_key = null;
        } else {
            primary_key = in.readInt();
        }
        if (in.readByte() == 0) {
            sort_order = null;
        } else {
            sort_order = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        if (consignment_status_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(consignment_status_id);
        }
        dest.writeString(description);
        dest.writeString(is_active);
        if (primary_key == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(primary_key);
        }
        if (sort_order == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sort_order);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getConsignment_status_id() {
        return consignment_status_id;
    }

    public void setConsignment_status_id(Integer consignment_status_id) {
        this.consignment_status_id = consignment_status_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public Integer getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(Integer primary_key) {
        this.primary_key = primary_key;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }
}
