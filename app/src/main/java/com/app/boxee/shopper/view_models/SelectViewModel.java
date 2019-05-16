package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.data.request.GetShipmentByIdRequest;
import com.app.boxee.shopper.data.response.GetShipmentByIdResponse;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.network.ServerResponse;
import com.app.boxee.shopper.repositories.ShopperRepository;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Response;

public class SelectViewModel extends ViewModel {

    private MutableLiveData<GetShipmentByIdResponse> data= new MutableLiveData<GetShipmentByIdResponse>();
    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private GetShipmentByIdResponse responseList;



    @Inject
    public SelectViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo  = userRepo;
        this.shopperDao = shopperDao;

    }


    public void init(Activity activity,Fragment fragment, GetShipmentByIdRequest request) {
//        LoaderDialog.hideLoader(fragment);
        if (this.responseList != null) {
            return;
        }
        LoaderDialog.showLoader(fragment);

        userRepo.getShipmentByPhone(request, activity, new ServerResponse<Response<GenericResponse<GetShipmentByIdResponse>>>() {
            @Override
            public void sendData(Response<GenericResponse<GetShipmentByIdResponse>> response) throws ParseException, JSONException {
                LoaderDialog.hideLoader(fragment);
                if (response.body().getCode() == 200) {
                    responseList = (GetShipmentByIdResponse) response.body().getData();
                    data.setValue(responseList);
                }
            }});

//      userRepo.getShipmentByPhone(request, activity, response -> {
//            LoaderDialog.hideLoader(fragment);
//            if (response.body().getCode() == 200) {
//                responseList = (GetShipmentByIdResponse)response.body().getData();
//                data.setValue(responseList);
//
//            }
//
//        });

        }
    public void refresh(Activity activity,Fragment fragment, GetShipmentByIdRequest request) {
//        if (this.responseList != null) {
//            return;
//        }
//        LoaderDialog.hideLoader(fragment);

        data = new MutableLiveData<GetShipmentByIdResponse>();
        LoaderDialog.showLoader(fragment);

        userRepo.getShipmentByPhone(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                responseList = (GetShipmentByIdResponse)response.body().getData();
                data.setValue(responseList);

            }

        });

    }

//    private void setDataListItems() {
//
//        if (mDataList == null) {
//            mDataList = new ArrayList<>();
//        }
//        Collections.reverse(responseList);
//        for (int i = 0; i < responseList.size(); i++) {
//            mDataList.add(new TimeLineModel(responseList.get(i).getDetail(), responseList.get(i).getUpdatedAt(), i == 0 ? OrderStatus.INACTIVE : i == 1 ? OrderStatus.ACTIVE : OrderStatus.COMPLETED,responseList.get(i).getCountryName(),responseList.get(i).getCityName(),responseList.get(i).getDepartment()));
//
//        }
////        shipmentStatus.setText(responseList.get(0).getToStatus() + "");
////        ((TimeLineAdapter) mRecyclerView.getAdapter()).setList(mDataList);
//    }


    public MutableLiveData<GetShipmentByIdResponse> getShipmentByIdResponseMutableLiveData() {
        return this.data;
    }


}
