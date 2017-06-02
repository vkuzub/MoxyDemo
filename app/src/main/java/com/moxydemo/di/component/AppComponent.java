package com.moxydemo.di.component;

import com.moxydemo.base.BaseMvpViewActivity;
import com.moxydemo.di.module.AppModule;
import com.moxydemo.di.module.UtilsModule;
import com.moxydemo.ui.cities_list.CitiesListPresenterImpl;
import com.moxydemo.ui.login.LoginPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void inject(BaseMvpViewActivity activity);

    void inject(LoginPresenterImpl presenter);
    void inject(CitiesListPresenterImpl presenter);

}
