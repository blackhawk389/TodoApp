package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class Locations{
    @SerializedName("en")
    @Expose
    @TypeConverters(En.class)
    private List<En> en = null;
    @SerializedName("ar")
    @Expose
    @TypeConverters(Ar.class)
    private List<Ar> ar = null;

    public List<En> getEn() {
        return en;
    }

    public void setEn(List<En> en) {
        this.en = en;
    }

    public List<Ar> getAr() {
        return ar;
    }

    public void setAr(List<Ar> ar) {
        this.ar = ar;
    }

    @TypeConverter // note this annotation
    public String fromMultiOrderModel(Locations optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Locations>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public Locations toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null)
        {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Locations>() {
        }.getType();
        Locations productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
