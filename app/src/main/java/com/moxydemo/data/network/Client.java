package com.moxydemo.data.network;

import com.moxydemo.data.db.model.City;
import com.moxydemo.data.network.model.LoginResponse;

import java.util.List;

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

    @GET("1eds0p")
    Observable<List<City>> getCities();

    @GET("f5zth")
    Observable<List<City>> getNewCities();
}
