package com.moxydemo.ui.cities_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.OneExecutionSingleStateStrategy;

/**
 * Created by Vyacheslav on 05.06.2017.
 */

@StateStrategyType(AddToEndStrategy.class)
public interface CitiesStarView extends MvpView {

    void onUpdateCityLike(City city);

    // FIXME: 05.06.2017
    @StateStrategyType(AddToEndStrategy.class)
    void updateRow(City city);

    // FIXME: 05.06.2017 is it legal?
    @StateStrategyType(OneExecutionSingleStateStrategy.class)
    void clearQueue();
}
