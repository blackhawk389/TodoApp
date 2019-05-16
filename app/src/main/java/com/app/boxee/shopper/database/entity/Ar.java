
package com.app.boxee.shopper.database.entity;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Ar {
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("has_operation")
    @Expose
    private String hasOperation;
    @SerializedName("id_parent")
    @Expose
    private Integer idParent;

    public Ar() {

    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHasOperation() {
        return hasOperation;
    }

    public void setHasOperation(String hasOperation) {
        this.hasOperation = hasOperation;
    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    @TypeConverter // note this annotation
    public String fromMultiOrderModel(List<Ar> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ar>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Ar> toMultiOrderModel(String optionValuesString) {

        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ar>>() {
        }.getType();
        return gson.fromJson(optionValuesString, type);
    }
}
