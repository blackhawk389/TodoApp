package com.app.boxee.shopper.data.response.AddAddress;

import com.google.gson.annotations.SerializedName;

public class ShopperAddress{

	@SerializedName("country")
	private String country;

	@SerializedName("phone_no")
	private String phoneNo;

	@SerializedName("city")
	private String city;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("deleted_by")
	private int deletedBy;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("fk_shopper")
	private int fkShopper;

	@SerializedName("google_map")
	private String googleMap;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("address_house_appartment")
	private String addressHouseAppartment;

	@SerializedName("fk_language")
	private int fkLanguage;

	@SerializedName("tag")
	private String tag;

	@SerializedName("fk_location_area")
	private int fkLocationArea;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("address_id")
	private int addressId;

	@SerializedName("fk_location_country")
	private int fkLocationCountry;

	@SerializedName("from_import")
	private String fromImport;

	@SerializedName("is_default")
	private int isDefault;

	@SerializedName("address_two")
	private String addressTwo;

	@SerializedName("created_by")
	private int createdBy;

	@SerializedName("deleted_at")
	private String deletedAt;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("address_landmark")
	private String addressLandmark;

	@SerializedName("phone")
	private String phone;

	@SerializedName("address_one")
	private String addressOne;

	@SerializedName("fk_location_city")
	private int fkLocationCity;

	@SerializedName("updated_by")
	private int updatedBy;

	@SerializedName("alternate_phone")
	private String alternatePhone;

	@SerializedName("carrier_code")
	private String carrierCode;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	public String getPhoneNo(){
		return phoneNo;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setDeletedBy(int deletedBy){
		this.deletedBy = deletedBy;
	}

	public int getDeletedBy(){
		return deletedBy;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setFkShopper(int fkShopper){
		this.fkShopper = fkShopper;
	}

	public int getFkShopper(){
		return fkShopper;
	}

	public void setGoogleMap(String googleMap){
		this.googleMap = googleMap;
	}

	public String getGoogleMap(){
		return googleMap;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setAddressHouseAppartment(String addressHouseAppartment){
		this.addressHouseAppartment = addressHouseAppartment;
	}

	public String getAddressHouseAppartment(){
		return addressHouseAppartment;
	}

	public void setFkLanguage(int fkLanguage){
		this.fkLanguage = fkLanguage;
	}

	public int getFkLanguage(){
		return fkLanguage;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	public void setFkLocationArea(int fkLocationArea){
		this.fkLocationArea = fkLocationArea;
	}

	public int getFkLocationArea(){
		return fkLocationArea;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setAddressId(int addressId){
		this.addressId = addressId;
	}

	public int getAddressId(){
		return addressId;
	}

	public void setFkLocationCountry(int fkLocationCountry){
		this.fkLocationCountry = fkLocationCountry;
	}

	public int getFkLocationCountry(){
		return fkLocationCountry;
	}

	public void setFromImport(String fromImport){
		this.fromImport = fromImport;
	}

	public String getFromImport(){
		return fromImport;
	}

	public void setIsDefault(int isDefault){
		this.isDefault = isDefault;
	}

	public int getIsDefault(){
		return isDefault;
	}

	public void setAddressTwo(String addressTwo){
		this.addressTwo = addressTwo;
	}

	public String getAddressTwo(){
		return addressTwo;
	}

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	public int getCreatedBy(){
		return createdBy;
	}

	public void setDeletedAt(String deletedAt){
		this.deletedAt = deletedAt;
	}

	public String getDeletedAt(){
		return deletedAt;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setAddressLandmark(String addressLandmark){
		this.addressLandmark = addressLandmark;
	}

	public String getAddressLandmark(){
		return addressLandmark;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setAddressOne(String addressOne){
		this.addressOne = addressOne;
	}

	public String getAddressOne(){
		return addressOne;
	}

	public void setFkLocationCity(int fkLocationCity){
		this.fkLocationCity = fkLocationCity;
	}

	public int getFkLocationCity(){
		return fkLocationCity;
	}

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy;
	}

	public int getUpdatedBy(){
		return updatedBy;
	}

	public void setAlternatePhone(String alternatePhone){
		this.alternatePhone = alternatePhone;
	}

	public String getAlternatePhone(){
		return alternatePhone;
	}

	public void setCarrierCode(String carrierCode){
		this.carrierCode = carrierCode;
	}

	public String getCarrierCode(){
		return carrierCode;
	}

	@Override
 	public String toString(){
		return 
			"ShopperAddress{" + 
			"country = '" + country + '\'' + 
			",phone_no = '" + phoneNo + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",deleted_by = '" + deletedBy + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",fk_shopper = '" + fkShopper + '\'' + 
			",google_map = '" + googleMap + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",address_house_appartment = '" + addressHouseAppartment + '\'' + 
			",fk_language = '" + fkLanguage + '\'' + 
			",tag = '" + tag + '\'' + 
			",fk_location_area = '" + fkLocationArea + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",address_id = '" + addressId + '\'' + 
			",fk_location_country = '" + fkLocationCountry + '\'' + 
			",from_import = '" + fromImport + '\'' + 
			",is_default = '" + isDefault + '\'' + 
			",address_two = '" + addressTwo + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",address_landmark = '" + addressLandmark + '\'' + 
			",phone = '" + phone + '\'' + 
			",address_one = '" + addressOne + '\'' + 
			",fk_location_city = '" + fkLocationCity + '\'' + 
			",updated_by = '" + updatedBy + '\'' + 
			",alternate_phone = '" + alternatePhone + '\'' + 
			",carrier_code = '" + carrierCode + '\'' + 
			"}";
		}
}