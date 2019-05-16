package com.app.boxee.shopper.data.request.AddAddress;


import com.google.gson.annotations.SerializedName;

public class AddAdressRequest{

	@SerializedName("building_name")
	private String buildingName;

	@SerializedName("apartment_number")
	private String apartmentNumber;

	@SerializedName("street")
	private String street;

	@SerializedName("action")
	private String action;

	@SerializedName("tag")
	private String tag;

	@SerializedName("area_text")
	private String areaId;

	@SerializedName("nearest_landmark")
	private String nearestLandmark;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("country_id")
	private int countryId;

	@SerializedName("city_id")
	private int cityId;

	public AddAdressRequest(String buildingName, String apartmentNumber, String street, String action, String tag, String areaId, String nearestLandmark,String latitude,String longitude, int countryId, int cityId) {
		this.buildingName = buildingName;
		this.apartmentNumber = apartmentNumber;
		this.street = street;
		this.action = action;
		this.tag = tag;
		this.areaId = areaId;
		this.nearestLandmark = nearestLandmark;
		this.latitude = latitude;
		this.longitude = longitude;
		this.countryId = countryId;
		this.cityId = cityId;
	}

	public void setBuildingName(String buildingName){
		this.buildingName = buildingName;
	}

	public String getBuildingName(){
		return buildingName;
	}

	public void setApartmentNumber(String apartmentNumber){
		this.apartmentNumber = apartmentNumber;
	}

	public String getApartmentNumber(){
		return apartmentNumber;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return action;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	public void setAreaId(String areaId){
		this.areaId = areaId;
	}

	public String getAreaId(){
		return areaId;
	}

	public void setNearestLandmark(String nearestLandmark){
		this.nearestLandmark = nearestLandmark;
	}

	public String getNearestLandmark(){
		return nearestLandmark;
	}

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}

	public void setCityId(int cityId){
		this.cityId = cityId;
	}

	public int getCityId(){
		return cityId;
	}

	@Override
 	public String toString(){
		return 
			"AddAdressRequest{" + 
			"building_name = '" + buildingName + '\'' + 
			",apartment_number = '" + apartmentNumber + '\'' + 
			",street = '" + street + '\'' + 
			",action = '" + action + '\'' + 
			",tag = '" + tag + '\'' + 
			",area_id = '" + areaId + '\'' + 
			",nearest_landmark = '" + nearestLandmark + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",city_id = '" + cityId + '\'' + 
			"}";
		}
}