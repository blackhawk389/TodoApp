package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.AddressBookAdapter;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.dailog.SearchListDialog;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.response.myAdresses.MyAddressesData;
import com.app.boxee.shopper.data.response.myAdresses.ShopperAddressItem;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.AddressBookViewModel;
import com.app.boxee.shopper.view_models.LocationViewModel;
//import com.google.android.gms.location.places.Place;
//import com.google.android.libraries.places.compat.ui.PlacePicker;
//import com.google.android.libraries.places.compat.Place;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressesFragment extends Fragment {


    private Env env;
    @BindView(R.id.address_book_rv)
    RecyclerView addressListrv;
    private AddressBookAdapter adapter;
    private ImageView addAddress;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AddressBookViewModel viewModel;
    private String selectedCity;
    private Place place;
    private static final int PLACE_PICKER_REQ_CODE = 2;
    private static final int LOC_REQ_CODE = 1;
    private MetadataData metadata;
    private LocationViewModel viewModel2;

    public MyAddressesFragment() {
        // Required empty public constructor
    }

    public static MyAddressesFragment newInstance(@Nullable String pickup) {

        Bundle args = new Bundle();
        args.putString("type", pickup);
        MyAddressesFragment fragment = new MyAddressesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_addresses, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        addAddress = (ImageView) getActivity().findViewById(R.id.add_address);
        //setRecyclerView();
        setStrings();
        setListner();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    public void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddressBookViewModel.class);
        viewModel.init(getActivity(), this);
        viewModel.getLoginResponse().observe(this, response -> updateUI(response));

        viewModel2 = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel.class);
        viewModel2.init(getActivity());
        viewModel2.getMataData().observe(this, metadataData -> {
            if(metadataData != null) {
                this.metadata = metadataData;
                setRecyclerView();
            }
        });


    }

    public void makedefault(String id) {
        viewModel.makeDefault(id, getActivity(), this);
        viewModel.getdefaultResponse().observe(this, response -> updateList(response));

    }

    private void updateList(GenericResponse response) {
        if (response.getCode() == 200) {
            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
            viewModel.init(getActivity(), this);
            viewModel.getLoginResponse().observe(this, response1 -> updateUI(response1));
        }
    }

    public void updateLocation(ShopperAddressItem shopperAddressItem) {
        FragmentManager manager = getFragmentManager();
        Fragment fr = manager.findFragmentByTag(manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 2).getName());
        getFragmentManager().popBackStack();
        if (fr instanceof MyAddressesFragment.PlaceHelper) {
            if (getArguments().get("type").equals("pickup")) {
                ((OrderDetailsFragment) fr).setPickupPlace(shopperAddressItem);
            } else {
                ((OrderDetailsFragment) fr).setPlace(shopperAddressItem);
            }
        }
//    viewModel.updateLocation(getActivity(), this, new UpdateLocationRequest(consignmentStatus.getConsignmentId(), "incoming", 1, selectedCityid, place.getAddress().toString(), place.getLatLng().latitude, place.getLatLng().longitude, "update_delivery_location", FirebaseMessagingService.getToken(getActivity())));

    }

    public void popBack(){
        getFragmentManager().popBackStack();
    }

    public interface PlaceHelper {
        void setPlace(String place);

        void setPickupPlace(String place);
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);


    }

    private void updateUI(GenericResponse<MyAddressesData> response) {
        //&& response.getData().getShopperAddress().size() > 0
        if (response != null && response.getData().getShopperAddress() != null ) {
            adapter.setList(response.getData().getShopperAddress());
        }
    }

    private void setListner() {
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.replaceFragment(getFragmentManager(), AddAddressFragment.newInstance(null,false), R.id.fragment_container, true, true);
            }
        });
    }

    private void setStrings() {
        if (getArguments().getString("type") == null) {
            ((MainActivity) getActivity()).setBack(true);
        } else {
            ((MainActivity) getActivity()).setBack(true);
        }
        ((MainActivity) getActivity()).changeTitle(env.getAppmyaddressnotfound(), true);
//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
////                selectModeRv.setRotationY(180);
//                addressListrv.setRotationY(180);
//            }
//        }
    }

    private void setRecyclerView() {
        adapter = new AddressBookAdapter(this, addressListrv, env, TinyDB.getInstance(), null, metadata);
        addressListrv.setAdapter(adapter);
    }

    public void deleteAddress(String s) {
        viewModel.deleteAdd(s, getActivity(), this);
        viewModel.deleteResponse().observe(this, response -> updateList(response));
    }

    public void location() {
//        showPlacePicker();
        Utils.replaceFragment(getFragmentManager(), AddAddressFragment.newInstance(null,true), R.id.fragment_container, true, true);

//        viewModel.init(getActivity());
//        viewModel.getMataData().observe(MyAddressesFragment.this, response -> {
//            showDailog(response);
//        });
    }

    private void showDailog(MetadataData response) {

        ArrayList<String> cityStrings = new ArrayList<>();
        if (TinyDB.getInstance().getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
            for (Ar loc : response.getLocations().getAr()) {
                if (loc.getType().equals("city"))
                    cityStrings.add(loc.getName());

            }
        } else {
            for (En loc : response.getLocations().getEn()) {
                if (loc.getType().equals("city"))
                    cityStrings.add(loc.getName());
            }

        }
        SearchListDialog searchListDialog = new SearchListDialog();
        searchListDialog.setList(cityStrings);
        searchListDialog.setCancelable(true);
        searchListDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                LoaderDialog.showLoader(ShipmentDetailsByStatusFragment.this);
                selectedCity = adapterView.getItemAtPosition(i).toString();
                searchListDialog.dismiss();
//                showPlacePicker(adapterView.getItemAtPosition(i).toString());
                LoaderDialog.showLoader(MyAddressesFragment.this);

            }
        });

        searchListDialog.show(getFragmentManager(), null);
    }

    private void showPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


//        if (getLocationFromAddress(getActivity(), s) != null) {
//            LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
//                    getLocationFromAddress(getActivity(), s)
//                    , getLocationFromAddress(getActivity(), s));
//            builder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
//        }
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQ_CODE);
        } catch (Exception e) {
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    void gg(){

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOC_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                viewModel.getMataData().observe(this, response -> {
                    showDailog(response);
                });
//                showPlacePicker(adapterView.getSelectedItem().toString());
            }
        } else if (requestCode == PLACE_PICKER_REQ_CODE) {
            LoaderDialog.hideLoader(MyAddressesFragment.this);
            if (resultCode == RESULT_OK) {
                //place = PlacePicker.getPlace(getActivity(), data);
                Utils.replaceFragment(getFragmentManager(), AddAddressFragment.newInstance(null,false), R.id.fragment_container, true, true);

//                viewModel.getMataData().observe(this, response -> {
//                    if (TinyDB.getInstance().getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
//                        for (int i = 0; i < response.getLocations().getAr().size(); i++) {
//                            if (selectedCity != null && selectedCity.equals(response.getLocations().getAr().get(i).getName()) && response.getLocations().getAr().get(i).getType().equals("city")) {
//                                selectedCityid = response.getLocations().getAr().get(i).getLocationId();
//                                break;
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < response.getLocations().getEn().size(); i++) {
//                            if (selectedCity != null && selectedCity.equals(response.getLocations().getEn().get(i).getName()) && response.getLocations().getEn().get(i).getType().equals("city")) {
//                                selectedCityid = response.getLocations().getEn().get(i).getLocationId();
//                                break;
//                            }
//                        }
//
//                    }
//                    viewModel.updateLocation(getActivity(), ShipmentDetailsByStatusFragment.this, new UpdateLocationRequest(consignmentStatus.getConsignmentId(), "incoming", 1, selectedCityid, place.getAddress().toString(), place.getLatLng().latitude, place.getLatLng().longitude, "update_delivery_location", FirebaseMessagingService.getToken(getActivity())));
//
//                });
//                addressModels = new ArrayList<>();
//                addressModels.add(new AddressModel("From", consignmentStatus.getConsignorAddress()));
//                addressModels.add(new AddressModel("To", place.getAddress().toString()));
//                ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
            }
        }
    }
//    void dfdf(){
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME);
//
//// Use the builder to create a FindCurrentPlaceRequest.
//        FindCurrentPlaceRequest request =
//                FindCurrentPlaceRequest.builder(placeFields).build();
//        Places.initialize(getActivity(), "");
//
//// Create a new Places client instance.
//        PlacesClient placesClient = Places.createClient(getActivity());
//
//// Call findCurrentPlace and handle the response (first check that the user has granted permission).
//        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
//            placeResponse.addOnCompleteListener(task -> {
//                if (task.isSuccessful()){
//                    FindCurrentPlaceResponse response = task.getResult();
//                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//
//                    }
//                } else {
//                    Exception exception = task.getException();
//                    if (exception instanceof ApiException) {
//                        ApiException apiException = (ApiException) exception;
//
//                    }
//                }
//            });
//        } else {
//            // A local method to request required permissions;
//            // See https://developer.android.com/training/permissions/requesting
//
//        }
//    }

}
