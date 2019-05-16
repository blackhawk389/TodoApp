package com.app.boxee.shopper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppSidemenu {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("key")
    @Expose
    private String key;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
