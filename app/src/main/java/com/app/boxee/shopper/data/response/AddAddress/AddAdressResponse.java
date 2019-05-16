package com.app.boxee.shopper.data.response.AddAddress;

import com.google.gson.annotations.SerializedName;

public class AddAdressResponse{

	@SerializedName("shopper_address")
	private ShopperAddress shopperAddress;

	public void setShopperAddress(ShopperAddress shopperAddress){
		this.shopperAddress = shopperAddress;
	}

	public ShopperAddress getShopperAddress(){
		return shopperAddress;
	}

	@Override
 	public String toString(){
		return 
			"AddAdressResponse{" + 
			"shopper_address = '" + shopperAddress + '\'' + 
			"}";
		}
}