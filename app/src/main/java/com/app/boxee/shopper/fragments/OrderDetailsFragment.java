package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.OrderDetailPageAdapter;
import com.app.boxee.shopper.adapter.TimeLineAddressAdapter;
import com.app.boxee.shopper.data.AddressModel;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.Status;
import com.app.boxee.shopper.data.request.UpdateLocationRequest;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.data.response.TrackingResponse;
import com.app.boxee.shopper.data.response.myAdresses.ShopperAddressItem;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.TrackingViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment implements View.OnClickListener, MyAddressesFragment.PlaceHelper, ViewPager.OnPageChangeListener {

    @BindView(R.id.fragment_detail_container)
    ViewPager viewPager;


    @BindView(R.id.main_tabs)
    TabLayout tabLayout;
    private OrderDetailPageAdapter pagerAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TrackingViewModel viewModel;
    private OrderDetailsByStatusRespose statusConsignment;
    private Consignment consignment;
    ShopperAddressItem addressItem;
    private Status status;
    Env env;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    public static OrderDetailsFragment newInstance(@Nullable Consignment consignmentFirebaseData, @Nullable OrderDetailsByStatusRespose orderDetailsByStatusRespose, @Nullable SelectModel status, @Nullable ShopperAddressItem addressItem) {

        Bundle args = new Bundle();
        args.putParcelable("consignment", consignmentFirebaseData);
        args.putParcelable("consignment_order_status", orderDetailsByStatusRespose);
        args.putParcelable("home_status", status);
        args.putParcelable("address", addressItem);
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setStrings();
        return view;
    }

//    public void updateRecord(){
//        statusConsignment = (OrderDetailsByStatusRespose) getArguments().getParcelable("consignment_order_status");
//        viewModel.getUpdatedTrackRecord(this, getActivity(), new String[]{statusConsignment.getConsignmentId() + ""});
//        viewModel.getTrackingList().observe(this, response -> updateUI(response));
//    }

    private void setViewPagger() {
        if (getArguments() != null) {
            consignment = (Consignment) getArguments().getParcelable("consignment");
            if (consignment != null) {
                setAdapter(consignment);
            } else {
                statusConsignment = (OrderDetailsByStatusRespose) getArguments().getParcelable("consignment_order_status");
                viewModel.init(this, new String[]{statusConsignment.getConsignmentId() + ""}, getActivity(), env);
                viewModel.getTrackingList().observe(this, response -> updateUI(response));
            }
        }
//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
//                viewPager.setRotationY(180);
//            }
//        }

    }


    private void setAdapter(Consignment consignment) {
        pagerAdapter = new OrderDetailPageAdapter(getChildFragmentManager(), getActivity(), consignment, (SelectModel) getArguments().getParcelable("home_status"), addressItem);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        setFonts();
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
        setViewPagger();
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackingViewModel.class);
    }

    private void updateUI(TrackingResponse response) {
        if (response != null) {
            if (response.getConsignments() != null && response.getConsignments().size() > 0) {
                this.consignment = response.getConsignments().get(0);
                setAdapter(consignment);
            }else{

            }
        }

    }

    private void updateUIadd(TrackingResponse response) {
        if (response != null) {
            if (response.getConsignments() != null && response.getConsignments().size() > 0) {
                this.consignment = response.getConsignments().get(0);
                setAdapterAdd(consignment);
            }
        }

    }

    private void setAdapterAdd(Consignment consignment) {
        pagerAdapter.setList(consignment);
    }

    private void setStrings() {
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppOrderDetailsTitle(), true);
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("viewpager called", "now");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void setPlace(String place) {
        if (place != null) {
            consignment.setConsigneeAddress(place);
//            setAdapter(consignment);
        }
    }

    @Override
    public void setPickupPlace(String place) {
        if (place != null) {
            consignment.setConsignorAddress(place);
//            setAdapter(consignment);
        }
    }

    public void setPlace(ShopperAddressItem place) {
        if (place != null) {
            addressItem = place;
            ShipmentDetailsByStatusFragment.shopperAddressItem = place;
            statusConsignment = (OrderDetailsByStatusRespose) getArguments().getParcelable("consignment_order_status");
//
            //setViewPagger();
            //Toast.makeText(getContext(), "from order detail", Toast.LENGTH_SHORT).show();
            //Fragment fragment = getChildFragmentManager().getFragments().get(0);
//            ((ShipmentDetailsByStatusFragment)fragment).setPlace(place);
        }
    }

    public void setPickupPlace(ShopperAddressItem place) {
        if (place != null) {
            addressItem = place;
            ShipmentDetailsByStatusFragment.shopperAddressItem = place;
        }
    }

    public void updateRecord() {
        viewModel.initAdd(this, new String[]{statusConsignment.getConsignmentId() + ""}, getActivity(), env);
        viewModel.getTrackingList().observe(this, response -> updateUIadd(response));
    }
}