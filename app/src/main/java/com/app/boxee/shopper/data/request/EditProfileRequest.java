package com.app.boxee.shopper.data.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileRequest implements Parcelable {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;

    protected EditProfileRequest(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        deviceId = in.readString();
        alternatePhone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(deviceId);
        dest.writeString(alternatePhone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditProfileRequest> CREATOR = new Creator<EditProfileRequest>() {
        @Override
        public EditProfileRequest createFromParcel(Parcel in) {
            return new EditProfileRequest(in);
        }

        @Override
        public EditProfileRequest[] newArray(int size) {
            return new EditProfileRequest[size];
        }
    };

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    @SerializedName("alternate_phone")
    private String alternatePhone;
    public String getFirstName() {
        return firstName;
    }

    public EditProfileRequest(String firstName, String lastName, String email,String deviceId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.deviceId = deviceId;
    }
    public EditProfileRequest(String firstName, String lastName, String email,String deviceId,String secNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.deviceId = deviceId;
        this.alternatePhone = secNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
