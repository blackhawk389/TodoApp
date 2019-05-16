package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.adapter.TimeLineAdapter;
import com.app.boxee.shopper.adapter.TimeLineTrackAdapter;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.Orientation;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.data.request.TrackRequest;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.data.response.ShipmentActivitiesTimeline;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.view_models.TrackShipmentDetailsViewModel;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipmentStatusTrackFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.main_content)
    RelativeLayout relativeLayout;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TrackShipmentDetailsViewModel viewModel;
    private TimeLineTrackAdapter mTimeLineAdapter;
    private Consignment consignment;
    @BindView(R.id.barcode)
    TextView barcode;
    Env env;
//    private ArrayList<TimeLineModel> response;
//    private OrderDetailsByStatusRespose consignmentStatus;

    public ShipmentStatusTrackFragment() {
        // Required empty public constructor
    }

    public static ShipmentStatusTrackFragment newInstance(Consignment consignmentFirebaseData) {

        Bundle args = new Bundle();
        args.putParcelable("consignment", consignmentFirebaseData);
        ShipmentStatusTrackFragment fragment = new ShipmentStatusTrackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_status, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViews();
        setStrings();
        listeners();
        return view;
    }

    private void setViews() {
        if (getArguments() != null) {
            consignment = (Consignment) getArguments().getParcelable("consignment");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        Collections.reverse(consignment.getShipmentActivitiesTimeline());
        mTimeLineAdapter = new TimeLineTrackAdapter(consignment.getShipmentActivitiesTimeline(), Orientation.VERTICAL, false);
        recyclerView.setAdapter(mTimeLineAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
//        this.configureViewModel();
    }

//    private void configureViewModel() {
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackShipmentDetailsViewModel.class);
//        viewModel.init(getActivity(),this,new TrackRequest(consignment !=null?consignment.getConsignment_id()+"":consignmentStatus.getConsignmentId()+"","shopper"));
//        viewModel.getTrackingList().observe(this, response ->updateUI(response));
//    }
//    private void updateUI(@Nullable ArrayList<TimeLineModel> response) {
//        if(response != null) {
//            this.response = response;
//            ((TimeLineAdapter) recyclerView.getAdapter()).setList(response);
//        }
//    }


    private void listeners() {
    }

    private void setStrings() {
        barcode.setText(env.getAppdashboardtitle());
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
