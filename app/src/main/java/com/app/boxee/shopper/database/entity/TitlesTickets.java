package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ramal NGI on 3/18/2019.
 */

public class TitlesTickets implements Parcelable {

    @SerializedName("en")
    @Expose
   // @TypeConverters(En.class)
    public List<String> enTitles;

    @SerializedName("ar")
    @Expose
   // @TypeConverters(Ar.class)

    public List<String> arTitles;

    public List<String> getEnTitles() {
        return enTitles;
    }

    public void setEnTitles(List<String> enTitles) {
        this.enTitles = enTitles;
    }

    public List<String> getArTitles() {
        return arTitles;
    }

    public void setArTitles(List<String> arTitles) {
        this.arTitles = arTitles;
    }

    public static Creator<TitlesTickets> getCREATOR() {
        return CREATOR;
    }

    public TitlesTickets() {

    }

    protected TitlesTickets(Parcel in) {
    }

    public static final Creator<TitlesTickets> CREATOR = new Creator<TitlesTickets>() {
        @Override
        public TitlesTickets createFromParcel(Parcel in) {
            return new TitlesTickets(in);
        }

        @Override
        public TitlesTickets[] newArray(int size) {
            return new TitlesTickets[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    @TypeConverter // note this annotation
    public String fromMultiOrderModel(TitlesTickets optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<TitlesTickets>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public TitlesTickets toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<TitlesTickets>() {
        }.getType();
        TitlesTickets productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
