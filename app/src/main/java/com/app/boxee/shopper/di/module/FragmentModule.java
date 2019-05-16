package com.app.boxee.shopper.di.module;


import com.app.boxee.shopper.fragments.AddAddressFragment;
import com.app.boxee.shopper.fragments.CardsFragment;
import com.app.boxee.shopper.fragments.DashboardFragment;
import com.app.boxee.shopper.fragments.HomeFragment;
import com.app.boxee.shopper.fragments.LocationFragment;
import com.app.boxee.shopper.fragments.LoginFragment;
import com.app.boxee.shopper.fragments.MapPlacePickerFragment;
import com.app.boxee.shopper.fragments.MyAddressesFragment;
import com.app.boxee.shopper.fragments.NumberVerificationFragment;
import com.app.boxee.shopper.fragments.OrderDetailsFragment;
import com.app.boxee.shopper.fragments.OrderHistoryFragment;
import com.app.boxee.shopper.fragments.OrderHistoryListFragment;
import com.app.boxee.shopper.fragments.OrderStatusByStatusFragment;
import com.app.boxee.shopper.fragments.OrderStatusFragment;
import com.app.boxee.shopper.fragments.ProfileFragment;
import com.app.boxee.shopper.fragments.SelectTypeFragment;
import com.app.boxee.shopper.fragments.ShipmentDetailsByStatusFragment;
import com.app.boxee.shopper.fragments.ShipmentDetailsFragment;
import com.app.boxee.shopper.fragments.ShipmentStatusFragment;
import com.app.boxee.shopper.fragments.ShipmentStatusTrackFragment;
import com.app.boxee.shopper.fragments.TicketFragment;
import com.app.boxee.shopper.fragments.TrackingFragment;
import com.app.boxee.shopper.fragments.UserProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract UserProfileFragment contributeUserProfileFragment();
    @ContributesAndroidInjector
    abstract DashboardFragment contributeDashboardFragment();
    @ContributesAndroidInjector
    abstract NumberVerificationFragment contributeNumberVerificationFragment();
    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();
    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();
    @ContributesAndroidInjector
    abstract OrderStatusByStatusFragment contributeOrderStatusByStatusFragment();
    @ContributesAndroidInjector
    abstract SelectTypeFragment contributeSelectTypeFragment();
    @ContributesAndroidInjector
    abstract TrackingFragment contributeTrackingFragment();
    @ContributesAndroidInjector
    abstract LocationFragment contributeLocationFragment();
    @ContributesAndroidInjector
    abstract CardsFragment contributeCardsFragment();
    @ContributesAndroidInjector
    abstract OrderStatusFragment contributeOrderStatusFragment();
    @ContributesAndroidInjector
    abstract OrderDetailsFragment contributeOrderDetailsFragment();
    @ContributesAndroidInjector
    abstract ShipmentStatusFragment contributeShipmentStatusFragment();
    @ContributesAndroidInjector
    abstract ShipmentDetailsFragment contributeShipmentDetailsFragment();
    @ContributesAndroidInjector
    abstract MapPlacePickerFragment contributeMapPlacePickerFragment();
    @ContributesAndroidInjector
    abstract ShipmentDetailsByStatusFragment contributeShipmentDetailsbyStatusFragment();
    @ContributesAndroidInjector
    abstract OrderHistoryFragment contributeOrderHistoryFragment();
    @ContributesAndroidInjector
    abstract OrderHistoryListFragment contributeOrderHistoryListFragment();
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfiletFragment();
    @ContributesAndroidInjector
    abstract AddAddressFragment contributeAddAddressFragment();
    @ContributesAndroidInjector
    abstract MyAddressesFragment contributeAddAddressBookFragment();
    @ContributesAndroidInjector
    abstract ShipmentStatusTrackFragment contributeShipmentTrackFragment();
    @ContributesAndroidInjector
    abstract TicketFragment contributeTicketFragment();
}
