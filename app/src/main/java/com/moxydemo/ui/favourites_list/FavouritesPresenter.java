package com.moxydemo.ui.favourites_list;

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

import timber.log.Timber;

/**
 * Created by Vyacheslav on 06.06.2017.
 */
@InjectViewState
public class FavouritesPresenter extends BasePresenter<FavouritesView> {

    @Inject
    DataManager dataManager;

    public FavouritesPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    void loadData() {

        Timber.i("Load data");

        getViewState().showLoading();

        rxAddSubscription(
                dataManager.loadFavourites()
                        .delay(2, TimeUnit.SECONDS)
                        .compose(RxUtils.applySchedulers())
                        .subscribe(
                                cities -> onLoadDataSuccess(cities),
                                throwable -> {
                                    logException(throwable);
                                    getViewState().showError(R.string.oops_something_went_wrong);
                                }
                        ));

    }


    void onLoadDataSuccess(List<City> data) {
        if (!CollectionUtils.isNullOrEmpty(data)) {
            getViewState().fillContent(data);
            getViewState().showContent();
        } else {
            getViewState().showEmpty(R.string.no_data);
        }
    }

    void adapterEmpty(){
        getViewState().showError(R.string.you_dont_have_any_favourites);
    }
}
