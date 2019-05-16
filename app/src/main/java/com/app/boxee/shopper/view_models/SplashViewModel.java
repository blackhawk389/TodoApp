package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.User;
import com.app.boxee.shopper.network.ServerResponse;
import com.app.boxee.shopper.repositories.ShopperRepository;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Response;

public class SplashViewModel extends ViewModel {

    private LiveData<MetadataData> data;
    private ShopperRepository userRepo;
    private ShopperDao shopperDao;


    @Inject
    public SplashViewModel(ShopperRepository userRepo,ShopperDao shopperDao) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;


    }

    // ----

    public void init(Activity activity) {
        if (this.data != null) {
            return;
        }
       this.data = userRepo.getMetadata(activity);

    }
//    public LiveData<MetadataData> getMataData() {
//        return this.data;
//    }

}

