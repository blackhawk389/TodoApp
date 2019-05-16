package com.app.boxee.shopper.data;

import java.io.Serializable;


public class CourierCenter implements Serializable {

    private String city, area, address;
    private String phone;
    private double longitude, latitude;

    public CourierCenter(String city, String area, String address, String phone, double latitude, double longitude) {
        this.city = city;
        this.area = area;
        this.address = address;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
