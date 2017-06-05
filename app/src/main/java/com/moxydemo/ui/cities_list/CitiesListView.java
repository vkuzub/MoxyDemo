package com.moxydemo.ui.cities_list;

import android.support.v4.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.base.view.HideShowContentView;
import com.moxydemo.data.db.model.City;

import java.util.List;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public interface CitiesListView extends MvpView, HideShowContentView, SwipeRefreshLayout.OnRefreshListener {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void initViews();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onLogOutClick();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onFavouritesClick();

    @StateStrategyType(AddToEndStrategy.class)
    void showSwipeRefresh(boolean show);

    @StateStrategyType(AddToEndStrategy.class)
    void fillContent(List<City> list);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void clearContent();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startLoginActivity();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void resetPaginationState();
}
