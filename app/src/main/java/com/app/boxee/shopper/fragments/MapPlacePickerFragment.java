package com.app.boxee.shopper.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.data.CourierCenter;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.Warehouse;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.LocationViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
public class MapPlacePickerFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener , AdapterView.OnItemSelectedListener {


    private static final int REQUEST_LOCATION = 666;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private boolean firstTime = true;
    private Marker currentLocationMarker;
    private String address;
    @BindView(R.id.address_txt)
    TextView addresstxt;
    @BindView(R.id.select_location)
    Button selectLocation;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LocationViewModel viewModel;
    private Spinner locations;

    public MapPlacePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel.class);
        viewModel.init(getActivity());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            final LocationSettingsStates state = result1.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                                getActivity(), 1000);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    break;
            }
        });
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    buildGoogleApiClient();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    hasLocationPermission();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        LatLng latLng = null;
        if (currentLocationMarker == null) {
            //Place current location marker
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_pickup)).draggable(true));
            address = getCompleteAddressString(latLng.latitude, latLng.longitude);
            addresstxt.setText(address);
        }
        if (latLng != null && firstTime) {
            firstTime = false;

            //move map camera
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng)
                    .zoom(10).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (firstTime) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_location:
                FragmentManager manager = getFragmentManager();
                Fragment fr = manager.findFragmentByTag(manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 2).getName());
                getFragmentManager().popBackStack();
                if (fr instanceof PlaceHelper) {
                    ((PlaceHelper) fr).setPlace(address);
                }
                break;
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
                            updateFilterMarkers(getLocationFromAddress(getActivity(),loc.getName()));
                            break;
                        }
                    }

                } else {
                    //english
                    for (En loc : metadataData.getLocations().getEn()) {
                        if (selectedCity.equals(loc.getName())) {
                            updateFilterMarkers(getLocationFromAddress(getActivity(),loc.getName()));
                            break;
                        }

                    }
                }
                Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            } else {

            }
        });
    }

    private void updateFilterMarkers(LatLng locationFromAddress) {
        mMap.clear();
        currentLocationMarker = mMap.addMarker(new MarkerOptions().position(locationFromAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_pickup)).draggable(true));
        address = getCompleteAddressString(locationFromAddress.latitude, locationFromAddress.longitude);
        addresstxt.setText(address);
        //move map camera
        CameraPosition cameraPosition = new CameraPosition.Builder().target(locationFromAddress)
                .zoom(10).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface PlaceHelper {
        void setPlace(String place);
    }

    public static MapPlacePickerFragment newInstance(@Nullable String location) {
        Bundle args = new Bundle();
        args.putString("address", location);
        MapPlacePickerFragment fragment = new MapPlacePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_place_picker, container, false);
        ButterKnife.bind(this, view);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locations = ((MainActivity) getActivity()).findViewById(R.id.location);
        setStrings();
        setListner();

        return view;
    }

    private void setListner() {
        selectLocation.setOnClickListener(this::onClick);
        locations.setOnItemSelectedListener(this);
    }

    private void setStrings() {
        ((MainActivity) getActivity()).changeTitle("Change Location", false);
        if (getArguments() != null)
            addresstxt.setText(getArguments().get("address") == null ? "" : getArguments().get("address").toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_pickup)).draggable(true));
                address = getCompleteAddressString(latLng.latitude, latLng.longitude);
                addresstxt.setText(address);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                hasLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                address = getCompleteAddressString(arg0.getPosition().latitude, arg0.getPosition().longitude);
                addresstxt.setText(address);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

    }

    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PermissionChecker.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PermissionChecker.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
                return false;
            }
        } else {
            return true;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

    }

}
