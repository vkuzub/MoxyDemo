package com.moxydemo.ui.favourites_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.data.db.model.City;

/**
 * Created by Vyacheslav on 06.06.2017.
 */

public interface CitiesFavouritesView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void removeRow(City city);
}
