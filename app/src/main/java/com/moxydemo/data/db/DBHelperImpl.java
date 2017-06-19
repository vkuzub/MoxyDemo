package com.moxydemo.data.db;

import android.text.TextUtils;

import com.moxydemo.data.db.model.City;
import com.moxydemo.data.db.model.CityDao;
import com.moxydemo.data.db.model.CitySuggestion;
import com.moxydemo.data.db.model.CitySuggestionDao;
import com.moxydemo.data.db.model.DaoSession;
import com.moxydemo.utils.CollectionUtils;

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
    public Observable<List<City>> loadCitiesLimit(int limit, int offset) {
        return daoSession.getCityDao().queryBuilder().limit(limit).offset(offset).rx().list();
    }

    @Override
    public Observable<List<City>> loadFavourites() {
        return daoSession.getCityDao().queryBuilder().where(CityDao.Properties.Favourited.eq(true)).rx().list();
    }

    @Override
    public Observable<List<City>> loadCoincides(String line) {
        String like = "%" + line + "%";
        return daoSession.getCityDao().queryBuilder().where(CityDao.Properties.City.like(like)).rx().list();
    }

    @Override
    public void saveSuggestion(String query) {
        if (TextUtils.isEmpty(query)) {
            return;
        }
        if (!CollectionUtils.isNullOrEmpty(daoSession.getCitySuggestionDao().queryBuilder().where(CitySuggestionDao.Properties.Query.eq(query)).list())) {
            return;
        }
        int size = daoSession.getCitySuggestionDao().loadAll().size();
        if (size == 3) {
            List<CitySuggestion> suggestions = daoSession.getCitySuggestionDao().loadAll();
            CitySuggestion removeCandidate = suggestions.get(0);
            daoSession.getCitySuggestionDao().delete(removeCandidate);
        }
        CitySuggestion suggestion = new CitySuggestion();
        suggestion.setQuery(query);
        daoSession.getCitySuggestionDao().insert(suggestion);
    }

    @Override
    public Observable<List<CitySuggestion>> loadSuggestionsRx() {
        return daoSession.getCitySuggestionDao().queryBuilder().orderAsc(CitySuggestionDao.Properties.Query).rx().list();
    }

    @Override
    public List<CitySuggestion> loadSuggestions() {
        return daoSession.getCitySuggestionDao().queryBuilder().orderAsc(CitySuggestionDao.Properties.Query).list();
    }

    @Override
    public int citiesCount() {
        return daoSession.getCityDao().loadAll().size();
    }

    @Override
    public void clearCities() {
        daoSession.getCityDao().deleteAll();
    }

    @Override
    public boolean isCitiesAvailable() {
        return daoSession.getCityDao().loadAll().size() >= 1;
    }

    @Override
    public City revertCityLike(City city) {
        city.setFavourited(!city.getFavourited());
        daoSession.update(city);
        return daoSession.getCityDao().load(city.get_id());
    }

    @Override
    public City removeFromFavourites(City city) {
        city.setFavourited(false);
        daoSession.update(city);
        return daoSession.getCityDao().load(city.get_id());
    }
}
