package com.app.boxee.shopper.di.component;

import android.app.Application;

import com.app.boxee.shopper.application.App;
import com.app.boxee.shopper.di.module.ActivityModule;
import com.app.boxee.shopper.di.module.AppModule;
import com.app.boxee.shopper.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(modules={ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
