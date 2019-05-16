package com.app.boxee.shopper.database.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "metadata")
public class MetadataData {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @Expose
    private int id;

    @SerializedName("warehouse")
    @Expose
    @TypeConverters(Warehouse.class)
    private List<Warehouse> warehouse = null;

    public List<Warehouse> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<Warehouse> warehouse) {
        this.warehouse = warehouse;
    }
    @SerializedName("locations")
    @Expose
    @TypeConverters(Locations.class)
    private Locations locations = null;

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }
}
