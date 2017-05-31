package com.moxydemo.data.network;

import com.moxydemo.data.network.model.LoginResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public interface Client {

    @GET("myu2x")
    Observable<LoginResponse> doLogin();

    @GET("1bdlvt")
    Observable<LoginResponse> doLoginFailed();

}
