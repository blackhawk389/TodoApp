package com.app.boxee.shopper.di.module;


import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.activities.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract SplashActivity contributeSplashActivity();

}
