package com.app.boxee.shopper.api;

import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface MetadataRestAPI {
    @GET("/metadata")
    Call<GenericResponse<MetadataData>> getMetadata();

}
