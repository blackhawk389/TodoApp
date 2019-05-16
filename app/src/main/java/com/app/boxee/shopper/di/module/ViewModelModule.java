package com.app.boxee.shopper.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.app.boxee.shopper.data.AddressModel;
import com.app.boxee.shopper.di.key.ViewModelKey;
import com.app.boxee.shopper.view_models.AddressBookViewModel;
import com.app.boxee.shopper.view_models.FactoryViewModel;
import com.app.boxee.shopper.view_models.LocationViewModel;
import com.app.boxee.shopper.view_models.LoginViewModel;
import com.app.boxee.shopper.view_models.MainViewModel;
import com.app.boxee.shopper.view_models.NumberVerificationViewModel;
import com.app.boxee.shopper.view_models.OrderByStatusViewModel;
import com.app.boxee.shopper.view_models.ProfileViewModel;
import com.app.boxee.shopper.view_models.SelectViewModel;
import com.app.boxee.shopper.view_models.ShipmentDetailsViewModel;
import com.app.boxee.shopper.view_models.SplashViewModel;
import com.app.boxee.shopper.view_models.TicketViewModel;
import com.app.boxee.shopper.view_models.TrackShipmentDetailsViewModel;
import com.app.boxee.shopper.view_models.TrackingViewModel;
import com.app.boxee.shopper.view_models.UserProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel.class)
    abstract ViewModel bindUserProfileViewModel(UserProfileViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(AddressBookViewModel.class)
    abstract ViewModel bindAddressBookViewModel(AddressBookViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(SelectViewModel.class)
    abstract ViewModel bindSelectViewModel(SelectViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(OrderByStatusViewModel.class)
    abstract ViewModel bindOrderStatusViewModel(OrderByStatusViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(NumberVerificationViewModel.class)
    abstract ViewModel bindNumberVerificationViewModel(NumberVerificationViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel.class)
    abstract ViewModel bindLocationViewModel(LocationViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(TrackingViewModel.class)
    abstract ViewModel bindUserTrackingViewModel(TrackingViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(TrackShipmentDetailsViewModel.class)
    abstract ViewModel bindShipmentActivityDetailsViewModel(TrackShipmentDetailsViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(ShipmentDetailsViewModel.class)
    abstract ViewModel bindShipmentDetailsViewModel(ShipmentDetailsViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel repoViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(TicketViewModel.class)
    abstract ViewModel bindTicketViewModel(TicketViewModel repoViewModel);
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
