package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.SerializedName;

public class DeleteRequest{

	@SerializedName("action")
	private String action;

	public DeleteRequest(String action) {
		this.action = action;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return action;
	}

	@Override
 	public String toString(){
		return 
			"DeleteRequest{" + 
			"action = '" + action + '\'' + 
			"}";
		}
}