package com.moxydemo.ui.cities_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.data.db.model.City;

/**
 * Created by Vyacheslav on 05.06.2017.
 */

@StateStrategyType(AddToEndStrategy.class)
public interface CitiesStarView extends MvpView {

    void onUpdateCityLike(City city);

    void updateRow(City city);
}
