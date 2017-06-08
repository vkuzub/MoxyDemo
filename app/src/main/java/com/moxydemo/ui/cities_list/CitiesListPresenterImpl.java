package com.moxydemo.ui.cities_list;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.moxydemo.App;
import com.moxydemo.R;
import com.moxydemo.base.BasePresenter;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.CollectionUtils;
import com.moxydemo.utils.RxUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

@InjectViewState
public class CitiesListPresenterImpl extends BasePresenter<CitiesListView> implements CitiesListPresenter {

    private int sleep = 2;

    private int page;
    private int limit = 10;

    @Inject
    DataManager dataManager;

    public CitiesListPresenterImpl() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    @Override
    public void loadData() {
        getViewState().showLoading();

        resetPage();

        rxAddSubscription(
                Observable.just(dataManager.isCitiesAvailable())
                        .flatMap(available -> {
                            if (available) {
                                return dataManager.loadCitiesLimit(limit, getOffset());
                            }
                            return dataManager.getCities().doOnNext(this::saveCitiesToDB).
                                    flatMap(list -> dataManager.loadCitiesLimit(limit, getOffset()));
                        })
                        .delay(sleep, TimeUnit.SECONDS)
                        .compose(RxUtils.applySchedulers())
                        .subscribe(getCommonSubscriber()));
    }

    private Subscriber<List<City>> getCommonSubscriber() {
        return new Subscriber<List<City>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                logException(throwable);
                getViewState().showError(R.string.oops_something_went_wrong);
            }

            @Override
            public void onNext(List<City> cities) {
                onDataLoadedSuccess(cities);
            }
        };
    }

    @Override
    public void onListToEndScrolled() {
        if (canLoadMore()) {

            getViewState().showSwipeRefresh(true);

            rxAddSubscription(dataManager.loadCitiesLimit(limit, getOffset())
                    .delay(sleep, TimeUnit.SECONDS)
                    .compose(RxUtils.applySchedulers())
                    .subscribe(cities -> addDataToAdapter(cities),
                            throwable -> {
                                logException(throwable);
                                getViewState().showError(R.string.oops_something_went_wrong);
                            }));
        }
    }

    @Override
    public void onDataLoadedSuccess(List<City> cities) {
        if (!CollectionUtils.isNullOrEmpty(cities)) {
            incrementPage();
            getViewState().showSwipeRefresh(false);
            getViewState().fillContent(cities);
            getViewState().showContent();
        } else {
            getViewState().showEmpty(R.string.no_data);
        }
    }

    @Override
    public void addDataToAdapter(List<City> cities) {
        getViewState().showSwipeRefresh(false);
        if (!CollectionUtils.isNullOrEmpty(cities)) {
            incrementPage();
            getViewState().fillContent(cities);
        }
    }

    @Override
    public void onRefresh() {
        getViewState().resetPaginationState();
        getViewState().clearContent();
        loadData();
    }

    @Override
    public int getOffset() {
        return page * limit;
    }

    @Override
    public void incrementPage() {
        page++;
    }

    @Override
    public void resetPage() {
        page = 0;
    }

    @Override
    public boolean canLoadMore() {
        int count = dataManager.citiesCount();
        return getOffset() < count;
    }

    private void saveCitiesToDB(List<City> cities) {
        dataManager.saveCities(cities);
    }

    void logOut() {
        getViewState().showLoading();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    TimeUnit.SECONDS.sleep(sleep);
                } catch (InterruptedException e) {
                    logException(e);
                }
                dataManager.saveUserToken("");
                getViewState().startLoginActivity();
                return null;
            }
        }.execute();
    }

    void startFavourites() {
        getViewState().startFavouritesActivity();
    }
}
