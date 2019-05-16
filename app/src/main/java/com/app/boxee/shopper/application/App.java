package com.app.boxee.shopper.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.app.boxee.shopper.BuildConfig;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.di.component.DaggerAppComponent;
import com.app.boxee.shopper.utils.EnvUtil;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.testfairy.TestFairy;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        this.initDagger();
//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/worksans_extrabold.ttf");
        context = getApplicationContext();
        TestFairy.begin(this, "6168a302ab5c7a157e0d10092486426b072c7495");

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    // ---

    private void initDagger(){
        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}
