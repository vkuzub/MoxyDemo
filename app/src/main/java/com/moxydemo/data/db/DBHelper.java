package com.moxydemo.data.db;

import com.moxydemo.data.db.model.City;

import java.util.List;

import rx.Observable;

/**
 * Created by Vyacheslav on 02.06.2017.
 */

public interface DBHelper {

    void saveCities(List<City> cities);

    Observable<List<City>> loadCities();

    void clearCities();

    boolean isCitiesAvailable();
}