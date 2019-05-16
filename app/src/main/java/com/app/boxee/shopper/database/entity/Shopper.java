package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Shopper implements Parcelable {
    @SerializedName("shopper_id")
    @Expose
    private Integer shopperId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @SerializedName("last_name")
    @Expose
    private String lname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country_code")
    @Expose
    private Object countryCode;
    @SerializedName("carrier_code")
    @Expose
    protected Object carrierCode;
    @SerializedName("phone_no")
    @Expose
    private Object phoneNo;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("alternate_phone")
    @Expose
    private String alternatePhone;
    @SerializedName("fk_language_preferred")
    @Expose
    private Integer fkLanguagePreferred;
    @SerializedName("security_pin")
    @Expose
    private String securityPin;
    @SerializedName("alert_preference")
    @Expose
    private String alertPreference;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Integer updatedBy;
    @SerializedName("deleted_by")
    @Expose
    private Integer deletedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    protected Shopper(Parcel in) {
        if (in.readByte() == 0) {
            shopperId = null;
        } else {
            shopperId = in.readInt();
        }
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        if (in.readByte() == 0) {
            fkLanguagePreferred = null;
        } else {
            fkLanguagePreferred = in.readInt();
        }
        securityPin = in.readString();
        alertPreference = in.readString();
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        if (in.readByte() == 0) {
            updatedBy = null;
        } else {
            updatedBy = in.readInt();
        }
        if (in.readByte() == 0) {
            deletedBy = null;
        } else {
            deletedBy = in.readInt();
        }
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public Shopper() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (shopperId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(shopperId);
        }
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        if (fkLanguagePreferred == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fkLanguagePreferred);
        }
        dest.writeString(securityPin);
        dest.writeString(alertPreference);
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        if (updatedBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(updatedBy);
        }
        if (deletedBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(deletedBy);
        }
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shopper> CREATOR = new Creator<Shopper>() {
        @Override
        public Shopper createFromParcel(Parcel in) {
            return new Shopper(in);
        }

        @Override
        public Shopper[] newArray(int size) {
            return new Shopper[size];
        }
    };

    public Integer getShopperId() {
        return shopperId;
    }

    public void setShopperId(Integer shopperId) {
        this.shopperId = shopperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public Object getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(Object carrierCode) {
        this.carrierCode = carrierCode;
    }

    public Object getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Object phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public Integer getFkLanguagePreferred() {
        return fkLanguagePreferred;
    }

    public void setFkLanguagePreferred(Integer fkLanguagePreferred) {
        this.fkLanguagePreferred = fkLanguagePreferred;
    }

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }

    public String getAlertPreference() {
        return alertPreference;
    }

    public void setAlertPreference(String alertPreference) {
        this.alertPreference = alertPreference;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }
    @TypeConverter // note this annotation
    public String fromMultiOrderModel(Shopper optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Shopper>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public Shopper toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null)
        {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Shopper>() {
        }.getType();
        Shopper productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}
