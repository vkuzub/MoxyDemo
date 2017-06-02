package com.moxydemo.data.db;

import com.moxydemo.data.db.model.City;
import com.moxydemo.data.db.model.DaoSession;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Vyacheslav on 02.06.2017.
 */

public class DBHelperImpl implements DBHelper {

    private DaoSession daoSession;

    @Inject
    public DBHelperImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public void saveCities(List<City> cities) {
        daoSession.getCityDao().saveInTx(cities);
    }

    @Override
    public Observable<List<City>> loadCities() {
        return daoSession.getCityDao().rx().loadAll();
    }

    @Override
    public void clearCities() {
        daoSession.getCityDao().deleteAll();
    }

    @Override
    public boolean isCitiesAvailable() {
        return daoSession.getCityDao().loadAll().size() >= 1;
    }
}