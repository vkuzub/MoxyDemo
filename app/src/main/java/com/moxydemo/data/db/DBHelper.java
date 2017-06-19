package com.moxydemo.data.db;

import com.moxydemo.data.db.model.City;
import com.moxydemo.data.db.model.CitySuggestion;

import java.util.List;

import rx.Observable;

/**
 * Created by Vyacheslav on 02.06.2017.
 */

public interface DBHelper {

    void saveCities(List<City> cities);

    Observable<List<City>> loadCities();

    Observable<List<City>> loadCitiesLimit(int limit, int offset);

    Observable<List<City>> loadFavourites();

    Observable<List<City>> loadCoincides(String line);

    void saveSuggestion(String query);

    Observable<List<CitySuggestion>> loadSuggestionsRx();

    List<CitySuggestion> loadSuggestions();

    int citiesCount();

    void clearCities();

    boolean isCitiesAvailable();

    City revertCityLike(City city);

    City removeFromFavourites(City city);
}
