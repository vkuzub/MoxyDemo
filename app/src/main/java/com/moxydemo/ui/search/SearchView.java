package com.moxydemo.ui.search;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.base.view.HideShowContentView;
import com.moxydemo.data.db.model.City;
import com.moxydemo.data.db.model.CitySuggestion;

import java.util.List;

/**
 * Created by Vyacheslav on 12.06.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SearchView extends MvpView, HideShowContentView {

    void expandSearch();

    void collapseSearch();

    void showSuggestions(List<CitySuggestion> data);

    void hideSuggestions();

    void showSuggestionsLoading();

    void hideSuggestionsLoading();

    void showSearchResult(List<City> data);

    void clearSearchResult();

    void setSearchText(String text);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showNoResults();
}
