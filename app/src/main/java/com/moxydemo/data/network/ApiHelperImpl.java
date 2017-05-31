package com.moxydemo.data.network;

import com.moxydemo.data.network.model.LoginResponse;
import com.moxydemo.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public class ApiHelperImpl implements ApiHelper {

    Client client;

    @Inject
    public ApiHelperImpl(Client client) {
        this.client = client;
    }

    @Override
    public Observable<LoginResponse> doLogin() {
        return client.doLogin().compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<LoginResponse> doLoginFailed() {
        return client.doLoginFailed().compose(RxUtils.applySchedulers());
    }
}
