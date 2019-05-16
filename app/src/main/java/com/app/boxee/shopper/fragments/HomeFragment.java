package com.app.boxee.shopper.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.HomePageAdapter;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    @BindView(R.id.fragment_main)
    ViewPager viewPager;
    @BindView(R.id.main_tabs)
    TabLayout tabLayout;


    private HomePageAdapter pagerAdapter;
    private String[] navLabels;
    private int[] navIconsActive = {
            R.drawable.ic_home_selected,
            R.drawable.ic_track_selected,
            R.drawable.ic_location_selected,
            R.drawable.ic_cards_selected
    };
    private Env env;
    private ShopperData shopperData;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(ShopperData response) {
        Bundle args = new Bundle();
        args.putParcelable("shopperData", response);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setViewPager();
        setStrings();
        listeners();

        return view;
    }


    private void setViewPager() {
        if (getArguments() != null) {
            shopperData = getArguments().getParcelable("shopperData");
        }
        String cn = getActivity().getIntent().getStringExtra("CN");
        if (pagerAdapter == null)
            pagerAdapter = new HomePageAdapter(getChildFragmentManager(), getActivity(), shopperData, cn);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        navLabels = new String[]{env.getAppHomeTitle(), env.getAppTrackingTitle(), env.getAppLocationsTitle(), env.getAppHelpTitle()};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);
            tab_label.setText(navLabels[i]);
            if (Utils.getCurrentLanguage().equals("ar")) {
//            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
                Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
                tab_label.setTypeface(bold);
            }
            tab_icon.setImageResource(navIconsActive[i]);
            tabLayout.getTabAt(i).setCustomView(tab);
            if (i == 0) {
                tab_label.setVisibility(View.VISIBLE);

            }
        }

        if (cn != null) {
            viewPager.setCurrentItem(1);
            getActivity().getIntent().removeCategory("CN");
        }
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
        ((MainActivity) getActivity()).changeTitle(env.getAppHomeTitle(), true);
        ((MainActivity) getActivity()).hideAppBar(false);
        ((MainActivity) getActivity()).setBack(false);


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
        switch (position) {
            case 0:
                ((MainActivity) getActivity()).changeTitle(env.getAppHomeTitle(), true);
                break;
            case 1:
                ((MainActivity) getActivity()).changeTitle(env.getAppTrackingTitle(), true);
                break;
            case 2:
                ((MainActivity) getActivity()).changeTitle(env.getAppLocationsTitle(), false);

                break;
            case 3:
                ((MainActivity) getActivity()).changeTitle(env.getAppHelpTitle(), true);
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tab.getCustomView().findViewById(R.id.nav_label).setVisibility(View.VISIBLE);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getCustomView().findViewById(R.id.nav_label).setVisibility(View.GONE);

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
