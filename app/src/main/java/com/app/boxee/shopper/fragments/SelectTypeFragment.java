package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.SelectTypeAdapter;
import com.app.boxee.shopper.data.AppLocationsOption;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.request.GetShipmentByIdRequest;
import com.app.boxee.shopper.data.response.GetShipmentByIdResponse;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.entity.ShopperIncommingShipment;
import com.app.boxee.shopper.database.entity.ShopperOutgoingShipment;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.SelectViewModel;
import com.app.boxee.shopper.view_models.TrackingViewModel;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTypeFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.select_mode_rv)
    RecyclerView selectModeRv;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.main_content)
    RelativeLayout relativeLayout;
    @BindView(R.id.btn_incoming)
    RadioButton incoming;
    @BindView(R.id.btn_outgoing)
    RadioButton outging;
    private ShopperData shopperData;
    private Env env;
    @BindView(R.id.swap)
    SwipeRefreshLayout pullToRefresh;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SelectViewModel viewModel;
    private GetShipmentByIdResponse response;


    public static SelectTypeFragment newInstance(ShopperData shopperData) {

        Bundle args = new Bundle();
        args.putParcelable("shopperData", shopperData);
        SelectTypeFragment fragment = new SelectTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_type, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViews();
        setFonts();
        setStrings();
        listeners();
        return view;
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            incoming.setTypeface(bold);
            outging.setTypeface(bold);


        }
    }

    private void setViews() {
        if (getArguments() != null) {
            shopperData = getArguments().getParcelable("shopperData");
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        selectModeRv.setLayoutManager(layoutManager);
        selectModeRv.setAdapter(new SelectTypeAdapter(this, createIncomingList(response != null && response.getShopperIncommingShipment() != null ? response.getShopperIncommingShipment() : shopperData.getShopperIncommingShipment()), selectModeRv, env));
        radioGroup.check(R.id.btn_incoming);
        Configuration config = getActivity().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                //in Right To Left layout
                //selectModeRv.setRotationY(180);
                //relativeLayout.setRotationY(180);

            }
        }

    }

    private ArrayList<SelectModel> createIncomingList(ShopperIncommingShipment incommingShipment) {
        ArrayList<SelectModel> list = new ArrayList<>();
        ShopperIncommingShipment incomingObject = incommingShipment;
        if (incomingObject != null) {
            List<AppLocationsOption> options = env.getAppHomeOptionsIncoming();
            list.add(new SelectModel(options.get(0).getName(), incomingObject.getInProgressWithWebshop().toString(), "incoming"));
            list.add(new SelectModel(options.get(1).getName(), incomingObject.getInProgressWithBoxee().toString(), "incoming"));
            list.add(new SelectModel(options.get(2).getName(), incomingObject.getArrivingToday().toString(), "incoming"));
            list.add(new SelectModel(options.get(3).getName(), incomingObject.getHistory().toString(), "incoming"));
            list.add(new SelectModel(options.get(4).getName(), incomingObject.getHoldOnBoxee().toString(), "incoming"));
            list.add(new SelectModel(options.get(5).getName(), incomingObject.getReturnToShipper().toString(), "incoming"));
        }
        return list;
    }

    private ArrayList<SelectModel> createOutgoingList(ShopperOutgoingShipment outgoingShipment) {
        ArrayList<SelectModel> list = new ArrayList<>();
        ShopperOutgoingShipment outgingObject = outgoingShipment;
        if (outgingObject != null) {
            List<AppLocationsOption> options = env.getAppHomeOptionsOutgoing();
            list.add(new SelectModel(options.get(0).getName(), outgingObject.getAwbsGeneratedByWebshopForReturn().toString(), "outgoing"));
            list.add(new SelectModel(options.get(1).getName(), outgingObject.getScheduleMyPickup().toString(), "outgoing"));
            list.add(new SelectModel(options.get(2).getName(), outgingObject.getPickingUpToday().toString(), "outgoing"));
            list.add(new SelectModel(options.get(3).getName(), outgingObject.getPickedUpByBoxee().toString(), "outgoing"));
            list.add(new SelectModel(options.get(4).getName(), outgingObject.getReturnHistory().toString(), "outgoing"));
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectViewModel.class);
        viewModel.init(getActivity(), this, new GetShipmentByIdRequest(shopperData.getShopper().getPhone(), com.app.boxee.shopper.services.FirebaseMessagingService.getToken(getActivity())));
        viewModel.getShipmentByIdResponseMutableLiveData().observe(this, trackingModelList -> updateUI(trackingModelList));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                viewModel.refresh(getActivity(), SelectTypeFragment.this, new GetShipmentByIdRequest(shopperData.getShopper().getPhone(), com.app.boxee.shopper.services.FirebaseMessagingService.getToken(getActivity())));
                viewModel.getShipmentByIdResponseMutableLiveData().observe(SelectTypeFragment.this, trackingModelList -> updateUI(trackingModelList));
            }
        });

    }

    private void updateUI(GetShipmentByIdResponse trackingModelList) {
        if (pullToRefresh.isRefreshing())
            pullToRefresh.setRefreshing(false);
        if (trackingModelList != null) {
            response = trackingModelList;
            if (radioGroup.getCheckedRadioButtonId() == R.id.btn_incoming) {
                ((SelectTypeAdapter) selectModeRv.getAdapter()).setList(
                        createIncomingList(trackingModelList.getShopperIncommingShipment()));
            } else {
                ((SelectTypeAdapter) selectModeRv.getAdapter()).setList(
                        createOutgoingList(trackingModelList.getShopperOutgoingShipment()));
            }

        }
    }

//    private void updateUI() {
//
//    }

    private void listeners() {
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void setStrings() {
        incoming.setText(env.getAppHomeOptions().get(0).getName());
        outging.setText(env.getAppHomeOptions().get(1).getName());

//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
////                selectModeRv.setRotationY(180);
//                relativeLayout.setRotationY(180);
//            }
//        }

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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup.getCheckedRadioButtonId() == R.id.btn_incoming) {
            ((SelectTypeAdapter) selectModeRv.getAdapter()).setList(createIncomingList(response != null ? response.getShopperIncommingShipment() : shopperData.getShopperIncommingShipment()));
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.btn_outgoing) {
            ((SelectTypeAdapter) selectModeRv.getAdapter()).setList(createOutgoingList(response != null ? response.getShopperOutgoingShipment() : shopperData.getShopperOutgoingShipment()));

        }
    }


}
