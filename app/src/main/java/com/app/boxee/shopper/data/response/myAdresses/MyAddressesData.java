package com.app.boxee.shopper.data.response.myAdresses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyAddressesData {

	@SerializedName("shopper_address")
	private List<ShopperAddressItem> shopperAddress;

	public void setShopperAddress(List<ShopperAddressItem> shopperAddress){
		this.shopperAddress = shopperAddress;
	}

	public List<ShopperAddressItem> getShopperAddress(){
		return shopperAddress;
	}

	@Override
 	public String toString(){
		return 
			"MyAddressesData{" +
			"shopper_address = '" + shopperAddress + '\'' + 
			"}";
		}
}