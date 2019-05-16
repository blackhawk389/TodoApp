package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.CustomInfoWindowAdapter;
import com.app.boxee.shopper.data.CourierCenter;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.Warehouse;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.LocationViewModel;
import com.app.boxee.shopper.view_models.UserProfileViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    private List<CourierCenter> courierCenterList;
    private ArrayList<LatLng> latLngs;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.main_content)
    RelativeLayout relativeLayout;
    @BindView(R.id.btn_pick)
    RadioButton pickup;
    @BindView(R.id.btn_drop)
    RadioButton dropOff;
    private SupportMapFragment mapFragment;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LocationViewModel viewModel;
    private Spinner locations;
    private En en;
    private Ar ar;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(boolean parentDashboard) {

        Bundle args = new Bundle();
        args.putBoolean("parentDashboard", parentDashboard);
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);
        locations = ((MainActivity) getActivity()).findViewById(R.id.location);
        FragmentManager fm = getChildFragmentManager();

        // Inflate the layout for this fragment
        SupportMapFragment mapFragment = (SupportMapFragment) fm
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setStrings();
        setFonts();
        return view;
    }

    private void setFonts() {
        if(Utils.getCurrentLanguage().equals("ar")){
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");

        }
        else{
//            skipTxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf"));
        }
    }

    private void setStrings() {
        Env env = EnvUtil.getInstance(getActivity());
        if (getArguments() != null && getArguments().getBoolean("parentDashboard")) {
            ((MainActivity) getActivity()).hideAppBar(false);
            ((MainActivity) getActivity()).setBack(true);
            ((MainActivity) getActivity()).changeTitle(env.getAppDashboardClexLocations(), false);
            ((MainActivity) getActivity()).setSpinner(true);
        } else {
            Configuration config = getActivity().getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    //in Right To Left layout
//                selectModeRv.setRotationY(180);
                    //relativeLayout.setRotationY(180);
                }
            }
        }
        pickup.setText(env.getAppLocationsOptions().get(0).getName());
        dropOff.setText(env.getAppLocationsOptions().get(1).getName());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();

    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel.class);
        viewModel.init(getActivity());
    }


    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void listners() {
        radioGroup.setOnCheckedChangeListener(this);
        locations.setOnItemSelectedListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                new Handler().postDelayed(() -> {
                    Uri gmmIntentUri = Uri.parse("https://maps.google.com/maps?daddr=" + marker.getPosition().latitude + "," + marker.getPosition().longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                            mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);


                }, 1000);
            }
        });
        listners();
        setCordinates();
    }

    private void setCordinates() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.btn_pick) {
            viewModel.getMataData().observe(this, metadataData -> {
                updateFilterMarkers(metadataData, en, ar);
            });
        } else {
            viewModel.getMataData().observe(this, metadataData -> {
                updateFilterMarkers(metadataData, en, ar);

            });
        }
    }


    private void updateFilterMarkers(@Nullable MetadataData metadataData, @Nullable En location, @Nullable Ar arLocation) {
        if (metadataData != null) {
            mMap.clear();
            List<Warehouse> warehouses = metadataData.getWarehouse();
            courierCenterList = new ArrayList<>();
            for (Warehouse warehouse : warehouses) {
                try {
                    if (location != null) {
                        if (warehouse.getFkLocation() == location.getLocationId()) {
                            courierCenterList.add(new CourierCenter(warehouse.getFkLocationCity() + "", warehouse.getFkLocation() + "", warehouse.getName(), "+92417454000", Double.parseDouble(warehouse.getLatitude()), Double.parseDouble(warehouse.getLongitude())));
                        }
                    } else if (arLocation != null) {
                        if (warehouse.getFkLocation() == arLocation.getLocationId()) {
                            courierCenterList.add(new CourierCenter(warehouse.getFkLocationCity() + "", warehouse.getFkLocation() + "", warehouse.getName(), "+92417454000", Double.parseDouble(warehouse.getLatitude()), Double.parseDouble(warehouse.getLongitude())));
                        }
                    } else {
                        courierCenterList.add(new CourierCenter(warehouse.getFkLocationCity() + "", warehouse.getFkLocation() + "", warehouse.getName(), "+92417454000", Double.parseDouble(warehouse.getLatitude()), Double.parseDouble(warehouse.getLongitude())));

                    }
                } catch (Exception ex) {

                }
            }
            boolean firstMarker = true;
            latLngs = new ArrayList<>(courierCenterList.size());
            for (CourierCenter courierCenter : courierCenterList) {
                LatLng latLng = new LatLng(courierCenter.getLatitude(), courierCenter.getLongitude());
                latLngs.add(latLng);
                if (radioGroup.getCheckedRadioButtonId() == R.id.btn_pick) {

                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(courierCenter.getAddress())
                            .snippet(getAddressFromLatLng(latLng) + ";" + courierCenter.getPhone())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_pickup)));
                } else {
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(courierCenter.getAddress())
                            .snippet(getAddressFromLatLng(latLng) + ";" + courierCenter.getPhone())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_dropoff)));
                }
                if (firstMarker) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    firstMarker = false;
                }
            }
        }
    }


    private String getAddressFromLatLng(LatLng latLng) {
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            return addressFragments.get(0);
        }
        return null;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (mMap != null) {
            mMap.clear();
            setCordinates();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        viewModel.getMataData().observe(this, metadataData -> {
            if (i != 0) {
                String selectedCity = adapterView.getSelectedItem().toString();
                if (TinyDB.getInstance().getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    //arabic
                    for (Ar loc : metadataData.getLocations().getAr()) {
                        if (selectedCity.equals(loc.getName())) {
                            ar = loc;
                            en = null;
                            updateFilterMarkers(metadataData, en, ar);
                            break;
                        }
                    }

                } else {
                    //english
                    for (En loc : metadataData.getLocations().getEn()) {
                        if (selectedCity.equals(loc.getName())) {
                            en = loc;
                            ar = null;
                            updateFilterMarkers(metadataData, en, ar);
                            break;
                        }

                    }
                }
                Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            } else {
                en = null;
                ar = null;
                updateFilterMarkers(metadataData, en, ar);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
