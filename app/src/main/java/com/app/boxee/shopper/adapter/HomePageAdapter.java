package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.fragments.CardsFragment;
import com.app.boxee.shopper.fragments.HelpFragment;
import com.app.boxee.shopper.fragments.LocationFragment;
import com.app.boxee.shopper.fragments.SelectTypeFragment;
import com.app.boxee.shopper.fragments.TrackingFragment;
import com.app.boxee.shopper.utils.EnvUtil;

public class HomePageAdapter extends FragmentStatePagerAdapter {
    private final String tabTitles[];
    private final Context context;
    private Fragment mCurrentFragment;
    private final List<Fragment> fragments;

    public
    HomePageAdapter(FragmentManager fm, Context context, ShopperData shopperData, String CN) {
        super(fm);
        Env env = EnvUtil.getInstance(context);
        //,"Help"
        tabTitles = new String[]{env.getAppHomeTitle(),env.getAppTrackingTitle(),env.getAppLocationsTitle()};
        fragments = new ArrayList<>(4);
        fragments.add(SelectTypeFragment.newInstance(shopperData));
        fragments.add(TrackingFragment.newInstance(false, CN));
        fragments.add(new LocationFragment());
        //fragments.add(new HelpFragment());
        this.context = context;


    }

    @Override
    public int getCount() {
        return tabTitles.length;
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
}

