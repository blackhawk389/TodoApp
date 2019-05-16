package com.app.boxee.shopper.data.response;

import com.app.boxee.shopper.database.entity.ShopperIncommingShipment;
import com.app.boxee.shopper.database.entity.ShopperOutgoingShipment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShipmentByIdResponse {
    @SerializedName("shopper_incomming_shipment")
    @Expose
    private ShopperIncommingShipment shopperIncommingShipment;
    @SerializedName("shopper_outgoing_shipment")
    @Expose
    private ShopperOutgoingShipment shopperOutgoingShipment;

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

}
