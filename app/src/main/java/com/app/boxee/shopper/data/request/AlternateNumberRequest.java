package com.app.boxee.shopper.data.request;

import com.google.gson.annotations.SerializedName;

public class AlternateNumberRequest{

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("shopper_id")
	private String shopperId;

	@SerializedName("alternate_phone")
	private String alternatePhone;

	@SerializedName("source")
	private String source;

	@SerializedName("type")
	private String type;

	public AlternateNumberRequest(String deviceId, String shopperId, String alternatePhone, String source, String type) {
		this.deviceId = deviceId;
		this.shopperId = shopperId;
		this.alternatePhone = alternatePhone;
		this.source = source;
		this.type = type;

	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setShopperId(String shopperId){
		this.shopperId = shopperId;
	}

	public String getShopperId(){
		return shopperId;
	}

	public void setAlternatePhone(String alternatePhone){
		this.alternatePhone = alternatePhone;
	}

	public String getAlternatePhone(){
		return alternatePhone;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"AlternateNumberRequest{" + 
			"device_id = '" + deviceId + '\'' + 
			",shopper_id = '" + shopperId + '\'' + 
			",alternate_phone = '" + alternatePhone + '\'' + 
			",source = '" + source + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}