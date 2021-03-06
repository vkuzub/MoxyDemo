package com.moxydemo.ui.cities_list;

import com.arellomobile.mvp.InjectViewState;
import com.moxydemo.App;
import com.moxydemo.base.BasePresenter;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Vyacheslav on 05.06.2017.
 */
@InjectViewState
public class CitiesStarPresenter extends BasePresenter<CitiesStarView> {

    @Inject
    DataManager dataManager;

    private int dataCount;


    public void setDataCount(int newCount) {
        if (dataCount < newCount)
            dataCount = newCount;
        Timber.i("Data count: %s", newCount);
    }

    public int getDataCount() {
        return dataCount;
    }

    public void resetDataCount() {
        dataCount = 0;
        getViewState().clearQueue();
    }

    public CitiesStarPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void attachView(CitiesStarView view) {
        super.attachView(view);
        Timber.i("Cities star presenter attached %d", this.hashCode());
    }

    void updateLike(City city) {
        rxAddSubscription(
                Observable.just(0)
                        .delay(1, TimeUnit.SECONDS)
                        .map(integer -> dataManager.revertCityLike(city))
                        .compose(RxUtils.applySchedulers())
                        .subscribe(city1 -> onCityUpdated(city),
                                throwable -> logException(throwable))
        );

//        City city1 = new City(city.get_id(), city.getCity(), city.getLl(), !city.getFavourited());
//        onCityUpdated(city1);
    }

    void onCityUpdated(City city) {
        if (city != null) {
            getViewState().updateRow(city);
        }
    }

}
