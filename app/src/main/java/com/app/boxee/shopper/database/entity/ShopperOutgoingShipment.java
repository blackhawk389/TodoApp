package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ShopperOutgoingShipment implements Parcelable{
    @SerializedName("awbs_generated_by_webshop_for_return")
    @Expose
    private Integer awbsGeneratedByWebshopForReturn;
    @SerializedName("schedule_my_pickup")
    @Expose
    private Integer scheduleMyPickup;
    @SerializedName("picking_up_today")
    @Expose
    private Integer pickingUpToday;
    @SerializedName("picked_up_by_boxee")
    @Expose
    private Integer pickedUpByBoxee;
    @SerializedName("return_history")
    @Expose
    private Integer returnHistory;

    protected ShopperOutgoingShipment(Parcel in) {
        if (in.readByte() == 0) {
            awbsGeneratedByWebshopForReturn = null;
        } else {
            awbsGeneratedByWebshopForReturn = in.readInt();
        }
        if (in.readByte() == 0) {
            scheduleMyPickup = null;
        } else {
            scheduleMyPickup = in.readInt();
        }
        if (in.readByte() == 0) {
            pickingUpToday = null;
        } else {
            pickingUpToday = in.readInt();
        }
        if (in.readByte() == 0) {
            pickedUpByBoxee = null;
        } else {
            pickedUpByBoxee = in.readInt();
        }
        if (in.readByte() == 0) {
            returnHistory = null;
        } else {
            returnHistory = in.readInt();
        }
    }

    public ShopperOutgoingShipment() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (awbsGeneratedByWebshopForReturn == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(awbsGeneratedByWebshopForReturn);
        }
        if (scheduleMyPickup == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(scheduleMyPickup);
        }
        if (pickingUpToday == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pickingUpToday);
        }
        if (pickedUpByBoxee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pickedUpByBoxee);
        }
        if (returnHistory == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(returnHistory);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopperOutgoingShipment> CREATOR = new Creator<ShopperOutgoingShipment>() {
        @Override
        public ShopperOutgoingShipment createFromParcel(Parcel in) {
            return new ShopperOutgoingShipment(in);
        }

        @Override
        public ShopperOutgoingShipment[] newArray(int size) {
            return new ShopperOutgoingShipment[size];
        }
    };

    public Integer getAwbsGeneratedByWebshopForReturn() {
        return awbsGeneratedByWebshopForReturn;
    }

    public void setAwbsGeneratedByWebshopForReturn(Integer awbsGeneratedByWebshopForReturn) {
        this.awbsGeneratedByWebshopForReturn = awbsGeneratedByWebshopForReturn;
    }

    public Integer getScheduleMyPickup() {
        return scheduleMyPickup;
    }

    public void setScheduleMyPickup(Integer scheduleMyPickup) {
        this.scheduleMyPickup = scheduleMyPickup;
    }

    public Integer getPickingUpToday() {
        return pickingUpToday;
    }

    public void setPickingUpToday(Integer pickingUpToday) {
        this.pickingUpToday = pickingUpToday;
    }

    public Integer getPickedUpByBoxee() {
        return pickedUpByBoxee;
    }

    public void setPickedUpByBoxee(Integer pickedUpByBoxee) {
        this.pickedUpByBoxee = pickedUpByBoxee;
    }

    public Integer getReturnHistory() {
        return returnHistory;
    }

    public void setReturnHistory(Integer returnHistory) {
        this.returnHistory = returnHistory;
    }
    @TypeConverter // note this annotation
    public String fromMultiOrderModel(ShopperOutgoingShipment optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ShopperOutgoingShipment>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public ShopperOutgoingShipment toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null)
        {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ShopperOutgoingShipment>() {
        }.getType();
        ShopperOutgoingShipment productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
