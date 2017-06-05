package com.moxydemo.ui.cities_list;

import com.moxydemo.data.db.model.City;

import java.util.List;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public interface CitiesListPresenter {

    void loadData();

    void onListToEndScrolled();

    void onDataLoadedSuccess(List<City> cities);

    void addDataToAdapter(List<City> cities);

    void onRefresh();

    int getOffset();

    void incrementPage();

    void resetPage();

    boolean canLoadMore();
}
