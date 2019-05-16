
package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {


    @SerializedName("warehouse_id")
    @Expose
    private Integer warehouseId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fk_location_city")
    @Expose
    private Integer fkLocationCity;
    @SerializedName("fk_location")
    @Expose
    private Integer fkLocation;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFkLocationCity() {
        return fkLocationCity;
    }

    public void setFkLocationCity(Integer fkLocationCity) {
        this.fkLocationCity = fkLocationCity;
    }

    public Integer getFkLocation() {
        return fkLocation;
    }

    public void setFkLocation(Integer fkLocation) {
        this.fkLocation = fkLocation;
    }

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

//    Gson gson = new Gson();

//    @TypeConverter
//    public List<Warehouse> stringToSomeObjectList(String data) {
//        if (data == null) {
//            return Collections.emptyList();
//        }
//
//        Type listType = new TypeToken<List<Warehouse>>() {}.getType();
//
//        return gson.fromJson(data, listType);
//    }
//
//    @TypeConverter
//    public String someObjectListToString(List<Warehouse> someObjects) {
//        return gson.toJson(someObjects);
//    }
    @TypeConverter // note this annotation
    public String fromMultiOrderModel(List<Warehouse> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Warehouse>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Warehouse> toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null)
        {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Warehouse>>() {
        }.getType();
        return gson.fromJson(optionValuesString, type);
    }

}
