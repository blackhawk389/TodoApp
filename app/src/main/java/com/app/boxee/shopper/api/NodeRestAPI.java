package com.app.boxee.shopper.api;

import com.app.boxee.shopper.data.request.TrackRequest;
import com.app.boxee.shopper.data.response.TrackResponseData;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NodeRestAPI {

    @POST("/tracking/getTrackingRecordByCnNo")
    Call<GenericResponse<List<TrackResponseData>>> trackShipment(@Body TrackRequest request);
}
