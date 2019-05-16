package com.app.boxee.shopper.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.SplashActivity;
import com.app.boxee.shopper.adapter.TimeLineAddressAdapter;
import com.app.boxee.shopper.dailog.SearchListDialog;
import com.app.boxee.shopper.data.AddressModel;
import com.app.boxee.shopper.data.AppLocationsOption;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.Orientation;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.request.DownloadPDFRequest;
import com.app.boxee.shopper.data.request.RateAWBRequest;
import com.app.boxee.shopper.data.request.UpdateDateRequest;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.request.UpdatePickupDateRequest;
import com.app.boxee.shopper.data.response.AddAddress.ShopperAddress;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.myAdresses.ShopperAddressItem;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.ShipmentDetailsViewModel;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.android.libraries.places.compat.ui.PlacePicker;
//import com.google.android.libraries.places.compat.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipmentDetailsByStatusFragment extends Fragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    @BindView(R.id.recyclerViewAddress)
    RecyclerView recyclerView;
    @BindView(R.id.barcode)
    TextView barcode;
    @BindView(R.id.expected_date_txt)
    TextView datetxt;
    @BindView(R.id.expected_time_txt)
    TextView expectedtimetxt;
    @BindView(R.id.expected_date_value)
    TextView date;
    @BindView(R.id.webshop_value)
    TextView webshop;
    @BindView(R.id.deparment_Status_txt)
    TextView statusTxt;
    @BindView(R.id.rating_txt)
    TextView ratingtxt;
    @BindView(R.id.department_status_value)
    TextView status;
    @BindView(R.id.amount_value)
    TextView amount;
    @BindView(R.id.amount_txt)
    TextView amounttxt;
    @BindView(R.id.cash_type)
    TextView paymentType;
    @BindView(R.id.main_content)
    NestedScrollView relativelayout;
    @BindView(R.id.card_payment)
    Button paymentCrad;
    @BindView(R.id.generateTicket)
    Button generateTicket;
    @BindView(R.id.downloadPDF)
    ImageView downloadPDF;
    @BindView(R.id.btn_delivery_date)
    Button changeDeliveryDate;
    @BindView(R.id.btn_locations)
    Button changeLocation;
    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.rating_layout)
    LinearLayout ratingLayout;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ShipmentDetailsViewModel viewModel;
    public static final String TAG = "PlacePickerActivity";
    private static final int LOC_REQ_CODE = 1;
    private static final int PLACE_PICKER_REQ_CODE = 2;
    private TimeLineAddressAdapter mTimeLineAdapter;
    private ArrayList<AddressModel> addressModels;
    private Consignment consignmentStatus;
    private SelectModel homeStatus;
    private Env env;
    private List<AppLocationsOption> selectionList;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int msec;
    private String date_str;
    private final static int PLACE_PICKER_REQUEST = 999;
    private Place place;
    private String selectedCity;
    private Integer selectedCityid;
    private List<AppLocationsOption> selectionListOutgoing;
    public static ShopperAddressItem shopperAddressItem;
    private ShopperAddressItem updatedPlaceObject;

    public ShipmentDetailsByStatusFragment() {
        // Required empty public constructor
    }

    public static ShipmentDetailsByStatusFragment newInstance(Consignment consignment_order_status, SelectModel homeStatus, @Nullable ShopperAddressItem addressItem) {

        Bundle args = new Bundle();
        args.putParcelable("consignment_status", consignment_order_status);
        args.putParcelable("home_status", homeStatus);
        args.putParcelable("address", addressItem);
        shopperAddressItem = addressItem;
        ShipmentDetailsByStatusFragment fragment = new ShipmentDetailsByStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_details_by_status, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViews();
        setStrings();
        listeners();
        return view;
    }


    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");


        }
    }

    private void setViews() {
        if (getArguments() != null) {
            consignmentStatus = (Consignment) getArguments().getParcelable("consignment_status");
            homeStatus = (SelectModel) getArguments().getParcelable("home_status");
        }
        setButtonsViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAddressAdapter(addressModels
                , Orientation.VERTICAL, false, env);
        recyclerView.setAdapter(mTimeLineAdapter);
        if (consignmentStatus != null) {
            createModelAddress(consignmentStatus);
        }
//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
//                relativelayout.setRotationY(180);
//            }
//        }
        if (shopperAddressItem != null) {
            if (homeStatus.getMode().equalsIgnoreCase("incoming")) {
                datetxt.setText(env.getAppShipmentDetailsDeliveryDate());
                setPlace(shopperAddressItem);
            } else {
                setPickupPlace(shopperAddressItem);
                datetxt.setText(env.getApppickupdate());
            }
        }


//        if(list.get(position).getIsPaid() == 1){
//            dataHolder.prepaid.setText("Paid");
//
//        }else{
//            dataHolder.prepaid.setText(list.get(position).getAmount()+"");
//
//        }
    }

    private void setButtonsViews() {
        selectionList = env.getAppHomeOptionsIncoming();
        selectionListOutgoing = env.getAppHomeOptionsOutgoing();
        amounttxt.setText(env.getAppShipmentDetailsAmount());
        statusTxt.setText(env.getAppShipmentDetailsStatus());
        expectedtimetxt.setText(env.getAppOrderStatusWebshop());

        generateTicket.setText(env.getAppenerateticket());

        if(!homeStatus.getMode().equalsIgnoreCase("incoming")) {

            changeLocation.setText(env.getAppbtnpickuplcationpickup());
            changeDeliveryDate.setText(env.getAppbtnpickuplcationdate());
        }else{
            changeLocation.setText(env.getAppbtnpickuplcation());
            changeDeliveryDate.setText(env.getAppShipmentDetailsBtnChangeDeliveryDate());
        }

        if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName())) {
            //inpreparation by webshop
            //changeDeliveryDate.setVisibility(View.VISIBLE);
            changeLocation.setVisibility(View.VISIBLE);

            //acc to requirement
            generateTicket.setVisibility(View.VISIBLE);
            if(homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName())) {
                changeDeliveryDate.setVisibility(View.VISIBLE);
            }
            generateTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.replaceFragment(getParentFragment().getFragmentManager(), TicketFragment.newInstance("" + consignmentStatus.getConsignmentId()), R.id.fragment_container, true, true);
                }
            });

        } else if(homeStatus.getStatus().equals(selectionList.get(0).getName())) {

            changeLocation.setVisibility(View.VISIBLE);
            generateTicket.setVisibility(View.VISIBLE);

            generateTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(env.getAppbtnpickupcontact());
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env. getAppOk(), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                    // Change the alert dialog buttons text and background color
                    positiveButton.setTextColor(Color.parseColor("#0044B8"));
                }
            });



        } else if(homeStatus.getStatus().equals(selectionList.get(1).getName()) || homeStatus.getStatus().equals(selectionList.get(4).getName())
       || homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())){
            changeDeliveryDate.setVisibility(View.VISIBLE);
            changeLocation.setVisibility(View.VISIBLE);
            generateTicket.setVisibility(View.VISIBLE);

            generateTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.replaceFragment(getParentFragment().getFragmentManager(), TicketFragment.newInstance("" + consignmentStatus.getConsignmentId()), R.id.fragment_container, true, true);
                }
            });

        }
         else if (homeStatus.getStatus().equals(selectionList.get(3).getName())) {
            //history
            ratingLayout.setVisibility(View.VISIBLE);
            generateTicket.setVisibility(View.VISIBLE);
            generateTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.replaceFragment(getParentFragment().getFragmentManager(), TicketFragment.newInstance("" + consignmentStatus.getConsignmentId()), R.id.fragment_container, true, true);
                }
            });
        } else if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName()) || homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())) {
            changeLocation.setVisibility(View.VISIBLE);
            changeLocation.setText(env.getAppbtnpickuplcation());
            changeDeliveryDate.setVisibility(View.VISIBLE);
            changeDeliveryDate.setText(env.getAppbtnpickuplcationdate());
            downloadPDF.setVisibility(View.VISIBLE);
        } else if (homeStatus.getStatus().equals(selectionListOutgoing.get(3).getName())){

            generateTicket.setVisibility(View.VISIBLE);
            generateTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.replaceFragment(getParentFragment().getFragmentManager(), TicketFragment.newInstance("" + consignmentStatus.getConsignmentId()), R.id.fragment_container, true, true);
                }
            });
//            generateTicket.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                    alertDialog.setCancelable(false);
//                    alertDialog.setMessage("Contact Shipper");
//                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.dismiss();
//                        }
//                    });
//                    alertDialog.show();
//                    Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//
//                    // Change the alert dialog buttons text and background color
//                    positiveButton.setTextColor(Color.parseColor("#0044B8"));
//                }
//            });
        } else if (homeStatus.getStatus().equals(selectionList.get(5).getName())){
            generateTicket.setVisibility(View.VISIBLE);
            generateTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(env.getAppbtnpickupcontact());
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env.getAppOk(), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                    // Change the alert dialog buttons text and background color
                    positiveButton.setTextColor(Color.parseColor("#0044B8"));
                }
            });

        }else{
            //all buttons visibilities gone
            ratingLayout.setVisibility(View.GONE);
            downloadPDF.setVisibility(View.GONE);
            generateTicket.setVisibility(View.GONE);
            changeLocation.setVisibility(View.GONE);
            changeDeliveryDate.setVisibility(View.GONE);
            paymentCrad.setVisibility(View.GONE);
        }

        if (homeStatus.getMode().equalsIgnoreCase("incoming")) {
            datetxt.setText(env.getAppShipmentDetailsDeliveryDate());
        } else {
            datetxt.setText(env.getApppickupdate());
        }


    }

    private void createModelAddress(@Nullable Consignment respose) {
        if (respose != null) {
            addressModels = new ArrayList<>();
            addressModels.add(new AddressModel(env.getAppShipmentDetailsFrom(),respose.getConsignorAddress()));
            addressModels.add(new AddressModel(env.getAppShipmentDetailsTo(), respose.getConsigneeAddress()));
            ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();

    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShipmentDetailsViewModel.class);
        env = EnvUtil.getInstance(getActivity());
        viewModel.init(getActivity(), env);
    }

    private void listeners() {
        downloadPDF.setOnClickListener(this::onClick);
        changeDeliveryDate.setOnClickListener(this::onClick);
        changeLocation.setOnClickListener(this::onClick);
        ratingBar.setOnRatingBarChangeListener(this::onRatingChanged);
       // generateTicket.setOnClickListener(this::onClick);
    }

    private void setStrings() {
        ratingtxt.setText(env.getSaudiaarabrateservice());
        if (consignmentStatus.getRating() != null) {
            ratingBar.setRating(Integer.parseInt(consignmentStatus.getRating()));
        }
        barcode.setText(consignmentStatus.getConsignmentId() + "");
//        if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName()) ||
//                homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())) {
//            date.setText(consignmentStatus.getReturnPickupDate() == null ? "YYYY-MM-DD" : consignmentStatus.getReturnPickupDate());
//        } else {
//            date.setText(consignmentStatus.getExpectedDeliveryDate() == null ? "YYYY-MM-DD" : consignmentStatus.getExpectedDeliveryDate());
//        }

        if (homeStatus.getMode().equalsIgnoreCase("outgoing") && consignmentStatus.getReturnPickupDate() != null) {
            date.setText(consignmentStatus.getReturnPickupDate() == null ? "YYYY-MM-DD" : consignmentStatus.getReturnPickupDate());
        } else {
            date.setText(consignmentStatus.getExpectedDeliveryDate() == null ? "YYYY-MM-DD" : consignmentStatus.getExpectedDeliveryDate());
        }

        if(homeStatus.getStatus().equalsIgnoreCase(env.getAppReturnHistory())){


                date.setText(consignmentStatus.getPopAt() == null ? "--" :consignmentStatus.getPopAt().split(" ")[0]);
//                date.setText(consignmentStatus.getPopAt() == null ?consignmentStatus.getReturnPickupDate() :
//                        consignmentStatus.getPopAt().split(" ")[0]);
           // }
//            date.setText(consignmentStatus.getPodAt()  == null ? consignmentStatus.getReturnPickupDate() == null ?
//                    consignmentStatus.getExpectedDeliveryDate() : consignmentStatus.getReturnPickupDate() : consignmentStatus.getExpectedDeliveryDate());
//            dataHolder.deliveryDatetxt.setText(env.getAppOrderStatusDeliveryDate());
//            dataHolder.deliveryDate.setText(list.get(position).getPopAt() == null ? list.get(position).getDeliveryDate() == null ?
//                    list.get(position).getReturnPickupDate() : list.get(position).getDeliveryDate() : list.get(position).getReturnPickupDate());
//                dataHolder.expectedtxt2.setText("Return Date");
//                dataHolder.expectedvalue2.setText(list.get(position).getReturnPickupDate() + "");
        }



        webshop.setText(consignmentStatus.getWebshopName() == null ? "" : consignmentStatus.getWebshopName());
        if(!Utils.getCurrentLanguage().equalsIgnoreCase("ar")) {
            status.setText(consignmentStatus.getConsignmentActiivity() + "");
        }else{
            status.setText(consignmentStatus.getConsignmentActiivityAr() + "");
        }
       // status.setText(consignmentStatus.getConsignmentActiivity());
        if(consignmentStatus.getPaymentType().equalsIgnoreCase("pre paid")){
            amount.setText(env.getAppprepaid());
            paymentType.setText(env.getAppprepaid());
//            paymentType.setVisibility(View.VISIBLE);
        }else{
            amount.setText("SAR " + consignmentStatus.getAmount());
            paymentType.setText(consignmentStatus.getPaymentType());

            if(homeStatus.getMode().equalsIgnoreCase("incoming")) {
                if (consignmentStatus.getIsPaid() == 1) {
                    paymentType.setText(env.getApppaid());
                } else if (consignmentStatus.getPaymentType().equalsIgnoreCase("pre paid")) {
                    paymentType.setText(env.getAppprepaid());
                } else {
                    paymentType.setText(consignmentStatus.getPaymentType());
                }
            }
        }

        if(homeStatus.getStatus().equalsIgnoreCase("returned to shipper") || homeStatus.getStatus().equalsIgnoreCase("تم ارجاع الشحنة إلى المرسل")){
            datetxt.setText(env.getApporderstatus());
            date.setText(consignmentStatus.getReturnPickupDate()  == null ? "YYYY-MM-DD" : consignmentStatus.getReturnPickupDate() + "");
        }
//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
////                selectModeRv.setRotationY(180);
//                relativelayout.setRotationY(180);
//            }
//        }
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadPDF:
                if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName()) || homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())) {
                    viewModel.downloadPDF(getActivity(), ShipmentDetailsByStatusFragment.this,
                            new DownloadPDFRequest("" + consignmentStatus.getConsignmentId(), FirebaseMessagingService.getToken(getActivity())));
                }
                break;
            case R.id.btn_delivery_date:
                datePicker();
                break;
            case R.id.btn_locations:
//                viewModel.getMataData().observe(this, response -> {
//                    showDailog(response);
//                });
//                showPlacePicker();
                if (changeLocation.getText().equals(env.getAppbtnpickuplcationpickup()) ) {
                    Utils.replaceFragment(getParentFragment().getFragmentManager(), MyAddressesFragment.newInstance("pickup"), R.id.fragment_container, true, true);

                } else {
                    Utils.replaceFragment(getParentFragment().getFragmentManager(), MyAddressesFragment.newInstance("delivery"), R.id.fragment_container, true, true);
                }
                break;
//            case R.id.generateTicket:
//                Utils.replaceFragment(getParentFragment().getFragmentManager(), TicketFragment.newInstance("" + consignmentStatus.getConsignmentId()), R.id.fragment_container, true, true);
//                break;
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        viewModel.rateAWB(getActivity(), ShipmentDetailsByStatusFragment.this,
                new RateAWBRequest("" + consignmentStatus.getConsignmentId(), "" + rating, FirebaseMessagingService.getToken(getActivity())));

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
                showPlacePicker(adapterView.getItemAtPosition(i).toString());


            }
        });

        searchListDialog.show(getFragmentManager(), null);
    }

    private void datePicker() {


        if(consignmentStatus.getReturnPickupDate() != null) {
            String[] srr = consignmentStatus.getReturnPickupDate().split("-");
            mYear = Integer.parseInt(srr[0]);
            mMonth = Integer.parseInt(srr[1]);
            mDay = Integer.parseInt(srr[2]);
        }else{
            String[] srr = consignmentStatus.getExpectedDeliveryDate().split("-");
            mYear = Integer.parseInt(srr[0]);
            mMonth = Integer.parseInt(srr[1]);
            mDay = Integer.parseInt(srr[2]);
        }
        // Get Current Date
        date_str = mYear + "-" + (mMonth + 1) + "-" + mDay;
        try {
//            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
//            dayFormat.setLenient(false);
//            String day = dayFormat.format(Date.parse(consignmentStatus.getExpectedDeliveryDate()));
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-DD", Locale.ENGLISH);
//            Date theDate = format.parse(consignmentStatus.getExpectedDeliveryDate());
//            final Calendar c = Calendar.getInstance();
//            c.setTime(theDate);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);

        try {
            if (new SimpleDateFormat("yyyy-MM-dd").parse(date_str).after(new Date())) {
                final Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                date_str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                try {

                                    SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                                    Date dt1=format1.parse(date_str);
                                    DateFormat format2=new SimpleDateFormat("EEEE");
                                    String finalDay=format2.format(dt1);

                                    if (new SimpleDateFormat("yyyy-MM-dd").parse(date_str).before(new Date())) {
                                        Toast.makeText(getActivity(), env.getAppenerateticketToast(), Toast.LENGTH_LONG).show();
                                    } else if(finalDay.equalsIgnoreCase("friday")
                                            || finalDay.equalsIgnoreCase("الجمعة")) {
                                        Toast.makeText(getActivity(), env.getAppenerateticketToast(), Toast.LENGTH_LONG).show();
                                    }
//                                    else if(dayOfMonth == mDay+1){
//                                        Toast.makeText(getActivity(), env.getAppenerateticketToast(), Toast.LENGTH_LONG).show();
                                    //}
                                    else{
                                        if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName()) ||
                                                homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())) {
                                            viewModel.updateDate(getActivity(), ShipmentDetailsByStatusFragment.this,
                                                    new UpdatePickupDateRequest("" + consignmentStatus.getConsignmentId(), date_str, "update_pickup_date", FirebaseMessagingService.getToken(getActivity())));
                                        } else {
                                            viewModel.updateDate(getActivity(), ShipmentDetailsByStatusFragment.this,
                                                    new UpdateDateRequest("" + consignmentStatus.getConsignmentId(), date_str, "update_delivery_date", FirebaseMessagingService.getToken(getActivity())));
                                        }
//                                timePicker();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth, mDay+1);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();


            }else{
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                date_str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                try {

                                    SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                                    Date dt1=format1.parse(date_str);
                                    DateFormat format2=new SimpleDateFormat("EEEE");
                                    String finalDay=format2.format(dt1);

                                    if (new SimpleDateFormat("yyyy-MM-dd").parse(date_str).before(new Date())) {
                                        Toast.makeText(getActivity(), env.getAppenerateticketToast(), Toast.LENGTH_LONG).show();
                                    } else if(finalDay.equalsIgnoreCase("friday")) {
                                        Toast.makeText(getActivity(), env.getAppenerateticketToast(), Toast.LENGTH_LONG).show();
                                    }
//                                    if(dayOfMonth == mDay+1){
//                                        Toast.makeText(getActivity(), env.getAppenerateticketToast(), Toast.LENGTH_LONG).show();
//                                    }else
                                        {
                                        if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName()) ||
                                                homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())) {
                                            viewModel.updateDate(getActivity(), ShipmentDetailsByStatusFragment.this,
                                                    new UpdatePickupDateRequest("" + consignmentStatus.getConsignmentId(), date_str, "update_pickup_date", FirebaseMessagingService.getToken(getActivity())));
                                        } else {
                                            viewModel.updateDate(getActivity(), ShipmentDetailsByStatusFragment.this,
                                                    new UpdateDateRequest("" + consignmentStatus.getConsignmentId(), date_str, "update_delivery_date", FirebaseMessagingService.getToken(getActivity())));
                                        }
//                                timePicker();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth-1, mDay+1);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void changeDate(){
        date.setText(date_str);
    }

    private void timePicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.datepicker,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        date.setText(date_str);
                        String min = minute == 0 ? "00" : minute + "";
                        String hour = hourOfDay == 0 ? "00" : hourOfDay + "";
                        webshop.setText(hour + ":" + min + ":00");
                        if (homeStatus.getStatus().equals(selectionListOutgoing.get(0).getName()) ||
                                homeStatus.getStatus().equals(selectionListOutgoing.get(1).getName())) {
                            viewModel.updateDate(getActivity(), ShipmentDetailsByStatusFragment.this,
                                    new UpdatePickupDateRequest("" + consignmentStatus.getConsignmentId(), date_str + " " + hourOfDay + ":" + minute + ":00", "update_pickup_date", FirebaseMessagingService.getToken(getActivity())));
                        } else {
                            viewModel.updateDate(getActivity(), ShipmentDetailsByStatusFragment.this,
                                    new UpdateDateRequest("" + consignmentStatus.getConsignmentId(), date_str + " " + hourOfDay + ":" + minute + ":00", "update_delivery_date", FirebaseMessagingService.getToken(getActivity())));
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
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

    private void showPlacePicker(String s) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        if (getLocationFromAddress(getActivity(), s) != null) {
            LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                    getLocationFromAddress(getActivity(), s)
                    , getLocationFromAddress(getActivity(), s));
            builder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
        }
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQ_CODE);
        } catch (Exception e) {
            Log.e(TAG, e.getStackTrace().toString());
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == LOC_REQ_CODE) {
//            if (resultCode == RESULT_OK) {
//                viewModel.getMataData().observe(this, response -> {
//                    showDailog(response);
//                });
////                showPlacePicker(adapterView.getSelectedItem().toString());
//            }
//        } else if (requestCode == PLACE_PICKER_REQ_CODE) {
//            if (resultCode == RESULT_OK) {
//                place = PlacePicker.getPlace(getActivity(), data);
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
//                    viewModel.updateLocation(getActivity(), ShipmentDetailsByStatusFragment.this,
//                            new UpdateLocationRequest(consignmentStatus.getConsignmentId(), "incoming", "update_delivery_location", ""));
//
//                });
//                addressModels = new ArrayList<>();
//                addressModels.add(new AddressModel("From", consignmentStatus.getConsignorAddress()));
//                addressModels.add(new AddressModel("To", place.getAddress().toString()));
//                ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
//            }
//        }
//    }

    public void setPlace(ShopperAddressItem place) {
        if (place != null) {
//            addressModels = new ArrayList<>();
//            addressModels.add(new AddressModel("From", consignmentStatus.getConsignorAddress()));
//            addressModels.add(new AddressModel("To", place.getAddressHierarchy()));
//            ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
            updatedPlaceObject = place;
            if(viewModel!=null) {
                viewModel.updateLocation(getActivity(), ShipmentDetailsByStatusFragment.this,
                        new UpdateLocationRequest(consignmentStatus.getConsignmentId(), "incoming",
                                "update_delivery_location", "" + place.getAddressId()));
            }
        }
    }

    public void setAddress(){
        addressModels = new ArrayList<>();
        addressModels.add(new AddressModel(env.getAppShipmentDetailsFrom(), consignmentStatus.getConsignorAddress()));
        addressModels.add(new AddressModel(env.getAppShipmentDetailsTo(), updatedPlaceObject.getAddressHierarchy()));
        ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
    }

    public void setPickupPlace(ShopperAddressItem place) {
        if (place != null) {
//            addressModels = new ArrayList<>();
//            addressModels.add(new AddressModel("From", place.getAddressHierarchy()));
//            addressModels.add(new AddressModel("To", consignmentStatus.getConsigneeAddress()));
//            ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
            updatedPlaceObject = place;
            if(viewModel != null) {
                viewModel.updateLocation(getActivity(), ShipmentDetailsByStatusFragment.this,
                        new UpdateLocationRequest(consignmentStatus.getConsignmentId(), "outgoing", "update_pickup_location", "" + place.getAddressId()));
            }


        }
    }


    public void openPDF(String url) {
        if (!url.equalsIgnoreCase("")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + url));
            startActivity(browserIntent);
        } else {
            Toast.makeText(getActivity(), "Unable to download", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shopperAddressItem = null;
    }

    public void updateDate(){
        String currentDate = date.getText().toString();
        String[] arr = currentDate.split("-");

        String inputString = arr[0]+"-"+arr[1]+"-"+Integer.parseInt(arr[2]);
        final Calendar c = Calendar.getInstance();
        int mDay = 0, mYear = 0, mMonth = 0;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = formatter.parse(inputString);
            c.setTime(parsedDate);
            mDay = c.get(Calendar.DAY_OF_MONTH +1);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        date.setText(mYear+"-" + mMonth+"-"+mDay);
    }
}
