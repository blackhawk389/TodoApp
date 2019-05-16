package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.fragments.CardsFragment;
import com.app.boxee.shopper.fragments.LocationFragment;
import com.app.boxee.shopper.fragments.OrderHistoryListFragment;
import com.app.boxee.shopper.fragments.OrderStatusByStatusFragment;
import com.app.boxee.shopper.fragments.SelectTypeFragment;
import com.app.boxee.shopper.fragments.TrackingFragment;
import com.app.boxee.shopper.utils.EnvUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryPageAdapter extends FragmentStatePagerAdapter {
    private final String tabTitles[];
    private final Context context;
    private Fragment mCurrentFragment;
    private final List<Fragment> fragments;

    public OrderHistoryPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        Env env = EnvUtil.getInstance(context);
        tabTitles = context.getResources().getStringArray(R.array.statuses);
        fragments = new ArrayList<>();
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));
        fragments.add(OrderHistoryListFragment.newInstance(new SelectModel("delivery","1","outgoing")));

        this.context = context;
    }

    @Override
    public int getCount() {
        return fragments.size();
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

            return tabTitles[position];

    }
}

