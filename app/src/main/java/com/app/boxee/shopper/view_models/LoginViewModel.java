package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.OrderStatus;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.TrackRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.data.response.TrackResponseData;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.repositories.ShopperRepository;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private GenericResponse<LoginResponse> loginResponse;
    private MutableLiveData<GenericResponse<LoginResponse>> data = new MutableLiveData<GenericResponse<LoginResponse>>();;

    @Inject
    public LoginViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
    }


    public void init(Activity activity, Fragment fragment, LoginRequest request) {
//        if (this.loginResponse != null) {
//            return;
//        }
        LoaderDialog.showLoader(fragment);
        data= new MutableLiveData<>();
        userRepo.login(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200) {
                loginResponse = response.body();
                data.setValue(loginResponse);
            }
            else {
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    public MutableLiveData<GenericResponse<LoginResponse>> getLoginResponse() {
        return this.data;
    }


}
