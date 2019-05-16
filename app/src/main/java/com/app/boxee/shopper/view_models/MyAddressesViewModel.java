package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.NumberVerificationRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

public class MyAddressesViewModel extends ViewModel {

    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private GenericResponse<ShopperData> numberResponse;
    private LiveData<ShopperData> data ;
    private GenericResponse<LoginResponse> loginResponse;
    private MutableLiveData<GenericResponse<LoginResponse>> dataLogin = new MutableLiveData<>();

    @Inject
    public MyAddressesViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;

    }




    public void loginAgain(Activity activity, Fragment fragment, LoginRequest request) {
        LoaderDialog.showLoader(fragment);
        userRepo.login(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            loginResponse = response.body();
            dataLogin.setValue(loginResponse);
        });

    }


    public MutableLiveData<GenericResponse<LoginResponse>> getLoginResponse() {
        return this.dataLogin;
    }


}
