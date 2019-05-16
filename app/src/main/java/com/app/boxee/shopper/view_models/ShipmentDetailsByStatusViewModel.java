package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.UpdateDateRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

public class ShipmentDetailsByStatusViewModel extends ViewModel {

    private ShopperRepository userRepo;
    private ShopperDao shopperDao;


    @Inject
    public ShipmentDetailsByStatusViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;

    }


    public void updateDate(Activity activity, Fragment fragment, UpdateDateRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.updateDate(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);

            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
    public void updateLocation(Activity activity, Fragment fragment, UpdateLocationRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.updateLocation(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);

            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

}
