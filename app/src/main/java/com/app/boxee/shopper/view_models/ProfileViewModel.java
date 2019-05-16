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

public class ProfileViewModel extends ViewModel {

    private final ShopperDao shopperDao;
    private ShopperRepository userRepo;
    private Shopper profileResponse;
    private MutableLiveData<Shopper> data = new MutableLiveData<>();
    private GenericResponse<LoginResponse> loginResponse;
    private MutableLiveData<GenericResponse<LoginResponse>> logindata = new MutableLiveData<GenericResponse<LoginResponse>>();

    @Inject
    public ProfileViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
    }

    // ----

    public ShopperData init() {
        return shopperDao.hasShopper();

    }


    public void editProfile(Activity activity, Fragment fragment, EditProfileRequest request, ShopperData shopperData) {

        LoaderDialog.showLoader(fragment);

        userRepo.editProfile(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                profileResponse = response.body().getData();
                data.setValue(profileResponse);
                shopperData.setShopper(response.body().getData());
                shopperDao.updateShopper(shopperData);
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public MutableLiveData<Shopper> getShopper() {
        return this.data;
    }
    public void getstep1(Activity activity, Fragment fragment, EditProfileRequest request, ShopperData shopperData) {

        LoaderDialog.showLoader(fragment);
        AlternateNumberRequest alternateNumberRequest = new AlternateNumberRequest(request.getDeviceId(),String.valueOf(shopperData.getId()),request.getAlternatePhone(),"android","step-1");
        userRepo.alternatePhone(alternateNumberRequest, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if (response.body().getCode() == 200) {
                loginResponse = response.body();
                logindata.setValue(loginResponse);

            } else {
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public MutableLiveData<GenericResponse<LoginResponse>> getAlternateResponse() {
        return this.logindata;
    }


}
