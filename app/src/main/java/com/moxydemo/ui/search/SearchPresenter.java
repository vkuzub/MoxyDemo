package com.moxydemo.ui.search;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.moxydemo.App;
import com.moxydemo.base.BasePresenter;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.db.model.City;
import com.moxydemo.data.db.model.CitySuggestion;
import com.moxydemo.utils.CollectionUtils;
import com.moxydemo.utils.RxUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Vyacheslav on 12.06.2017.
 */
@InjectViewState
public class SearchPresenter extends BasePresenter<SearchView> {

    @Inject
    DataManager dataManager;

    private String prevQuery;

    public SearchPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showContent();
        getViewState().expandSearch();
    }

    public void onSearchTextChanged(String oldQuery, String newQuery) {
        Timber.i(oldQuery + " " + newQuery);
        if (TextUtils.isEmpty(newQuery)) {
            getViewState().clearSearchResult();
        } else {
            loadSuggestions();
        }
    }

    public void loadSuggestions() {
        getViewState().hideSuggestions();
        getViewState().showSuggestionsLoading();
        //defer
        loadSavedSuggestions();
    }

    public void onSuggestionClick(String query) {
        getViewState().setSearchText(query);
        getViewState().hideSuggestions();
        search(query);
    }

    public void search(String query) {
        if (TextUtils.isEmpty(query)) {
            return;
        }
        if (!TextUtils.isEmpty(prevQuery) && prevQuery.equals(query)) {
            return;
        }
        getViewState().hideSuggestionsLoading();
        prevQuery = query;
        getViewState().showLoading();
        saveSearchForRecent(query);
        rxAddSubscription(
                dataManager.loadCoincides(query)
                        .delay(1, TimeUnit.SECONDS)
                        .compose(RxUtils.applySchedulers())
                        .subscribe(cities -> onResultsLoaded(cities), throwable -> logException(throwable)));
    }

    private void onResultsLoaded(List<City> result) {
        Timber.i("Results %s", result.toString());
        getViewState().hideSuggestions();
        if (!CollectionUtils.isNullOrEmpty(result)) {
            getViewState().showSearchResult(result);
        } else {
            getViewState().clearSearchResult();
            getViewState().showNoResults();
        }
        getViewState().showContent();
    }

    private void loadSavedSuggestions() {
        rxAddSubscription(
                dataManager.loadSuggestions()
                        .delay(2, TimeUnit.SECONDS)
                        .compose(RxUtils.applySchedulers()).
                        subscribe(suggestions -> onSuggestionsLoaded(suggestions), throwable -> logException(throwable))
        );
    }

    private void onSuggestionsLoaded(List<CitySuggestion> suggestions) {
        getViewState().hideSuggestionsLoading();
        if (!CollectionUtils.isNullOrEmpty(suggestions)) {
            getViewState().showSuggestions(suggestions);
        }
    }

    private void saveSearchForRecent(String query) {
        dataManager.saveSuggestion(query);
    }
}
