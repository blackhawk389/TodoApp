package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

import com.app.boxee.shopper.adapter.TimeLineAddressAdapter;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.AddressModel;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.request.DownloadPDFRequest;
import com.app.boxee.shopper.data.request.RateAWBRequest;
import com.app.boxee.shopper.data.request.UpdateDateRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.request.UpdatePickupDateRequest;
import com.app.boxee.shopper.data.response.DownloadPDFResponse;
import com.app.boxee.shopper.data.response.GetShipmentByIdResponse;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.fragments.MyAddressesFragment;
import com.app.boxee.shopper.fragments.OrderDetailsFragment;
import com.app.boxee.shopper.fragments.OrderStatusFragment;
import com.app.boxee.shopper.fragments.ShipmentDetailsByStatusFragment;
import com.app.boxee.shopper.repositories.ShopperRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class ShipmentDetailsViewModel extends ViewModel {

    private LiveData<MetadataData> data;
    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    public DownloadPDFResponse downloadPDFResponse;
    private Activity activity;
    Env env;


    @Inject
    public ShipmentDetailsViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
    }

    // ----

    public void init(Activity activity, Env env) {
        this.env = env;
        this.activity = activity;
        if (this.data != null) {
            return;
        }
        this.data = userRepo.getMetadata(activity);
    }

    public LiveData<MetadataData> getMataData() {
        return this.data;
    }

    public void updateDate(Activity activity, Fragment fragment, UpdateDateRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.updateDate(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200 && response.errorBody() == null){
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                ((OrderDetailsFragment)fragment.getParentFragment()).updateRecord();
            }else{
                createDailog(response.body().getMessage());
            }
           // Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    public void updateDate(Activity activity, Fragment fragment, UpdatePickupDateRequest request) {
        LoaderDialog.showLoader(fragment);
        userRepo.updateDate(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200 && response.errorBody() == null){
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                ((OrderDetailsFragment)fragment.getParentFragment()).updateRecord();
            }else{
                createDailog(response.body().getMessage());
            }
//            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    public void updateLocation(Activity activity, Fragment fragment, UpdateLocationRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.updateLocation(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200 && response.errorBody() == null){
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                ((OrderDetailsFragment)fragment.getParentFragment()).updateRecord();
                //removed for not updating ahead time
                //((ShipmentDetailsByStatusFragment)fragment).setAddress();

            }else{
                createDailog(response.body().getMessage());
            }
           // ((OrderDetailsFragment) fragment).setViewPagger();
        });



    }
    private void createDailog(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env.getAppOk(), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setTextColor(Color.parseColor("#0044B8"));
    }

    public void rateAWB(Activity activity, Fragment fragment, RateAWBRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.rateAWB(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);

            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    public void downloadPDF(Activity activity, Fragment fragment, DownloadPDFRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.downloadPDF(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                downloadPDFResponse = (DownloadPDFResponse) response.body().getData();
                ((ShipmentDetailsByStatusFragment) fragment).openPDF(downloadPDFResponse.getDownloadPdf().getAwbPdfUrl());
            }
        });

    }
}

