package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import com.app.boxee.shopper.adapter.OrderHistoryListAdapter;
import com.app.boxee.shopper.adapter.OrderStatusByStatusAdapter;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.view_models.OrderByStatusViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryListFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.order_status_rv)
    RecyclerView orderStatusRv;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private OrderByStatusViewModel viewModel;
    private String[] consignments;
    private SelectModel status;
    private Env env;

    public OrderHistoryListFragment() {
        // Required empty public constructor
    }

    public static OrderHistoryListFragment newInstance(SelectModel selectModel) {

        Bundle args = new Bundle();
        args.putParcelable("status",selectModel);
        OrderHistoryListFragment fragment = new OrderHistoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_history_list, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViews();
        setStrings();
        listeners();
        return view;
    }

    private void setViews() {
        status = getArguments().getParcelable("status");
        orderStatusRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        orderStatusRv.setAdapter(new OrderHistoryListAdapter(this, null, orderStatusRv));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();

    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OrderByStatusViewModel.class);
        viewModel.init(status,env, getActivity(), this);
        viewModel.getData().observe(this, resposes -> updateUI(resposes));

    }

    private void updateUI(@Nullable List<OrderDetailsByStatusRespose> trackingModelList) {
        if(trackingModelList !=null) {
            ((OrderHistoryListAdapter) orderStatusRv.getAdapter()).setList((ArrayList<OrderDetailsByStatusRespose>) trackingModelList);
        }
    }

    private void listeners() {

    }

    private void setStrings() {
//        ((MainActivity) getActivity()).setBack(true);
//        ((MainActivity) getActivity()).changeTitle(status.getStatus(), true);
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