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
import com.app.boxee.shopper.adapter.OrderStatusByStatusAdapter;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.OrderByStatusViewModel;
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
public class OrderStatusByStatusFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.order_status_rv)
    RecyclerView orderStatusRv;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private OrderByStatusViewModel viewModel;
    private SelectModel status;
    private Env env;

    public OrderStatusByStatusFragment() {
        // Required empty public constructor
    }

    public static OrderStatusByStatusFragment newInstance(SelectModel selectModel) {

        Bundle args = new Bundle();
        args.putParcelable("status", selectModel);
        OrderStatusByStatusFragment fragment = new OrderStatusByStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_status_by_status, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViews();
        setStrings();
        setFonts();
        listeners();
        return view;
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
        }
    }

    private void setViews() {
        if (getArguments() != null) {
            status = getArguments().getParcelable("status");
        }
        orderStatusRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        orderStatusRv.setAdapter(new OrderStatusByStatusAdapter(this, null, orderStatusRv, env));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();

    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OrderByStatusViewModel.class);
        viewModel.init(status, env, getActivity(), this);
        viewModel.getData().observe(this, resposes -> updateUI(resposes));
    }

    private void updateUI(@Nullable List<OrderDetailsByStatusRespose> trackingModelList) {
        if (trackingModelList != null) {
            ((OrderStatusByStatusAdapter) orderStatusRv.getAdapter()).setList((ArrayList<OrderDetailsByStatusRespose>) trackingModelList, status);
        }
    }

    public void listeners() {

    }

    private void setStrings() {
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppOrderStatusTitle(), true);
        //((MainActivity) getActivity()).changeTitle(status.getStatus(), true);
        Configuration config = getActivity().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                //in Right To Left layout
//                selectModeRv.setRotationY(180);
                //orderStatusRv.setRotationY(180);
            }
        }
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