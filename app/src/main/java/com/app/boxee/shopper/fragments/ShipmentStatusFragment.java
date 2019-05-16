package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.adapter.SelectTypeAdapter;
import com.app.boxee.shopper.adapter.TimeLineAdapter;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.Orientation;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.data.request.TrackRequest;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.view_models.TrackShipmentDetailsViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipmentStatusFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.main_content)
    RelativeLayout relativeLayout;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TrackShipmentDetailsViewModel viewModel;
    private TimeLineAdapter mTimeLineAdapter;
    //    private ConsignmentFirebaseData consignment;
    private ArrayList<TimeLineModel> response;
    private OrderDetailsByStatusRespose consignmentStatus;

    public ShipmentStatusFragment() {
        // Required empty public constructor
    }

    public static ShipmentStatusFragment newInstance(OrderDetailsByStatusRespose consignment_order_status) {

        Bundle args = new Bundle();
//        args.putParcelable("consignment",consignmentFirebaseData);
        args.putParcelable("consignment_status", consignment_order_status);
        ShipmentStatusFragment fragment = new ShipmentStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_status, container, false);
        ButterKnife.bind(this, view);
        setViews();
        setStrings();
        listeners();
        return view;
    }

    private void setViews() {
//        consignment = (ConsignmentFirebaseData) getArguments().getParcelable("consignment");
        if (getArguments() != null) {
            consignmentStatus = (OrderDetailsByStatusRespose) getArguments().getParcelable("consignment_status");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAdapter(response, Orientation.VERTICAL, false);
        recyclerView.setAdapter(mTimeLineAdapter);
//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
//                relativeLayout.setRotationY(180);
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackShipmentDetailsViewModel.class);
        viewModel.init(getActivity(), this, new TrackRequest(consignmentStatus.getConsignmentId() + "", "shopper"));
        viewModel.getTrackingList().observe(this, response -> updateUI(response));
    }

    private void updateUI(@Nullable ArrayList<TimeLineModel> response) {
        if (response != null) {
            this.response = response;
            ((TimeLineAdapter) recyclerView.getAdapter()).setList(response);
        }
    }


    private void listeners() {
    }

    private void setStrings() {
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


}
