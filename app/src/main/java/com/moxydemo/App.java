package com.moxydemo;

import android.app.Application;

import com.moxydemo.di.component.AppComponent;
import com.moxydemo.di.component.DaggerAppComponent;
import com.moxydemo.di.module.AppModule;

import timber.log.Timber;

/**
 * Created by Vyacheslav on 22.05.2017.
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
