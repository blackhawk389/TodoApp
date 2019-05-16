package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.AppHomeOption;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.data.response.myAdresses.ShopperAddressItem;
import com.app.boxee.shopper.fragments.CardsFragment;
import com.app.boxee.shopper.fragments.LocationFragment;
import com.app.boxee.shopper.fragments.SelectTypeFragment;
import com.app.boxee.shopper.fragments.ShipmentDetailsByStatusFragment;
import com.app.boxee.shopper.fragments.ShipmentDetailsFragment;
import com.app.boxee.shopper.fragments.ShipmentStatusFragment;
import com.app.boxee.shopper.fragments.ShipmentStatusTrackFragment;
import com.app.boxee.shopper.fragments.TrackingFragment;
import com.app.boxee.shopper.utils.EnvUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailPageAdapter extends FragmentStatePagerAdapter {
    private final List<AppHomeOption> tabTitles;
    private final Context context;
    private Fragment mCurrentFragment;
    private final List<Fragment> fragments;
    private Consignment consignment;

    public OrderDetailPageAdapter(FragmentManager fm, Context context, @Nullable Consignment consign, @Nullable SelectModel homeStatus,
                                  @Nullable ShopperAddressItem addressItem) {
        super(fm);
        tabTitles = EnvUtil.getInstance(context).getAppOrderDetailsOptions();
        fragments = new ArrayList<>(2);
        this.consignment = consign;
        if (homeStatus == null) {
            //tracking flow
            fragments.add(ShipmentDetailsFragment.newInstance(consignment));

        } else {
            //home flow
            fragments.add(ShipmentDetailsByStatusFragment.newInstance(consignment, homeStatus, addressItem));
//            fragments.add(ShipmentStatusFragment.newInstance( consignment_order_status));
        }
        fragments.add(ShipmentStatusTrackFragment.newInstance(consignment));
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles.get(position).getName();

    }


    public void setList() {


    }

    public void setList(Consignment consignment) {
        this.consignment = consignment;
        notifyDataSetChanged();

    }
}

