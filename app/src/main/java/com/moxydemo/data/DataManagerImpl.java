package com.moxydemo.data;

import com.moxydemo.data.network.ApiHelper;
import com.moxydemo.data.network.model.LoginResponse;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public class DataManagerImpl implements DataManager {

    private ApiHelper apiHelper;

    @Inject
    public DataManagerImpl(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public Observable<LoginResponse> doLogin() {
        return apiHelper.doLogin();
    }

    @Override
    public Observable<LoginResponse> doLoginFailed() {
        return apiHelper.doLoginFailed();
    }
}
