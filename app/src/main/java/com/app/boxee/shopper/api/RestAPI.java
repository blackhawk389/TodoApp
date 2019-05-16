package com.app.boxee.shopper.api;

import com.app.boxee.shopper.data.request.AddAddress.AddAdressRequest;
import com.app.boxee.shopper.data.request.AlternateNumberStepTwoRequest;
import com.app.boxee.shopper.data.request.DeleteRequest;
import com.app.boxee.shopper.data.request.DownloadPDFRequest;
import com.app.boxee.shopper.data.request.AlternateNumberRequest;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.data.request.GetShipmentByIdRequest;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.NumberVerificationRequest;
import com.app.boxee.shopper.data.request.OrderDetailsByStatusRequest;
import com.app.boxee.shopper.data.request.RateAWBRequest;
import com.app.boxee.shopper.data.request.TrackingRequest;
import com.app.boxee.shopper.data.request.UpdateDateRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.request.UpdatePickupDateRequest;
import com.app.boxee.shopper.data.response.AddAddress.AddAdressResponse;
import com.app.boxee.shopper.data.response.DownloadPDFResponse;
import com.app.boxee.shopper.data.response.Data;
import com.app.boxee.shopper.data.response.GetShipmentByIdResponse;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusList;
import com.app.boxee.shopper.data.response.TrackingResponse;
import com.app.boxee.shopper.data.response.myAdresses.MyAddressesData;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RestAPI {
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);

    @POST("/shopper/login")
    Call<GenericResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("/shopper/login")
    Call<GenericResponse<ShopperData>> verify(@Body NumberVerificationRequest request);

    @POST("/shopper/alternate/phone")
    Call<GenericResponse<LoginResponse>> alternatePhone(@Body AlternateNumberRequest request);

    @POST("/shopper/alternate/phone")
    Call<GenericResponse<Data>> alternatePhoneStep2(@Body AlternateNumberStepTwoRequest request);

    @POST("shopper/get/shipment")
    Call<GenericResponse<GetShipmentByIdResponse>> getShipmentsByPhone(@Body GetShipmentByIdRequest request);

    @POST("shopper/edit")
    Call<GenericResponse<Shopper>> editProfile(@Body EditProfileRequest request);

    @POST("shopper/shipment/details")
    Call<GenericResponse<OrderDetailsByStatusList>> getOrderDetailsByStatus(@Body OrderDetailsByStatusRequest request);

    @POST("consignment/get-track-record")
    Call<GenericResponse<TrackingResponse>> trackListOfCns(@Body TrackingRequest request);

    @POST("shopper/shipment/update")
    Call<GenericResponse> updateDate(@Body UpdateDateRequest request);

    @POST("shopper/shipment/update")
    Call<GenericResponse> updateLocation(@Body UpdateLocationRequest request);

    @POST("shopper/shipment/update")
    Call<GenericResponse> rateAWB(@Body RateAWBRequest request);

    @POST("shopper/pdf/download")
    Call<GenericResponse<DownloadPDFResponse>> downloadPDF(@Body DownloadPDFRequest request);

    @GET("shopper/address-listing")
    Call<GenericResponse<MyAddressesData>> getAddress();

    @GET("shopper/default/address/{add_id}")
    Call<GenericResponse> makeDefault(@Path("add_id") String userId);

    @POST("shopper/update/address/{add_id}")
    Call<GenericResponse> deleteAdd(@Path("add_id") String userId, @Body DeleteRequest delete);

    @POST("shopper/update/address")
    Call<GenericResponse<AddAdressResponse>> addAddress(@Body AddAdressRequest addAdressRequest);

    @POST("shopper/shipment/update")
    Call<GenericResponse> updateDate(@Body UpdatePickupDateRequest request);

    @POST("shopper/update/address/{add_id}")
    Call<GenericResponse<AddAdressResponse>> editAddress(@Path("add_id") String userId, @Body AddAdressRequest addAdressRequest);

    @POST("shopper/ticket/create")
    Call<GenericResponse> generateTicket(@Body RequestBody data);

    @GET("shopper/alert-preference/{pref}")
    Call<GenericResponse> enableNotification(@Path("pref") boolean userId);

    @POST("auth/shopper/logout")
    Call<GenericResponse> logout();
}
