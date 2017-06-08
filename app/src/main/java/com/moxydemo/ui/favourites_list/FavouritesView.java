package com.moxydemo.ui.favourites_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.base.view.HideShowContentView;
import com.moxydemo.data.db.model.City;

import java.util.List;

/**
 * Created by Vyacheslav on 06.06.2017.
 */

public interface FavouritesView extends MvpView, HideShowContentView {


    @StateStrategyType(AddToEndSingleStrategy.class)
    void fillContent(List<City> list);

}
