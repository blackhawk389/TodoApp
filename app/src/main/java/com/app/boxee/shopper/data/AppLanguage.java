package com.app.boxee.shopper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppLanguage {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("l_code")
    @Expose
    private String lCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLCode() {
        return lCode;
    }

    public void setLCode(String lCode) {
        this.lCode = lCode;
    }
}
