package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.AlternateNumberRequest;
import com.app.boxee.shopper.data.request.AlternateNumberStepTwoRequest;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.NumberVerificationRequest;
import com.app.boxee.shopper.data.response.Data;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

public class NumberVerificationViewModel extends ViewModel {

    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private GenericResponse<ShopperData> numberResponse;
    private LiveData<ShopperData> data ;
    private GenericResponse<LoginResponse> loginResponse;
    private MutableLiveData<GenericResponse<LoginResponse>> dataLogin = new MutableLiveData<>();
    private MutableLiveData<GenericResponse<Data>> logindata = new MutableLiveData<GenericResponse<Data>>();
    private GenericResponse<Data> dataResponse;
    private Shopper profileResponse;
    private MutableLiveData<Shopper> dataProfile = new MutableLiveData<>();
    @Inject
    public NumberVerificationViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;

    }


    public void init(Activity activity, Fragment fragment, NumberVerificationRequest request) {
        LoaderDialog.showLoader(fragment);
        if (this.numberResponse != null) {
            return;
        }
        this.data =  userRepo.getShopper(activity,fragment,request);
        LoaderDialog.hideLoader(fragment);
    }


    public LiveData<ShopperData> getNumberVerificationResponse() {
        return this.data;
    }

    public void getstep2(Activity activity, Fragment fragment, EditProfileRequest request, ShopperData shopperData,String alternatePin) {
        LoaderDialog.showLoader(fragment);
        AlternateNumberStepTwoRequest alternateNumberRequest = new AlternateNumberStepTwoRequest(request.getDeviceId(),String.valueOf(shopperData.getId()),request.getAlternatePhone(),"android","step-2",alternatePin);

        userRepo.alternatePhone2(alternateNumberRequest, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                dataResponse = response.body();
                logindata.setValue(dataResponse);

            } else {
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public ShopperData init() {
        return shopperDao.hasShopper();

    }
    public MutableLiveData<GenericResponse<Data>> getNumberVerificationResponsesec() {
        return this.logindata;
    }
    public void editProfile(Activity activity, Fragment fragment, EditProfileRequest request, ShopperData shopperData) {

        LoaderDialog.showLoader(fragment);

        userRepo.editProfile(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response != null ) {
                if (response.body().getCode() == 200) {
                    profileResponse = response.body().getData();
                    dataProfile.setValue(profileResponse);
                    shopperData.setShopper(response.body().getData());
                    shopperDao.updateShopper(shopperData);
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    public MutableLiveData<Shopper> getShopper() {
        return this.dataProfile;
    }
    public void loginAgain(Activity activity, Fragment fragment, LoginRequest request) {
//        if (this.loginResponse != null) {
//            return;
//        }
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
