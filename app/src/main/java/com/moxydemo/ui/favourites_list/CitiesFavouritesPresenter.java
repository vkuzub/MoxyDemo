package com.moxydemo.ui.favourites_list;

import com.arellomobile.mvp.InjectViewState;
import com.moxydemo.App;
import com.moxydemo.base.BasePresenter;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Vyacheslav on 06.06.2017.
 */

@InjectViewState
public class CitiesFavouritesPresenter extends BasePresenter<CitiesFavouritesView> {

    @Inject
    DataManager dataManager;

    public CitiesFavouritesPresenter() {
        App.getAppComponent().inject(this);
    }

    void removeFromFavourites(City city) {
        rxAddSubscription(
                Observable.just(0)
                        .delay(1, TimeUnit.SECONDS)
                        .map(integer -> dataManager.removeFromFavourites(city))
                        .compose(RxUtils.applySchedulers())
                        .subscribe(res -> onRemoveSuccess(res),
                                throwable -> logException(throwable))

        );
    }

    void onRemoveSuccess(City city) {
        getViewState().removeRow(city);
    }

}
