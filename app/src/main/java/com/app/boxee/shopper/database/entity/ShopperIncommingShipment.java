package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ShopperIncommingShipment implements Parcelable{
    @SerializedName("in_progress_with_webshop")
    @Expose
    private Integer inProgressWithWebshop;
    @SerializedName("in_progress_with_boxee")
    @Expose
    private Integer inProgressWithBoxee;
    @SerializedName("arriving_today")
    @Expose
    private Integer arrivingToday;
    @SerializedName("hold_on_boxee")
    @Expose
    private Integer holdOnBoxee;
    @SerializedName("history")
    @Expose
    private Integer history;

    @SerializedName("return_to_shipper")
    @Expose
    private Integer returnToShipper;

    protected ShopperIncommingShipment(Parcel in) {
        if (in.readByte() == 0) {
            inProgressWithWebshop = null;
        } else {
            inProgressWithWebshop = in.readInt();
        }
        if (in.readByte() == 0) {
            inProgressWithBoxee = null;
        } else {
            inProgressWithBoxee = in.readInt();
        }
        if (in.readByte() == 0) {
            arrivingToday = null;
        } else {
            arrivingToday = in.readInt();
        }
        if (in.readByte() == 0) {
            holdOnBoxee = null;
        } else {
            holdOnBoxee = in.readInt();
        }
        if (in.readByte() == 0) {
            history = null;
        } else {
            history = in.readInt();
        }
        if (in.readByte() == 0) {
            history = null;
        } else {
            returnToShipper = in.readInt();
        }
    }

    public static final Creator<ShopperIncommingShipment> CREATOR = new Creator<ShopperIncommingShipment>() {
        @Override
        public ShopperIncommingShipment createFromParcel(Parcel in) {
            return new ShopperIncommingShipment(in);
        }

        @Override
        public ShopperIncommingShipment[] newArray(int size) {
            return new ShopperIncommingShipment[size];
        }
    };

    public ShopperIncommingShipment() {
    }

    public Integer getInProgressWithWebshop() {
        return inProgressWithWebshop;
    }

    public void setInProgressWithWebshop(Integer inProgressWithWebshop) {
        this.inProgressWithWebshop = inProgressWithWebshop;
    }

    public Integer getInProgressWithBoxee() {
        return inProgressWithBoxee;
    }

    public void setInProgressWithBoxee(Integer inProgressWithBoxee) {
        this.inProgressWithBoxee = inProgressWithBoxee;
    }

    public Integer getArrivingToday() {
        return arrivingToday;
    }

    public void setArrivingToday(Integer arrivingToday) {
        this.arrivingToday = arrivingToday;
    }

    public Integer getHoldOnBoxee() {
        return holdOnBoxee;
    }

    public void setHoldOnBoxee(Integer holdOnBoxee) {
        this.holdOnBoxee = holdOnBoxee;
    }

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }
    @TypeConverter // note this annotation
    public String fromMultiOrderModel(ShopperIncommingShipment optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ShopperIncommingShipment>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public ShopperIncommingShipment toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null)
        {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ShopperIncommingShipment>() {
        }.getType();
        ShopperIncommingShipment productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (inProgressWithWebshop == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(inProgressWithWebshop);
        }
        if (inProgressWithBoxee == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(inProgressWithBoxee);
        }
        if (arrivingToday == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(arrivingToday);
        }
        if (holdOnBoxee == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(holdOnBoxee);
        }
        if (history == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(history);
        }
    }

    public Integer getReturnToShipper() {
        return returnToShipper;
    }

    public void setReturnToShipper(Integer returnToShipper) {
        this.returnToShipper = returnToShipper;
    }

}
