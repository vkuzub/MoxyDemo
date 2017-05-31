package com.moxydemo.data.network;

import com.moxydemo.data.network.model.LoginResponse;

import rx.Observable;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public interface ApiHelper {

    Observable<LoginResponse> doLogin();

    Observable<LoginResponse> doLoginFailed();

}
