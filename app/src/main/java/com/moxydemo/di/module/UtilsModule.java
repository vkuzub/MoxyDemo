package com.moxydemo.di.module;

import com.moxydemo.data.DataManager;
import com.moxydemo.data.DataManagerImpl;
import com.moxydemo.data.network.ApiHelper;
import com.moxydemo.data.network.ApiHelperImpl;
import com.moxydemo.data.network.Client;
import com.moxydemo.data.network.ServiceGenerator;
import com.moxydemo.utils.ToastUtils;
import com.moxydemo.utils.impl.ToastUtilsImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vyacheslav on 31.05.2017.
 */
@Module
public class UtilsModule {

    @Singleton
    @Provides
    public ToastUtils provideToastUtils(ToastUtilsImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public ServiceGenerator serviceGenerator() {
        return new ServiceGenerator();
    }

    @Singleton
    @Provides
    public Client provideClient(ServiceGenerator serviceGenerator) {
        return serviceGenerator.createService(Client.class);
    }

    @Singleton
    @Provides
    public ApiHelper provideApiHelper(ApiHelperImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public DataManager provideDataManager(DataManagerImpl impl) {
        return impl;
    }

}
