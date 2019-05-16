package com.app.boxee.shopper.fragments;


import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.HomePageAdapter;
import com.app.boxee.shopper.adapter.OrderHistoryPageAdapter;
import com.app.boxee.shopper.data.DataModel;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.utils.EnvUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;


public class OrderHistoryFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    @BindView(R.id.fragment_main)
    ViewPager viewPager;
    @BindView(R.id.main_tabs)
    TabLayout tabLayout;
    private OrderHistoryPageAdapter pagerAdapter;
    private Env env;


    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    public static OrderHistoryFragment newInstance() {

        Bundle args = new Bundle();

        OrderHistoryFragment fragment = new OrderHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViewPager();
        setStrings();
        listeners();
        return view;
    }

    private void setViewPager() {
        if (pagerAdapter == null)
            pagerAdapter = new OrderHistoryPageAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(9);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
//                viewPager.setRotationY(180);
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
//        this.configureViewModel();
    }

//    private void configureViewModel() {
//        updateUI();
//    }
//
//    private void updateUI() {
//
//    }

    private void listeners() {


    }

    private void setStrings() {
        ((MainActivity) getActivity()).setBack(false);
        ((MainActivity) getActivity()).changeTitle("Order History", true);


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

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
