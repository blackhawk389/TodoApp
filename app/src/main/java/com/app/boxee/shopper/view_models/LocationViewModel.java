package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.AddAddress.AddAdressRequest;
import com.app.boxee.shopper.data.response.AddAddress.AddAdressResponse;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.fragments.AddAddressFragment;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

public class LocationViewModel extends ViewModel {

    private LiveData<MetadataData> data;
    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private GenericResponse<AddAdressResponse> addAddressResponse;
    private MutableLiveData<GenericResponse<AddAdressResponse>> addData = new MutableLiveData<>();


    @Inject
    public LocationViewModel(ShopperRepository userRepo, ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
    }


    public void init(Activity activity) {
        if (this.data != null) {
            return;
        }
       this.data = userRepo.getMetadata(activity);
    }


    public LiveData<MetadataData> getMataData() {
        return this.data;
    }
    public void addAddress(Activity activity, AddAddressFragment fragment, AddAdressRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.addAddress(request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200) {
                addAddressResponse = response.body();
                addData.setValue(addAddressResponse);
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public MutableLiveData<GenericResponse<AddAdressResponse>> getAddResponse() {
        return this.addData;
    }
    public void editAddress(Activity activity,String addId, AddAddressFragment fragment, AddAdressRequest request) {
        LoaderDialog.showLoader(fragment);

        userRepo.editAddress(addId,request, activity, response -> {
            LoaderDialog.hideLoader(fragment);
            if(response.body().getCode() == 200) {
                addAddressResponse = response.body();
                addData.setValue(addAddressResponse);
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(activity,response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public MutableLiveData<GenericResponse<AddAdressResponse>> geteditResponse() {
        return this.addData;
    }

}

