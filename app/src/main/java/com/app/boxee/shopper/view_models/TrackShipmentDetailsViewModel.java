package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.OrderStatus;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.data.request.TrackRequest;
import com.app.boxee.shopper.data.response.TrackResponseData;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.repositories.ShopperRepository;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

public class TrackShipmentDetailsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TimeLineModel>> data= new MutableLiveData<>();
    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private ArrayList<TrackResponseData> responseList;
    private ArrayList<TimeLineModel> mDataList;



    @Inject
    public TrackShipmentDetailsViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo  = userRepo;
        this.shopperDao = shopperDao;

    }


    public void init(Activity activity,Fragment fragment, TrackRequest request) {
        if (this.responseList != null) {
            return;
        }
        LoaderDialog.showLoader(fragment);

      userRepo.getShipmentDetails(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                responseList = (ArrayList<TrackResponseData>) response.body().getData();
                setDataListItems();
            }

        });

        }

    private void setDataListItems() {

        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        Collections.reverse(responseList);
        for (int i = 0; i < responseList.size(); i++) {
            mDataList.add(new TimeLineModel(responseList.get(i).getDetail(), responseList.get(i).getUpdatedAt(), i == 0 ? OrderStatus.INACTIVE : i == 1 ? OrderStatus.ACTIVE : OrderStatus.COMPLETED,responseList.get(i).getCountryName(),responseList.get(i).getCityName(),responseList.get(i).getDepartment()));

        }
        data.setValue(mDataList);
//        shipmentStatus.setText(responseList.get(0).getToStatus() + "");
//        ((TimeLineAdapter) mRecyclerView.getAdapter()).setList(mDataList);
    }


    public MutableLiveData<ArrayList<TimeLineModel>> getTrackingList() {
        return this.data;
    }


}
