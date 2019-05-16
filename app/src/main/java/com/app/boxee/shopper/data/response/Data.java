package com.app.boxee.shopper.data.response;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("success_message")
	private String successMessage;

	public void setSuccessMessage(String successMessage){
		this.successMessage = successMessage;
	}

	public String getSuccessMessage(){
		return successMessage;
	}

	@Override
 	public String toString(){
		return 
			"MyAddressesData{" +
			"success_message = '" + successMessage + '\'' + 
			"}";
		}
}