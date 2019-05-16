package com.app.boxee.shopper.repositories;

import android.app.Activity;
import android.app.FragmentManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;


import com.app.boxee.shopper.R;
import com.app.boxee.shopper.api.MetadataRestAPI;
import com.app.boxee.shopper.api.NodeRestAPI;
import com.app.boxee.shopper.api.RestAPI;
import com.app.boxee.shopper.application.App;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.request.AddAddress.AddAdressRequest;
import com.app.boxee.shopper.data.request.AlternateNumberStepTwoRequest;
import com.app.boxee.shopper.data.request.DeleteRequest;
import com.app.boxee.shopper.data.request.DownloadPDFRequest;
import com.app.boxee.shopper.data.request.AlternateNumberRequest;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.data.request.GetShipmentByIdRequest;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.NumberVerificationRequest;
import com.app.boxee.shopper.data.request.OrderDetailsByStatusRequest;
import com.app.boxee.shopper.data.request.RateAWBRequest;
import com.app.boxee.shopper.data.request.TrackRequest;
import com.app.boxee.shopper.data.request.TrackingRequest;
import com.app.boxee.shopper.data.request.UpdateDateRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.request.UpdatePickupDateRequest;
import com.app.boxee.shopper.data.response.AddAddress.AddAdressResponse;
import com.app.boxee.shopper.data.response.DownloadPDFResponse;
import com.app.boxee.shopper.data.response.Data;
import com.app.boxee.shopper.data.response.GetShipmentByIdResponse;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusList;
import com.app.boxee.shopper.data.response.TrackingResponse;
import com.app.boxee.shopper.data.response.myAdresses.MyAddressesData;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.data.response.TrackResponseData;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.User;
import com.app.boxee.shopper.fragments.LoginFragment;
import com.app.boxee.shopper.network.ServerResponse;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Vals;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ShopperRepository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final RestAPI webservice;
    private final ShopperDao shopperDao;
    private final Executor executor;
    private final TinyDB tinyDB;
    private final NodeRestAPI nodeWebservice;
    private final MetadataRestAPI metadataService;

    @Inject
    public ShopperRepository(RestAPI webservice, ShopperDao shopperDao, Executor executor, TinyDB tinyDB, NodeRestAPI nodeWebservice, MetadataRestAPI metadataRestAPI) {
        this.webservice = webservice;
        this.shopperDao = shopperDao;
        this.executor = executor;
        this.tinyDB = tinyDB;
        this.nodeWebservice = nodeWebservice;
        this.metadataService = metadataRestAPI;
    }


    // ---

    public LiveData<User> getUser(String userLogin) {
        refreshUser(userLogin); // try to refresh data if possible from Github Api
        return shopperDao.load(userLogin); // return a LiveData directly from the database.
    }

    // ---

    private void refreshUser(final String userLogin) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean userExists = (shopperDao.hasUser(userLogin, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
            if (!userExists) {
                webservice.getUser(userLogin).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "MyAddressesData refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            User user = response.body();
                            user.setLastRefresh(new Date());
                            shopperDao.save(user);
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        //Toast.makeText(get,"You are offline, check your internet connection and try again", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public LiveData<MetadataData> getMetadata(Activity activity) {
        refreshMataData(activity); // try to refresh data if possible from Github Api
        return shopperDao.loadMataData();

    }

    private void getMetadata(Activity activity,
                             ServerResponse<Response<GenericResponse<MetadataData>>> responseServerResponse) {
        Call<GenericResponse<MetadataData>> getLocation = metadataService.getMetadata();
        getData(activity, getLocation, responseServerResponse);
    }

    public void getShipmentDetails(TrackRequest request, Activity activity,
                                   ServerResponse<Response<GenericResponse<List<TrackResponseData>>>> responseServerResponse) {
        Call<GenericResponse<List<TrackResponseData>>> getShipmentDetails = nodeWebservice.trackShipment(request);
        getData(activity, getShipmentDetails, responseServerResponse);
    }

    public void login(LoginRequest request, Activity activity,
                      ServerResponse<Response<GenericResponse<LoginResponse>>> responseServerResponse) {
        Call<GenericResponse<LoginResponse>> login = webservice.login(request);
        getData(activity, login, responseServerResponse);
    }

    public void getAddresses(Activity activity, ServerResponse<Response<GenericResponse<MyAddressesData>>> responseServerResponse) {
        Call<GenericResponse<MyAddressesData>> login = webservice.getAddress();
        getData(activity, login, responseServerResponse);
    }

    public void makeDefault(String id, Activity activity,
                            ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> login = webservice.makeDefault(id);
        getData(activity, login, responseServerResponse);
    }

    public void deleteAdd(String id, Activity activity,
                          ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> login = webservice.deleteAdd(id, new DeleteRequest("delete"));
        getData(activity, login, responseServerResponse);
    }

    public void addAddress(AddAdressRequest request, Activity activity,
                           ServerResponse<Response<GenericResponse<AddAdressResponse>>> responseServerResponse) {
        Call<GenericResponse<AddAdressResponse>> login = webservice.addAddress(request);
        getData(activity, login, responseServerResponse);
    }
    public void editAddress(String addId,AddAdressRequest request, Activity activity,
                           ServerResponse<Response<GenericResponse<AddAdressResponse>>> responseServerResponse) {
        Call<GenericResponse<AddAdressResponse>> login = webservice.editAddress(addId,request);
        getData(activity, login, responseServerResponse);
    }

    public void verifyNumber(NumberVerificationRequest request, Activity activity,
                             ServerResponse<Response<GenericResponse<ShopperData>>> responseServerResponse) {
        Call<GenericResponse<ShopperData>> verify = webservice.verify(request);
        getData(activity, verify, responseServerResponse);
    }

    public void getShipmentByPhone(GetShipmentByIdRequest request, Activity activity,
                                   ServerResponse<Response<GenericResponse<GetShipmentByIdResponse>>> responseServerResponse) {

        Call<GenericResponse<GetShipmentByIdResponse>> getShipmentByPhone = webservice.getShipmentsByPhone(request);

        getData(activity, getShipmentByPhone, responseServerResponse);
    }

    public void getOrderDetailsByStatus(OrderDetailsByStatusRequest request, Activity activity,
                                        ServerResponse<Response<GenericResponse<OrderDetailsByStatusList>>> responseServerResponse) {
        Call<GenericResponse<OrderDetailsByStatusList>> getOrderDetailsByStatus = webservice.getOrderDetailsByStatus(request);
        getData(activity, getOrderDetailsByStatus, responseServerResponse);
    }

    public void trackRecord(TrackingRequest request, Activity activity,
                            ServerResponse<Response<GenericResponse<TrackingResponse>>> responseServerResponse) {
        Call<GenericResponse<TrackingResponse>> tracking = webservice.trackListOfCns(request);
        getData(activity, tracking, responseServerResponse);
    }

    public void updateDate(UpdateDateRequest request, Activity activity,
                           ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> updateDate = webservice.updateDate(request);
        getData(activity, updateDate, responseServerResponse);
    }

    public void updateDate(UpdatePickupDateRequest request, Activity activity,
                           ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> updateDate = webservice.updateDate(request);
        getData(activity, updateDate, responseServerResponse);
    }

    public void rateAWB(RateAWBRequest request, Activity activity,
                        ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> updateDate = webservice.rateAWB(request);
        getData(activity, updateDate, responseServerResponse);
    }

    public void downloadPDF(DownloadPDFRequest request, Activity activity,
                            ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse<DownloadPDFResponse>> updateDate = webservice.downloadPDF(request);
        getData(activity, updateDate, responseServerResponse);
    }

    public void updateLocation(UpdateLocationRequest request, Activity activity,
                               ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> updateLocation = webservice.updateLocation(request);
        getData(activity, updateLocation, responseServerResponse);
    }

    public void editProfile(EditProfileRequest request, Activity activity,
                            ServerResponse<Response<GenericResponse<Shopper>>> responseServerResponse) {
        Call<GenericResponse<Shopper>> editProfile = webservice.editProfile(request);
        getData(activity, editProfile, responseServerResponse);
    }

    public void generateTicket(RequestBody request, Activity activity,
                               ServerResponse<Response<GenericResponse>> responseServerResponse) {
        Call<GenericResponse> generateTicket = webservice.generateTicket(request);
        getData(activity, generateTicket, responseServerResponse);
    }


    public void Notificationnable(Activity activity, boolean isenable, ServerResponse<Response<GenericResponse>> responseServerResponse ){
        Call<GenericResponse> enablenotifs = webservice.enableNotification(isenable);
        getData(activity, enablenotifs, responseServerResponse);
    }

    public void logout(Activity activity, ServerResponse<Response<GenericResponse>> responseServerResponse ){
        Call<GenericResponse> enablenotifs = webservice.logout();
        getData(activity, enablenotifs, responseServerResponse);
    }

    public void alternatePhone(AlternateNumberRequest request, Activity activity,
                               ServerResponse<Response<GenericResponse<LoginResponse>>> responseServerResponse) {
        Call<GenericResponse<LoginResponse>> alternatePhone = webservice.alternatePhone(request);
        getData(activity, alternatePhone, responseServerResponse);
    }

    public void alternatePhone2(AlternateNumberStepTwoRequest request, Activity activity,
                                ServerResponse<Response<GenericResponse<Data>>> responseServerResponse) {
        Call<GenericResponse<Data>> alternatePhone = webservice.alternatePhoneStep2(request);
        getData(activity, alternatePhone, responseServerResponse);
    }

    public void getAboutUs(AlternateNumberStepTwoRequest request, Activity activity,
                                ServerResponse<Response<GenericResponse<Data>>> responseServerResponse) {
        Call<GenericResponse<Data>> alternatePhone = webservice.alternatePhoneStep2(request);
        getData(activity, alternatePhone, responseServerResponse);
    }


    private void refreshMataData(Activity activity) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean mataDataExist = (shopperDao.hasMetaData() != null);
            // If user have to be updated
            if (!mataDataExist) {
                getMetadata(activity, response -> {
                    if (response.isSuccessful()) {
                        executor.execute(() -> {
                            shopperDao.addMetaData(response.body().getData());
                        });

                    } else {
                    }
                });
            }
        });
    }

    private void getData(Context activity, Call call, ServerResponse responseServerResponse) {

        call.enqueue(new Callback<GenericResponse>() {
                         @Override
                         public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                             try {
                                 Integer code = 0;
                                 try {
                                     responseServerResponse.sendData(response);
                                 } catch (Exception ex) {
                                     ex.printStackTrace();
                                 }
                                 if (response.body() instanceof GenericResponse) {
                                     code = response.body().getCode();
                                 } else {
                                     JsonObject object = ((JsonObject) ((Object) response.body()));
                                     code = object.get("code").getAsInt();
                                 }
                                 if ((code == 101 || code == 102 || code == 103) && response.body().getError()) {
                                     shopperDao.logout();
                                     tinyDB.putString(Vals.CUSTOMER_TOKEN, "");
//                                     DBController.with((Application) activity.getApplicationContext()).clearUserData();
                                     if (activity instanceof FragmentActivity) {
                                         android.support.v4.app.FragmentManager manager = ((FragmentActivity) activity).getSupportFragmentManager();
                                         manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                         Fragment page = manager.findFragmentById(R.id.fragment_container);
                                         if (!(page instanceof LoginFragment))
                                             manager.beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();

                                     }
                                 }


                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }

                         @Override
                         public void onFailure(Call<GenericResponse> call, Throwable t) {
                             try {
                                 Toast.makeText(activity,t.getMessage(), Toast.LENGTH_SHORT).show();
                                 responseServerResponse.sendData(null);

//                                 android.support.v4.app.FragmentManager manger = ((FragmentActivity) activity).getSupportFragmentManager();
//                                 if (((ServerException) t).getErrorCode() == 101 || ((ServerException) t).getErrorCode() == 102 || ((ServerException) t).getErrorCode() == 103) {
//                                     manger.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                     DBController.with(((FragmentActivity) activity).getApplication()).clearUserData();
//                                     Fragment page = manger.findFragmentById(R.id.fragment);
//                                     if (!(page instanceof LoginFragment))
//                                         manger.beginTransaction().replace(R.id.fragment, new LoginFragment()).commit();
//                                 }
                             } catch (Exception e) {
                                 t.printStackTrace();
                             }
                         }


                     }
        );
    }


    private Date getMaxRefreshTime(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }

    public LiveData<ShopperData> getShopper(Activity activity, Fragment fragment, NumberVerificationRequest request) {
        refreshShopper(activity, fragment, request); // try to refresh data if possible from Github Api
        return shopperDao.loadShopper();

    }

    private void refreshShopper(Activity activity, Fragment fragment, NumberVerificationRequest request) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean shopperExist = (shopperDao.hasShopper() != null);
            // If user have to be updated
            if (!shopperExist) {
                LoaderDialog.showLoader(fragment);
                verifyNumber(request, activity, response -> {
                    LoaderDialog.hideLoader(fragment);
                    if (response.isSuccessful()) {
                        executor.execute(() -> {
                            if (!response.body().getError()) {
                                shopperDao.addShopper(response.body().getData());
                            } else {
                                try {
                                    activity.runOnUiThread((new Runnable() {
                                        public void run() {

                                            LoaderDialog.hideLoader(fragment);
                                            Toast.makeText(fragment.getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }));
                                }catch (Exception ex){

                                }
                            }
                        });

                    } else {
                    }
                });
            }
        });
    }

//    public MutableLiveData<ShopperData> getShopper() {
//        MutableLiveData<ShopperData> shopper = new MutableLiveData<>();
//        executor.execute(() -> {
//          shopper.setValue( shopperDao.hasShopper());
//        });
//        return shopper;
//    }
}
