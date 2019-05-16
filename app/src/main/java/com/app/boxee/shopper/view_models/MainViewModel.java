package com.app.boxee.shopper.view_models;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.activities.SplashActivity;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.NumberVerificationRequest;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.fragments.DashboardFragment;
import com.app.boxee.shopper.fragments.HomeFragment;
import com.app.boxee.shopper.fragments.OrderStatusFragment;
import com.app.boxee.shopper.network.ServerResponse;
import com.app.boxee.shopper.repositories.ShopperRepository;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;

import org.json.JSONException;

import java.text.ParseException;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final Executor executor;
    private MutableLiveData<ShopperData> data = new MutableLiveData<>();
    private ShopperRepository userRepo;
    private ShopperDao shopperDao;
    private LiveData<MetadataData> metadata;
    private ShopperData shopper;


    @Inject
    public MainViewModel(ShopperRepository userRepo, ShopperDao shopperDao, Executor executor) {
        this.userRepo = userRepo;
        this.shopperDao = shopperDao;
        this.executor = executor;
    }

    // ----

    public void init(Bundle savedInstanceState, FragmentManager supportFragmentManager, int fragment_container, Activity activity,Bundle extra) {
//        executor.execute(() -> {
        boolean shopperExist = (shopperDao.hasShopper() != null);
        if (extra != null && extra.containsKey(activity.getString(R.string.actionCode)) ) {

            if (shopperExist) {
                ((MainActivity) activity).setShopperName(((shopperDao.hasShopper().getShopper().getName() == null
                        || shopperDao.hasShopper().getShopper().getName().equals("")) &&
                        (shopperDao.hasShopper().getShopper().getLname() == null || shopperDao.hasShopper().getShopper().getLname() == "") ? null :
                        shopperDao.hasShopper().getShopper().getName()  + " "
                                +  shopperDao.hasShopper().getShopper().getLname()));

                ((MainActivity) activity).enableNotification(shopperDao.hasShopper().getShopper().getAlertPreference().equalsIgnoreCase("1")
                        ? true: false);
            }

            String cn = extra.getString("CN");
            String[] list ={cn+""};
            Utils.replaceFragment(supportFragmentManager, HomeFragment.newInstance(shopperDao.hasShopper()), fragment_container, false, false);


            //Utils.replaceFragment(supportFragmentManager, OrderStatusFragment.newInstance(list), fragment_container, false, false);
        } else {
            if (shopperExist) {
                ((MainActivity) activity).setShopperName(((shopperDao.hasShopper().getShopper().getName() == null
                        || shopperDao.hasShopper().getShopper().getName().equals("")) &&
                        (shopperDao.hasShopper().getShopper().getLname() == null || shopperDao.hasShopper().getShopper().getLname() == "") ? null :
                        shopperDao.hasShopper().getShopper().getName()  + " "
                        +  shopperDao.hasShopper().getShopper().getLname()));
                if (savedInstanceState == null) {
                    shopper = shopperDao.hasShopper();
                    Utils.replaceFragment(supportFragmentManager, HomeFragment.newInstance(shopperDao.hasShopper()), fragment_container, false, false);
                }

                ((MainActivity) activity).enableNotification(shopperDao.hasShopper().getShopper().getAlertPreference().equalsIgnoreCase("1")
                        ? true: false);

            } else {
                if (savedInstanceState == null)
                    Utils.replaceFragment(supportFragmentManager, DashboardFragment.newInstance(), fragment_container, false, false);

            }
        }




//        });

    }

    public ShopperData getShopper() {
        return this.shopper;
    }

    public void logout(Activity activity) {
//        executor.execute(() -> {
        userRepo.logout(activity, new ServerResponse<Response<GenericResponse>>() {
            @Override
            public void sendData(Response<GenericResponse> response) throws ParseException, JSONException {
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                shopperDao.logout();
                TinyDB tinyDB = TinyDB.getInstance();
                tinyDB.putString(Vals.CUSTOMER_TOKEN, "");

            }
        });
        activity.startActivity(new Intent(activity, SplashActivity.class));
        activity.finish();

//        });

    }

    public void enablenotification(Activity activity, boolean enable) {

       userRepo.Notificationnable(activity, enable, new ServerResponse<Response<GenericResponse>>() {
           @Override
           public void sendData(Response<GenericResponse> response) throws ParseException, JSONException {
               Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               ShopperData shopperData = shopperDao.hasShopper();
               shopperData.getShopper().setAlertPreference(enable ? "1" : "0");
               shopperDao.updateShopper(shopperData);
//               userRepo.login(request, activity, response -> {
//                   LoaderDialog.hideLoader(fragment);
//                   loginResponse = response.body();
//
//                   dataLogin.setValue(loginResponse);
//               });
           }
       });
    }


    public void metadata(Activity activity) {

        if (this.metadata != null) {
            return;
        }
        this.metadata = userRepo.getMetadata(activity);

    }

    public LiveData<MetadataData> getMataData() {
        return this.metadata;
    }
}

