package com.app.boxee.shopper.data;

import android.os.Parcel;
import android.os.Parcelable;

public class WebShop implements Parcelable{
    public WebShop(){

    }
    String contact_person;

    public WebShop(Parcel in) {
        contact_person = in.readString();
        status = in.readString();
        webshop_name = in.readString();
        primary_key = in.readInt();
        webshop_id = in.readInt();
    }

    public static final Creator<WebShop> CREATOR = new Creator<WebShop>() {
        @Override
        public WebShop createFromParcel(Parcel in) {
            return new WebShop(in);
        }

        @Override
        public WebShop[] newArray(int size) {
            return new WebShop[size];
        }
    };

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWebshop_name() {
        return webshop_name;
    }

    public void setWebshop_name(String webshop_name) {
        this.webshop_name = webshop_name;
    }

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
    }

    public int getWebshop_id() {
        return webshop_id;
    }

    public void setWebshop_id(int webshop_id) {
        this.webshop_id = webshop_id;
    }

    String status;
    String webshop_name;
    int primary_key, webshop_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(contact_person);
        parcel.writeString(status);
        parcel.writeString(webshop_name);
        parcel.writeInt(primary_key);
        parcel.writeInt(webshop_id);
    }
}

