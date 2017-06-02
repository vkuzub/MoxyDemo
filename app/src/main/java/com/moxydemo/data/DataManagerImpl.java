package com.moxydemo.data;

import com.moxydemo.data.db.DBHelper;
import com.moxydemo.data.db.model.City;
import com.moxydemo.data.network.ApiHelper;
import com.moxydemo.data.network.model.LoginResponse;
import com.moxydemo.data.prefs.PreferenceHelper;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public class DataManagerImpl implements DataManager {

    private ApiHelper apiHelper;
    private PreferenceHelper preferenceHelper;
    private DBHelper dbHelper;

    @Inject
    public DataManagerImpl(ApiHelper apiHelper, PreferenceHelper preferenceHelper, DBHelper dbHelper) {
        this.apiHelper = apiHelper;
        this.preferenceHelper = preferenceHelper;
        this.dbHelper = dbHelper;
    }

    @Override
    public Observable<LoginResponse> doLogin() {
        return apiHelper.doLogin();
    }

    @Override
    public Observable<LoginResponse> doLoginFailed() {
        return apiHelper.doLoginFailed();
    }

    @Override
    public Observable<List<City>> getCities() {
        return apiHelper.getCities();
    }

    @Override
    public boolean isUserAuthorized() {
        return preferenceHelper.isUserAuthorized();
    }

    @Override
    public void saveUserToken(String token) {
        preferenceHelper.saveUserToken(token);
    }

    @Override
    public void saveCities(List<City> cities) {
        dbHelper.saveCities(cities);
    }

    @Override
    public Observable<List<City>> loadCities() {
        return dbHelper.loadCities();
    }

    @Override
    public void clearCities() {
        dbHelper.clearCities();
    }

    @Override
    public boolean isCitiesAvailable() {
        return dbHelper.isCitiesAvailable();
    }
}
