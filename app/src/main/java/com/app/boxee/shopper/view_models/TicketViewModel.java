package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.AlternateNumberRequest;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;

public class TicketViewModel extends ViewModel {

    private final ShopperDao shopperDao;
    private ShopperRepository userRepo;
    private Shopper profileResponse;
    private MutableLiveData<Object> data = new MutableLiveData<>();
    private GenericResponse<Object> loginResponse;
    private MutableLiveData<GenericResponse<Object>> logindata = new MutableLiveData<GenericResponse<Object>>();

    @Inject
    public TicketViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
    }

    // ----

    public ShopperData init() {
        return shopperDao.hasShopper();

    }


    public void generateTicket(Activity activity, Fragment fragment, MultipartBody request, ShopperData shopperData) {

        LoaderDialog.showLoader(fragment);


        userRepo.generateTicket(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                loginResponse = response.body();
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                data.setValue(loginResponse);
            } else {
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public MutableLiveData<Object> getShopper() {
        return this.data;
    }
//    public void getstep1(Activity activity, Fragment fragment, EditProfileRequest request, ShopperData shopperData) {
//
//        LoaderDialog.showLoader(fragment);
//        AlternateNumberRequest alternateNumberRequest = new AlternateNumberRequest(request.getDeviceId(),String.valueOf(shopperData.getId()),request.getAlternatePhone(),"android","step-1");
//        userRepo.alternatePhone(alternateNumberRequest, activity, response -> {
//            LoaderDialog.hideLoader(fragment);
//            if (response.body().getCode() == 200) {
//                loginResponse = response.body();
//                logindata.setValue(loginResponse);
//
//            } else {
//                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }
//
//    public MutableLiveData<GenericResponse<LoginResponse>> getAlternateResponse() {
//        return this.logindata;
//    }


}
