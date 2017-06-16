package com.moxydemo.ui.cities_list;

import android.support.v4.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.base.view.HideShowContentView;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.OneExecutionSingleStateStrategy;

import java.util.List;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public interface CitiesListView extends MvpView, HideShowContentView, SwipeRefreshLayout.OnRefreshListener {

    @StateStrategyType(AddToEndStrategy.class)
    void showSwipeRefresh(boolean show);

    @StateStrategyType(AddToEndStrategy.class)
    void fillContent(List<City> list);

    @StateStrategyType(OneExecutionSingleStateStrategy.class)
    void clearContent();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startLoginActivity();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void resetPaginationState();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startFavouritesActivity();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startSearchActivity();
}
