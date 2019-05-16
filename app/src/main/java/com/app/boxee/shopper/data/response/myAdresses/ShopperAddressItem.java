package com.app.boxee.shopper.data.response.myAdresses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ShopperAddressItem implements Parcelable{

	@SerializedName("building_name")
	private String buildingName;

	@SerializedName("apartment_number")
	private String apartmentNumber;

	@SerializedName("address_hierarchy")
	private String addressHierarchy;

	@SerializedName("phone")
	private String  phone;

	@SerializedName("street")
	private String street;

	@SerializedName("address_id")
	private int addressId;

	@SerializedName("tag")
	private String tag;

	@SerializedName("area_text")
	private String areaId;

	@SerializedName("is_default")
	private int isDefault;

	@SerializedName("nearest_landmark")
	private String nearestLandmark;
	private String cityName;

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

	public ShopperAddressItem(String buildingName, String apartmentNumber, String addressHierarchy, String phone, String street, int addressId, String tag, String areaId, int isDefault, String nearestLandmark,String latitude,String longitude, int countryId, int cityId) {
		this.buildingName = buildingName;
		this.apartmentNumber = apartmentNumber;
		this.addressHierarchy = addressHierarchy;
		this.phone = phone;
		this.street = street;
		this.addressId = addressId;
		this.tag = tag;
		this.areaId = areaId;
		this.isDefault = isDefault;
		this.nearestLandmark = nearestLandmark;
		this.latitude = latitude;
		this.longitude = longitude;
		this.countryId = countryId;
		this.cityId = cityId;
	}

	protected ShopperAddressItem(Parcel in) {
		buildingName = in.readString();
		apartmentNumber = in.readString();
		addressHierarchy = in.readString();
		phone = in.readString();
		street = in.readString();
		addressId = in.readInt();
		tag = in.readString();
		areaId = in.readString();
		isDefault = in.readInt();
		nearestLandmark = in.readString();
		latitude = in.readString();
		longitude = in.readString();
		countryId = in.readInt();
		cityId = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(buildingName);
		dest.writeString(apartmentNumber);
		dest.writeString(addressHierarchy);
		dest.writeString(phone);
		dest.writeString(street);
		dest.writeInt(addressId);
		dest.writeString(tag);
		dest.writeString(areaId);
		dest.writeInt(isDefault);
		dest.writeString(nearestLandmark);
		dest.writeString(latitude);
		dest.writeString(longitude);
		dest.writeInt(countryId);
		dest.writeInt(cityId);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ShopperAddressItem> CREATOR = new Creator<ShopperAddressItem>() {
		@Override
		public ShopperAddressItem createFromParcel(Parcel in) {
			return new ShopperAddressItem(in);
		}

		@Override
		public ShopperAddressItem[] newArray(int size) {
			return new ShopperAddressItem[size];
		}
	};

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

	public void setAddressHierarchy(String addressHierarchy){
		this.addressHierarchy = addressHierarchy;
	}

	public String getAddressHierarchy(){
		return addressHierarchy;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setAddressId(int addressId){
		this.addressId = addressId;
	}

	public int getAddressId(){
		return addressId;
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

	public void setIsDefault(int isDefault){
		this.isDefault = isDefault;
	}

	public int getIsDefault(){
		return isDefault;
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
			"ShopperAddressItem{" + 
			"building_name = '" + buildingName + '\'' + 
			",apartment_number = '" + apartmentNumber + '\'' + 
			",address_hierarchy = '" + addressHierarchy + '\'' + 
			",phone = '" + phone + '\'' + 
			",street = '" + street + '\'' + 
			",address_id = '" + addressId + '\'' + 
			",tag = '" + tag + '\'' + 
			",area_id = '" + areaId + '\'' + 
			",is_default = '" + isDefault + '\'' + 
			",nearest_landmark = '" + nearestLandmark + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",city_id = '" + cityId + '\'' + 
			"}";
		}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}
}