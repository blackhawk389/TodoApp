package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.CustomAutoCompleteAdapter;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.dailog.SearchListDialog;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.request.AddAddress.AddAdressRequest;
import com.app.boxee.shopper.data.response.AddAddress.AddAdressResponse;
import com.app.boxee.shopper.data.response.myAdresses.ShopperAddressItem;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.LocationViewModel;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

//import com.google.android.libraries.places.compat.ui.PlacePicker;
//import com.google.android.libraries.places.compat.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAddressFragment extends Fragment implements MapPlacePickerFragment.PlaceHelper {

    @BindView(R.id.main_content)
    LinearLayout mainContent;
    @BindView(R.id.location)
    Button location;
    @BindView(R.id.add_address)
    Button addBtn;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @BindView(R.id.country)
    AutoCompleteTextView country;
    @BindView(R.id.city)
    AutoCompleteTextView city;
    @BindView(R.id.area)
    AutoCompleteTextView area;
    @BindView(R.id.nick_name)
    Spinner nickname;
    @BindView(R.id.landmark)
    EditText landmark;
    @BindView(R.id.street)
    EditText street;
    @BindView(R.id.building)
    EditText building;
    @BindView(R.id.apartment)
    EditText apartment;
    private LocationViewModel viewModel;
    private TinyDB tinydb;
    private Env env;
    private MetadataData metadataData;
    private String countrySelected, citySelected, areaSelected;
    private Place place;
    private static final int PLACE_PICKER_REQ_CODE = 2;
    private static final int LOC_REQ_CODE = 1;

    private String selectedCity;
    private ShopperAddressItem address;
    private int selectedCityindex = 0;
    private int selectedCountryindex = 0;
    private int selectedAreaindex = 0;
    private ArrayList<String> listNickname;
    private ArrayList<String> listcity;
    private ArrayList<String> countrylist;
    private ArrayList<String> arealist;
    private boolean pin;
    private boolean pin2 = false;
    private boolean makeOptional = false;
    private double longitude = 0.0f;
    private double latitude = 0.0f;
    ArrayList<String> zonelist = new ArrayList<>();
    int countryId = 0;


    public AddAddressFragment() {
        // Required empty public constructor
    }

    public static AddAddressFragment newInstance(@Nullable ShopperAddressItem s, boolean pinLocation) {

        Bundle args = new Bundle();
        args.putParcelable("addId", s);
        args.putBoolean("pin", pinLocation);
        AddAddressFragment fragment = new AddAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);
        ButterKnife.bind(this, view);
        tinydb = TinyDB.getInstance();
        env = EnvUtil.getInstance(getContext());
        setListners();
        setTextWatchers();
        if (getArguments().getBoolean("pin")) {
            showPlacePicker();
        }
        return view;
    }

    private void setStrings(MetadataData response) {

        ((MainActivity) getActivity()).setBack(true);


        if (getArguments().getParcelable("addId") != null) {
            address = (ShopperAddressItem) getArguments().getParcelable("addId");
            ((MainActivity) getActivity()).changeTitle(env.getAppEditAddress(), true);
            addBtn.setText(env.getAppEditAddress());
            landmark.setText(address.getNearestLandmark() == null ? "" : address.getNearestLandmark() + "");
            street.setText(address.getStreet() == null ? "" :  address.getStreet() + "");
            building.setText(address.getBuildingName() == null ? "" : address.getBuildingName() + "");

            for(int i = 0; i < listNickname.size() ; i++){
//                if(listNickname.get(i).equalsIgnoreCase(address.getTag())){
//                    nickname.setSelection(i);
//                }

                if(address.getTag().equalsIgnoreCase("Home")){
                    nickname.setSelection(0);
                }else{
                    nickname.setSelection(1);
                }

            }

            area.setText(address.getAreaId() == null ? "" : address.getAreaId());
            apartment.setText(address.getApartmentNumber() == null ? "" : address.getApartmentNumber() + "");
            try {
                if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    List<Ar> location = response.getLocations().getAr();
                    for (int i = 0; i < location.size(); i++) {
                        if (location.get(i).getType().equalsIgnoreCase("country") && (location.get(i).getLocationId() == address.getCountryId())) {
                            country.setText(location.get(i).getName());
                            break;
                        }
                    }
                } else {
                    List<En> location = response.getLocations().getEn();
                    for (int i = 0; i < location.size(); i++) {
                        if (location.get(i).getType().equalsIgnoreCase("country") && (location.get(i).getLocationId() == address.getCountryId())) {
                            country.setText(location.get(i).getName());
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    List<Ar> location = response.getLocations().getAr();
                    for (int i = 0; i < location.size(); i++) {
                        if (location.get(i).getType().equalsIgnoreCase("city") && (location.get(i).getLocationId() == address.getCityId())) {
                            city.setText(location.get(i).getName());
                            break;
                        }
                    }
                } else {
                    List<En> location = response.getLocations().getEn();
                    for (int i = 0; i < location.size(); i++) {
                        if (location.get(i).getType().equalsIgnoreCase("city") && (location.get(i).getLocationId() == address.getCityId())) {
                            city.setText(location.get(i).getName());
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            ((MainActivity) getActivity()).changeTitle(env.getAppAddressAdd(), true);
        }
        Configuration config = getActivity().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                //in Right To Left layout
//                selectModeRv.setRotationY(180);
               // mainContent.setRotationY(180);
            }
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();

    }

    private boolean allFilled() {
//        if(!isValidCity()){
//            return false;
//        }
        if(makeOptional){
            return true;
        }
        if(pin2){
            if (country.getText().toString().trim().length() == 0 || city.getText().toString().trim().length() == 0) {
                return false;
            }
        }else {
            if ((country.getText().toString().trim().length() == 0 || city.getText().toString().trim().length() == 0 || area.getText().toString().trim().length() == 0)
                    || (!isValidCity())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCity(){
        if(listcity.contains(city.getText().toString())){
            return true;
        }
        return false;
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel.class);
        viewModel.init(getActivity());
        viewModel.getMataData().observe(this, metadataData -> {
//            updateFilterMarkers(metadataData, en, ar);
            setSppiners(metadataData);
            setStrings(metadataData);
            this.metadataData = metadataData;
        });
    }

    private void chkspinner(String type, int id, int index) {
        if (address != null) {
            if (type.equals("city")) {
                if (address.getCityId() == id) {
                    selectedCityindex = index;
                }

            } else if (type.equals("country")) {
                if (address.getCountryId() == id) {
                    selectedCountryindex = index;
                }
        }

        }

    }

    private void setSppiners(MetadataData response) {
        listcity = new ArrayList<>();
        listcity.add("Select city");
        if (response != null) {

            //Nickname spinner populate
            try {
                if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    listNickname = new ArrayList<>();
                   // listNickname.add("Select nickname");

                    listNickname.add(env.getAppHomeLable());
                    listNickname.add(env.getAppWork());

//                    for (Ar loc : location) {

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.sipinner_drop_down_list_black, listNickname) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTextSize(15);
                            v.setPadding(90, 0, 90, 15);
                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);
                            ((TextView) v).setGravity(Gravity.CENTER);
                            return v;
                        }
                    };
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    nickname.setAdapter(spinnerAdapter);

                } else {
                    listNickname = new ArrayList<>();
                   // listNickname.add("Select nickname");

                    listNickname.add(env.getAppHomeLable());
                    listNickname.add(env.getAppWork());

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.sipinner_drop_down_list_black, listNickname) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTextSize(15);
                            v.setPadding(90, 0, 90, 15);
                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }
                    };

                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nickname.setAdapter(spinnerAdapter);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //country spinner populate
            try {
                if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    List<Ar> location = response.getLocations().getAr();
                    countrylist = new ArrayList<>();
//                    countrylist.add("Select country");
                    for (int i = 0; i < location.size(); i++) {
                        Ar loc = location.get(i);
                        if (loc.getType().equals("country")) {
                            countrylist.add(loc.getName());
                            if(loc.getName().equalsIgnoreCase(env.getSaudiaarab())){
                                countryId = loc.getLocationId();
                            }
//                            chkspinner("country", loc.getLocationId(), countrylist.size() - 1);
                        }
                    }

                    String[] countryArray = countrylist.toArray(new String[countrylist.size()]);
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countryArray);

                    country.setAdapter(adapter);
                    country.setThreshold(1);//start searching from 1 character
                    for(int i = 0; i < countrylist.size(); i++){
                        if(countrylist.get(i).equalsIgnoreCase(env.getSaudiaarab())){
                            country.setSelection(i);
                            country.setText(countrylist.get(i));
                        }
                    }


                } else {
                    List<En> location = response.getLocations().getEn();
                    countrylist = new ArrayList<>();
//                    countrylist.add("Select country");
                    for (int i = 0; i < location.size(); i++) {
                        En loc = location.get(i);
                        if (loc.getType().equals("country")) {
                            countrylist.add(loc.getName());
                            if(loc.getName().equalsIgnoreCase(env.getSaudiaarab())){
                                countryId = loc.getLocationId();
                            }
//                            chkspinner("country", loc.getLocationId(), countrylist.size() - 1);
                        }

                    }
                    String[] countryArray = countrylist.toArray(new String[countrylist.size()]);
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countryArray);

                    country.setAdapter(adapter);
                    country.setThreshold(1);//start searching from 1 character

                    for(int i = 0; i < countrylist.size(); i++){
                        if(countrylist.get(i).equalsIgnoreCase(env.getSaudiaarab())){
                            country.setSelection(i);
                            country.setText(countrylist.get(i));
                        }
                    }


                    for (int i = 0; i < location.size(); i++) {
                        if (location.get(i).getIdParent().equals(countryId))
                            zonelist.add(location.get(i).getLocationId() + "");
                    }


                    for (int i = 0; i < location.size(); i++) {
                        En loc = location.get(i);
                        if (loc.getType().equals("city") && zonelist.contains(loc.getIdParent() + "")) {
                            listcity.add(loc.getName());
                        }

                    }


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //city spinner populate
            try {
                if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    List<Ar> location = response.getLocations().getAr();
                    listcity = new ArrayList<>();


                    for (int i = 0; i < location.size(); i++) {
                        Ar loc = location.get(i);
                        if (loc.getType().equals("city")) {
                            listcity.add(loc.getName());
                            chkspinner("city", loc.getLocationId(), listcity.size() - 1);
                        }
                   }
                    String[] cityArray = listcity.toArray(new String[listcity.size()]);
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cityArray);

                    city.setAdapter(adapter);
                    city.setThreshold(1);//start searching from 1 character


                } else {
                   // List<En> location = response.getLocations().getEn();
                   // listcity = new ArrayList<>();
//                    listcity.add("Select city");
//                    for (int i = 0; i < location.size(); i++) {
//                        En loc = location.get(i);
//                        if (loc.getType().equals("city")) {
//                            listcity.add(loc.getName());
//                            chkspinner("city", loc.getLocationId(), listcity.size() - 1);
//                        }
//                    }
                    String[] cityArray = listcity.toArray(new String[listcity.size()]);
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cityArray);

                    city.setAdapter(adapter);
                    city.setThreshold(1);//start searching from 1 character

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //area spinner populate
            try {
                if (address != null) {
                    country.setSelection(selectedCountryindex);
                    city.setSelection(selectedCityindex);
                    area.setSelection(selectedAreaindex);

                }

            } catch (Exception ex) {

            }

            area.setHint(env.getAppAddressarea());
        }
    }


    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void setListners() {

        country.setHint(env.getAppAddAddressCountry());
        city.setHint(env.getAppAddAddressCity());
        landmark.setHint(env.getAppAddnesarestlandmark());
        street.setHint(env.getAppAddstreetname());
        building.setHint(env.getAppAddbuildingname());
        apartment.setHint(env.getAppAddapartment());
        location.setText(env.getApppinlocation());
        addBtn.setText(env.getAppAddressAdd());



        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlacePicker();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFilled()) {
                    int areaId = 0;
                    int cityId = 0;
                    try {
                        if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                            List<Ar> location = metadataData.getLocations().getAr();
                            for (Ar loc : location) {
                                if (loc.getType().equals("area") && loc.getName().equals(areaSelected)) {
                                    areaId = loc.getLocationId();

                                } else if (loc.getType().equals("city") && loc.getName().equals(citySelected)) {
                                    cityId = loc.getLocationId();


                                } else if (loc.getType().equals("country") && loc.getName().equals(countrySelected)) {
                                    countryId = loc.getLocationId();

                                }
                            }

                        } else {
                            List<En> location = metadataData.getLocations().getEn();
                            for (En loc : location) {
                                if (loc.getType().equals("area") && loc.getName().equals(areaSelected)) {
                                    areaId = loc.getLocationId();

                                } else if (loc.getType().equals("city") && loc.getName().equals(citySelected)) {
                                    cityId = loc.getLocationId();


                                } else if (loc.getType().equals("country") && loc.getName().equals(countrySelected)) {
                                    countryId = loc.getLocationId();
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (getArguments().getParcelable("addId") != null) {
                        viewModel.editAddress(getActivity(), address.getAddressId() + "", AddAddressFragment.this,
                                new AddAdressRequest(building.getText().toString().trim(), apartment.getText().toString().trim(), street.getText().toString().trim(), "add",
                                        nickname.getSelectedItem().toString(), area.getText().toString(), landmark.getText().toString().trim(), String.valueOf(latitude), String.valueOf(longitude), countryId, cityId));
                        viewModel.getAddResponse().observe(getActivity(), metadataData -> {
                            update(metadataData);
                        });
                    } else {
                        viewModel.addAddress(getActivity(), AddAddressFragment.this,
                                new AddAdressRequest(building.getText().toString().trim(), apartment.getText().toString().trim(), street.getText().toString().trim(), "add",
                                        nickname.getSelectedItem().toString(), area.getText().toString(), landmark.getText().toString().trim(), String.valueOf(latitude), String.valueOf(longitude), countryId, cityId));
                        viewModel.getAddResponse().observe(getActivity(), metadataData -> {
                            update(metadataData);
                        });
                    }
                } else {
                   selected();
                }
            }
        });
    }

    private void selected() {
         if (country.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity(), "country can not be empty", Toast.LENGTH_SHORT).show();
        }else if( city.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity(), env.getAppaddaddress(), Toast.LENGTH_SHORT).show();
        } else if(  area.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity(), env.getAreamsge(), Toast.LENGTH_SHORT).show();
        }else if(!listcity.contains(city.getText().toString().trim())){
             Toast.makeText(getActivity(), "select right city", Toast.LENGTH_SHORT).show();
         }
    }

    private void setTextWatchers() {
//        area.setEnabled(false);
//        city.setEnabled(false);

        country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                countrySelected = s.toString();
                city.setEnabled(true);
                if (!pin) {
                    try {
                        if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                            List<Ar> location = metadataData.getLocations().getAr();
                            listcity = new ArrayList<>();
                            List<String>  zonelist = new ArrayList<>();
                            Integer countryId = 0;
                            for (int i = 0; i < location.size(); i++) {
                                if (location.get(i).getName().equals(countrySelected) && location.get(i).getType().equals("country")) {
                                    countryId = location.get(i).getLocationId();
                                    break;
                                }

                            }
                            for (int i = 0; i < location.size(); i++) {
                                if (location.get(i).getIdParent().equals(countryId) || location.get(i).getType().equals("zone"))
                                    zonelist.add(location.get(i).getLocationId() + "");
                            }
                            for (int i = 0; i < location.size(); i++) {
                                Ar loc = location.get(i);
                                if (loc.getType().equals("city") && zonelist.contains(loc.getIdParent() + "")) {
                                    listcity.add(loc.getName());
//                                    chkspinner("area", loc.getLocationId(), list.size() - 1);
                                }

                            }
                            String[] cityArray = listcity.toArray(new String[listcity.size()]);
                            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cityArray);

                            city.setAdapter(adapter);
                            city.setThreshold(1);//start searching from 1 character


                        } else {
                            List<En> location = metadataData.getLocations().getEn();
                            listcity = new ArrayList<>();
                            List<String> zonelist = new ArrayList<>();
                            Integer countryId = 0;
                            for (int i = 0; i < location.size(); i++) {
                                if (location.get(i).getName().equals(countrySelected) && location.get(i).getType().equals("country")) {
                                    countryId = location.get(i).getLocationId();
                                    break;
                                }

                            }
                            for (int i = 0; i < location.size(); i++) {
                                if (location.get(i).getIdParent().equals(countryId) && location.get(i).getType().equals("zone"))
                                    zonelist.add(location.get(i).getLocationId() + "");
                            }
                            for (int i = 0; i < location.size(); i++) {
                                En loc = location.get(i);
                                if (loc.getType().equals("city") && zonelist.contains(loc.getIdParent() + "")) {
                                    listcity.add(loc.getName());
//                                    chkspinner("area", loc.getLocationId(), list.size() - 1);
                                }

                            }

                            String[] cityArray = listcity.toArray(new String[listcity.size()]);
                            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cityArray);

                            city.setAdapter(adapter);
                            city.setThreshold(1);//start searching from 1 character

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                citySelected = s.toString();
                area.setEnabled(true);
                try {
                    if (!pin) {
                        if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                            List<Ar> location = metadataData.getLocations().getAr();
                            arealist = new ArrayList<>();
//                            List<String> zonelist = new ArrayList<>();
                            Integer cityId = 0;
//                            arealist.add("Select area");
                            for (int i = 0; i < location.size(); i++) {
                                if (location.get(i).getName().equals(citySelected) && location.get(i).getType().equals("city")) {
                                    cityId = location.get(i).getLocationId();
                                    break;
                                }

                            }

//                            for (int i = 0; i < location.size(); i++) {
//                                Ar loc = location.get(i);
//                                if (loc.getType().equals("area") && cityId.equals(loc.getIdParent())) {
//                                    arealist.add(loc.getName());
////                                    chkspinner("area", loc.getLocationId(), list.size() - 1);
//                                }
//
//                            }


                        } else {
                            List<En> location = metadataData.getLocations().getEn();
                            arealist = new ArrayList<>();
//                            List<String> zonelist = new ArrayList<>();
                            Integer cityId = 0;
                            arealist.add("Select area");
                            for (int i = 0; i < location.size(); i++) {
                                if (location.get(i).getName().equals(citySelected) && location.get(i).getType().equals("city")) {
                                    cityId = location.get(i).getLocationId();
                                    break;
                                }

                            }

//                            for (int i = 0; i < location.size(); i++) {
//                                En loc = location.get(i);
//                                if (loc.getType().equals("area") && cityId.equals(loc.getIdParent())) {
//                                    arealist.add(loc.getName());
////                                    chkspinner("area", loc.getLocationId(), list.size() - 1);
//                                }
//
//                            }

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                areaSelected = s.toString();
            }
        });

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
                LoaderDialog.showLoader(AddAddressFragment.this);

//                showPlacePicker(adapterView.getItemAtPosition(i).toString());


            }
        });

        searchListDialog.show(getFragmentManager(), null);
    }

    private void showPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQ_CODE);
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Integer countryId =0;

        if (requestCode == LOC_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                viewModel.getMataData().observe(this, response -> {
                    showDailog(response);
                });
//                showPlacePicker(adapterView.getSelectedItem().toString());
            }
        } else if (requestCode == PLACE_PICKER_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                pin = true;
                makeOptional = true;
                LoaderDialog.hideLoader(AddAddressFragment.this);

                place = PlacePicker.getPlace(getActivity(), data);
                try {
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;
                    Geocoder geocoder = new Geocoder(getActivity());
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getPremises(); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String citystr = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String countrystr = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    String nearest = addresses.get(0).getSubLocality();
                    String streetstr = addresses.get(0).getThoroughfare();
//                    String apartmentstr = addresses.get(0).get();
//                    nickname.setText(knownName == null ? "" : knownName);
                    landmark.setText(nearest == null ? "" : nearest);
                    street.setText(streetstr == null ? place.getAddress() : streetstr);
                    building.setText(address == null ? "" : address);
//                    apartment.setText(apartmentstr == null ? "" : apartmentstr);
                    if (!tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                        for (int i = 0; i < metadataData.getLocations().getEn().size(); i++) {
                            if (metadataData.getLocations().getEn().get(i).getName().equalsIgnoreCase(countrystr) && metadataData.getLocations().getEn().get(i).getType().equals("country")) {
                                if (countrylist.contains(metadataData.getLocations().getEn().get(i).getName())) {
                                    selectedCountryindex = countrylist.indexOf(metadataData.getLocations().getEn().get(i).getName());
                                    countryId = metadataData.getLocations().getEn().get(i).getLocationId();
                                }
                            } else if (metadataData.getLocations().getEn().get(i).getName().equalsIgnoreCase(citystr) && (metadataData.getLocations().getEn().get(i).getType().equals("city") ||
                                    metadataData.getLocations().getEn().get(i).getType().equals("zone"))) {
                                if (listcity.contains(citystr)) {
                                    selectedCityindex = listcity.indexOf(citystr);
                                    city.setText(citystr);
                                }else{
                                    city.setText("");
                                    selectedCityindex = 0;
                                }
                            }

                            else if (metadataData.getLocations().getAr().get(i).getName().equalsIgnoreCase(state) && metadataData.getLocations().getEn().get(i).getType().equals("state")) {
                                if (arealist.contains(metadataData.getLocations().getEn().get(i).getName())) {
                                    selectedAreaindex = arealist.indexOf(metadataData.getLocations().getEn().get(i).getName());
                                }
                            }
                        }

                    } else {
                        for (int i = 0; i < metadataData.getLocations().getAr().size(); i++) {
                            if (metadataData.getLocations().getAr().get(i).getName().equalsIgnoreCase(countrystr) && metadataData.getLocations().getAr().get(i).getType().equals("country")) {
                                if (countrylist.contains(metadataData.getLocations().getAr().get(i).getName())) {
                                    selectedCountryindex = countrylist.indexOf(metadataData.getLocations().getAr().get(i).getName());
                                }
                            } else if (metadataData.getLocations().getAr().get(i).getName().equalsIgnoreCase(citystr) && metadataData.getLocations().getAr().get(i).getType().equals("city")) {
                                if (listcity.contains(metadataData.getLocations().getAr().get(i).getName())) {
                                    selectedCityindex = listcity.indexOf(metadataData.getLocations().getAr().get(i).getName());
                                }
                            } else if (metadataData.getLocations().getAr().get(i).getName().equalsIgnoreCase(state) && metadataData.getLocations().getAr().get(i).getType().equals("state")) {
                                if (arealist.contains(metadataData.getLocations().getAr().get(i).getName())) {
                                    selectedAreaindex = arealist.indexOf(metadataData.getLocations().getAr().get(i).getName());
                                }
                            }
                        }
                    }
                    country.setSelection(selectedCountryindex);
                    city.setSelection(selectedCityindex);
//                    area.setSelection(selectedAreaindex);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getActivity(), ex + "", Toast.LENGTH_SHORT);
                }
                pin = false;
                pin2 = true;

            }
        }
    }

    private void update(GenericResponse<AddAdressResponse> metadataData) {
        //&& metadataData.getData().getShopperAddress() != null
        if (metadataData.getData() != null ) {
            getFragmentManager().popBackStack();
        }
    }


    @Override
    public void setPlace(String place) {

    }


}
