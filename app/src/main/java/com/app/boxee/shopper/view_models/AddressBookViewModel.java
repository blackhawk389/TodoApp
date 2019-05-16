package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.AppSidemenu;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.data.response.myAdresses.MyAddressesData;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.fragments.MyAddressesFragment;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

public class AddressBookViewModel extends ViewModel {

    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private MutableLiveData<GenericResponse<MyAddressesData>> data = new MutableLiveData<GenericResponse<MyAddressesData>>();
    private GenericResponse<MyAddressesData> responsedata;
    private GenericResponse makedefaultResponse;
    private MutableLiveData<GenericResponse> datamake = new MutableLiveData<GenericResponse>();
    private GenericResponse deleteResponse;
    private MutableLiveData<GenericResponse> datadelete = new MutableLiveData<GenericResponse>();;
    private LiveData<MetadataData> datamata;

    @Inject
    public AddressBookViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
    }

    public void init(Activity activity) {
        if (this.datamata != null) {
            return;
        }
        this.datamata = userRepo.getMetadata(activity);
    }
    public void updateLocation(Activity activity, Fragment fragment, UpdateLocationRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.updateLocation(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);

            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
    public LiveData<MetadataData> getMataData() {
        return this.datamata;
    }
    public void init(Activity activity, Fragment fragment) {
//        if (this.loginResponse != null) {
//            return;
//        }
        LoaderDialog.showLoader(fragment);
        data = new MutableLiveData<GenericResponse<MyAddressesData>>();
        userRepo.getAddresses( activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200) {
                responsedata = response.body();
                data.setValue(responsedata);
            }
            else {
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    public MutableLiveData<GenericResponse<MyAddressesData>> getLoginResponse() {
        return this.data;
    }


    public void makeDefault(String id, Activity activity, MyAddressesFragment myAddressesFragment) {
        LoaderDialog.showLoader(myAddressesFragment);

        userRepo.makeDefault(id, activity, response -> {
            LoaderDialog.hideLoader(myAddressesFragment);
            if(response.body().getCode() == 200) {
                makedefaultResponse = response.body();
                datamake.setValue(makedefaultResponse);
            }
            else {
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public MutableLiveData<GenericResponse> getdefaultResponse() {
        return this.datamake;
    }
    public void deleteAdd(String id, Activity activity, MyAddressesFragment myAddressesFragment) {
        LoaderDialog.showLoader(myAddressesFragment);

        userRepo.deleteAdd(id, activity, response -> {
            LoaderDialog.hideLoader(myAddressesFragment);
            if(response.body().getCode() == 200) {
                deleteResponse = response.body();
                datadelete.setValue(deleteResponse);
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public MutableLiveData<GenericResponse> deleteResponse() {
        return this.datadelete;
    }
}
