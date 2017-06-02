package com.moxydemo.ui.cities_list;

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

        rxAddSubscription(
                Observable.just(dataManager.isCitiesAvailable())
                        .flatMap(available -> {
                            if (available) {
                                return dataManager.loadCities();
                            }
                            return dataManager.getCities().doOnNext(this::saveCitiesToDB);
                        })
                        .delay(2, TimeUnit.SECONDS)
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
        getViewState().showSwipeRefresh(true);
    }

    @Override
    public void onDataLoadedSuccess(List<City> cities) {
        if (!CollectionUtils.isNullOrEmpty(cities)) {
            getViewState().showSwipeRefresh(false);
            getViewState().fillContent(cities);
            getViewState().showContent();
        }
    }

    @Override
    public void onRefresh() {
        getViewState().clearContent();
        loadData();
    }

    private void saveCitiesToDB(List<City> cities) {
        dataManager.saveCities(cities);
    }

    void logOut() {
        dataManager.saveUserToken("");
        getViewState().startLoginActivity();
    }
}
