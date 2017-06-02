package com.moxydemo.data.network;

import com.moxydemo.data.db.model.City;
import com.moxydemo.data.network.model.LoginResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public interface ApiHelper {

    Observable<LoginResponse> doLogin();

    Observable<LoginResponse> doLoginFailed();

    Observable<List<City>> getCities();

}
