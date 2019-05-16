package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.boxee.shopper.database.converter.ListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "ShopperData")

public class ShopperData implements Parcelable{
    protected ShopperData(Parcel in) {
        id = in.readInt();
        apiToken = in.readString();
    }

    public ShopperData() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(apiToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<ShopperData> CREATOR = new Creator<ShopperData>() {
        @Override
        public ShopperData createFromParcel(Parcel in) {
            return new ShopperData(in);
        }

        @Override
        public ShopperData[] newArray(int size) {
            return new ShopperData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @Expose
    private int id;
//
//    public List<String> getTicketList() {
//        return ticketList;
//    }
//
//    public void setTicketList(List<String> ticketList) {
//        this.ticketList = ticketList;
//    }
////
    @SerializedName("ticket_title")
    @Expose
    @TypeConverters(TitlesTickets.class)
    private TitlesTickets ticketList = null;

    public static Creator<ShopperData> getCREATOR() {
        return CREATOR;
    }


    @SerializedName("api_token")
    @Expose
    protected String apiToken;
    @SerializedName("shopper")
    @Expose
    @TypeConverters(Shopper.class)
    private Shopper shopper;
    @SerializedName("shopper_incomming_shipment")
    @Expose
    @TypeConverters(ShopperIncommingShipment.class)
    private ShopperIncommingShipment shopperIncommingShipment;
    @SerializedName("shopper_outgoing_shipment")
    @Expose
    @TypeConverters(ShopperOutgoingShipment.class)
    private ShopperOutgoingShipment shopperOutgoingShipment;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Shopper getShopper() {
        return shopper;
    }

    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
    }

    public ShopperIncommingShipment getShopperIncommingShipment() {
        return shopperIncommingShipment;
    }

    public void setShopperIncommingShipment(ShopperIncommingShipment shopperIncommingShipment) {
        this.shopperIncommingShipment = shopperIncommingShipment;
    }

    public ShopperOutgoingShipment getShopperOutgoingShipment() {
        return shopperOutgoingShipment;
    }

    public void setShopperOutgoingShipment(ShopperOutgoingShipment shopperOutgoingShipment) {
        this.shopperOutgoingShipment = shopperOutgoingShipment;
    }

    public TitlesTickets getTicketList() {
        return ticketList;
    }

    public void setTicketList(TitlesTickets ticketList) {
        this.ticketList = ticketList;
    }

}
