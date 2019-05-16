package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.OrderStatusAdapter;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.TrackingModel;
import com.app.boxee.shopper.data.response.TrackingResponse;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.view_models.TrackingViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStatusFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.order_status_rv)
    RecyclerView orderStatusRv;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TrackingViewModel viewModel;
    private String[] consignments;
    private Env env;

    public OrderStatusFragment() {
        // Required empty public constructor
    }

    public static OrderStatusFragment newInstance(String[] items) {

        Bundle args = new Bundle();
        args.putStringArray("cns", items);
        OrderStatusFragment fragment = new OrderStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        ButterKnife.bind(this, view);
        setViews();
        setStrings();
        listeners();
        return view;
    }

    private void setViews() {
        if (getArguments() != null) {
            consignments = getArguments().getStringArray("cns");
        }
        orderStatusRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        env = EnvUtil.getEnv(getActivity());
        orderStatusRv.setAdapter(new OrderStatusAdapter(this, null, orderStatusRv, env));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();

    }

    public void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackingViewModel.class);
        viewModel.init(this, this.consignments, getActivity(), env);
        viewModel.getTrackingList().observe(this, response -> updateUI(response));
    }

    private void updateUI(@Nullable TrackingResponse response) {
        if (response != null) {
            ((OrderStatusAdapter) orderStatusRv.getAdapter()).setList(response.getConsignments());
        }
    }

    private void listeners() {

    }

    private void setStrings() {
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppOrderStatusTitle(), true);
//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
////                selectModeRv.setRotationY(180);
//                orderStatusRv.setRotationY(180);
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

}