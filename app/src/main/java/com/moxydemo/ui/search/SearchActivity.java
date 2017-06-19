package com.moxydemo.ui.search;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.moxydemo.App;
import com.moxydemo.R;
import com.moxydemo.base.BaseMvpViewActivity;
import com.moxydemo.data.db.model.City;
import com.moxydemo.data.db.model.CitySuggestion;
import com.moxydemo.utils.KeyboardUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseMvpViewActivity implements SearchView {

    @BindView(R.id.rvSearchResultsList)
    RecyclerView rvSearchResultsList;
    @BindView(R.id.fsvSearch)
    FloatingSearchView fsvSearch;

    @InjectPresenter
    SearchPresenter searchPresenter;

    @Inject
    KeyboardUtils keyboardUtils;

    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);
        initSearch();
        initResultList();
    }

    private void initSearch() {

        fsvSearch.openMenu(true);

        fsvSearch.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                searchPresenter.onSearchTextChanged(oldQuery, newQuery);
            }
        });

        fsvSearch.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                searchPresenter.onSuggestionClick(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                searchPresenter.search(currentQuery);
            }
        });

        fsvSearch.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {

            @Override
            public void onFocus() {
                searchPresenter.fetchSuggestions();
            }

            @Override
            public void onFocusCleared() {

            }
        });
    }

    void initResultList() {
        searchAdapter = new SearchAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearchResultsList.setLayoutManager(layoutManager);
        rvSearchResultsList.setItemAnimator(new DefaultItemAnimator());
        rvSearchResultsList.setAdapter(searchAdapter);
    }

    @Override
    public void expandSearch() {
//        fsvSearch.openMenu(true);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void collapseSearch() {
//        fsvSearch.closeMenu(false);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void showSuggestions(List<CitySuggestion> data) {
        fsvSearch.swapSuggestions(data);
    }

    @Override
    public void hideSuggestions() {
        fsvSearch.clearSuggestions();
    }

    @Override
    public void showSuggestionsLoading() {
        fsvSearch.showProgress();
    }

    @Override
    public void hideSuggestionsLoading() {
        fsvSearch.hideProgress();
    }

    @Override
    public void showSearchResult(List<City> data) {
        searchAdapter.setData(data);
    }

    @Override
    public void clearSearchResult() {
        searchAdapter.clearData();
    }

    @Override
    public void setSearchText(String text) {
        fsvSearch.setSearchText(text);
    }

    @Override
    public void showNoResults() {
        showMessage("No results");
    }
}
