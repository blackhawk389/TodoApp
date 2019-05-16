package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Toast;

import com.app.boxee.shopper.activities.SplashActivity;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.Status;
import com.app.boxee.shopper.data.WebShop;
import com.app.boxee.shopper.data.request.TrackingRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.TrackResponseData;
import com.app.boxee.shopper.data.response.TrackingResponse;
import com.app.boxee.shopper.fragments.OrderDetailsFragment;
import com.app.boxee.shopper.repositories.ShopperRepository;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.FirebaseUtil;
import com.app.boxee.shopper.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class TrackingViewModel extends ViewModel {

    private final ShopperRepository userRepo;
    private MutableLiveData<TrackingResponse> trackingList = new MutableLiveData<>();
    private ArrayList<ConsignmentFirebaseData> cns;
    private TrackingResponse responseList;
    private Env env;


    @Inject
    public TrackingViewModel(ShopperRepository userRepo) {
        this.userRepo = userRepo;
    }


    public void init(Fragment fragment, String[] consignments, Activity activity,  Env env) {

        this.env = env;
        if (this.responseList != null) {
            return;
        }
        LoaderDialog.showLoader(fragment);
        TrackingRequest request = new TrackingRequest("shopper", Arrays.asList(consignments), FirebaseMessagingService.getToken(fragment.getActivity()));
        userRepo.trackRecord(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200 && response.errorBody() == null) {
                responseList = response.body().getData();
                setData(responseList, Arrays.asList(consignments));
            }else{
                createDailog(response.body().getMessage(), activity);
                //Toast.makeText(fragment.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
//        cns = new ArrayList<>();
//        start(0);

    }

    private void createDailog(String msg, Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env.getAppOk(), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
               alertDialog.dismiss();
            }
        });
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor("#0044B8"));
    }
    public void updateLocation(Activity activity, Fragment fragment, UpdateLocationRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.updateLocation(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200 && response.errorBody() == null){
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                ((OrderDetailsFragment)fragment.getParentFragment()).updateRecord();
            }else{
//                createDailog(response.body().getMessage());
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }
            // ((OrderDetailsFragment) fragment).setViewPagger();
        });



    }

    public void getUpdatedTrackRecord(Fragment fragment, Activity activity, String[] consignments){
        LoaderDialog.showLoader(fragment);
        TrackingRequest request = new TrackingRequest("shopper", Arrays.asList(consignments), FirebaseMessagingService.getToken(fragment.getActivity()));
        userRepo.trackRecord(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200 && response.errorBody() == null) {
                responseList = response.body().getData();
                setData(responseList, Arrays.asList(consignments));
            }else{
                Toast.makeText(fragment.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void setData(TrackingResponse responseList, List<String> consignment) {
        ArrayList<String> falseArray = new ArrayList<>();
        for (int i = 0; i < consignment.size(); i++) {
            boolean found = false;
            for (int j = 0; j < responseList.getConsignments().size(); j++) {
                if (consignment.get(i).equals(String.valueOf(responseList.getConsignments().get(j).getConsignmentId()))) {
                    found = true;
                    break;
                }

            }
            if (!found) {
                falseArray.add(consignment.get(i));
            }
        }
        for (String cn : falseArray) {
            try {
                responseList.getConsignments().add(new Consignment(Long.parseLong(cn), true));
            }catch (Exception ex){

            }
        }
        trackingList.setValue(responseList);

    }

//    void start(int counter) {
//        if (counter < consignments.length)
//            checkFirbase(consignments[counter], counter);
//        else {
//            if (counter == consignments.length) {
//                trackingList.setValue(cns);
//            }
//        }
//    }

    public MutableLiveData<TrackingResponse> getTrackingList() {
        return this.trackingList;
    }

    public void initAdd(Fragment fragment, String[] consignments, Activity activity, Env env) {
        this.responseList = null;
        init(fragment,consignments, activity, env);
    }

//    private void checkFirbase(String s, int i) {
//        LoaderDialog.showLoader(context);
//        FirebaseUtil.checkData(s, this.context, resp -> {
//            LoaderDialog.hideLoader(context);
//            if (resp instanceof ConsignmentFirebaseData) {
//                ConsignmentFirebaseData consignmentFirebaseData = (ConsignmentFirebaseData) resp;
//                consignmentFirebaseData.setFound(true);
//                cns.add(consignmentFirebaseData);
//                FirebaseUtil.checkWebShop(consignmentFirebaseData.getFk_webshop() + "", this.context, resp1 -> {
//                    FirebaseUtil.checkStatus(consignmentFirebaseData.getFk_status() + "", this.context, resp2 -> {
//                        start(i + 1);
//                        Status status = (Status) resp2;
//                        consignmentFirebaseData.setFk_status_name(status.getDescription());
//                    });
//                    WebShop webShop = (WebShop) resp1;
//                    consignmentFirebaseData.setFk_webshop_name(webShop.getWebshop_name());
//                });
//
//            } else {
//                ConsignmentFirebaseData consignmentFirebaseData = new ConsignmentFirebaseData();
//                consignmentFirebaseData.setConsignment_id(Long.parseLong(s));
//                consignmentFirebaseData.setFound(false);
//                cns.add(consignmentFirebaseData);
//                start(i + 1);
//            }
//
//
//        });
//    }
}
