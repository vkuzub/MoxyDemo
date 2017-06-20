package com.moxydemo.ui.details;

import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moxydemo.App;
import com.moxydemo.R;
import com.moxydemo.base.BasePresenter;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Vyacheslav on 20.06.2017.
 */

@InjectViewState
public class DetailsPresenter extends BasePresenter<DetailsView> {

    private long cityId;
    private City city;

    @Inject
    DataManager dataManager;

    public DetailsPresenter(long cityId) {
        this.cityId = cityId;
        App.getAppComponent().inject(this);
    }

    public City getCity() {
        return city;
    }

    private void setCity(City city) {
        this.city = city;
    }

    public long getCityId() {
        return cityId;
    }

    @Override
    public void attachView(DetailsView view) {
        super.attachView(view);
        loadDetails(cityId);
    }

    private void loadDetails(long cityId) {
        Timber.i("Load details " + cityId);
        getViewState().showLoading();
        rxAddSubscription(
                dataManager.loadCity(cityId)
                        .delay(200, TimeUnit.MILLISECONDS)
                        .compose(RxUtils.applySchedulers())
                        .subscribe(city -> onDetailsLoaded(city), throwable -> logException(throwable))
        );
    }

    private void onDetailsLoaded(City city) {
        if (city != null) {
            setCity(city);
            if (city.isFavourited()) {
                getViewState().showFavourite();
            } else {
                getViewState().showNofavourite();
            }
            getViewState().setTitle(city.getCity());
            getViewState().addMarker(getMarkerOptions(city));
            getViewState().moveCam(getLatLng(city), 4);

            getViewState().showContent();
        } else {
            getViewState().showError(R.string.oops_something_went_wrong);
        }
    }

    private LatLng getLatLng(City city) {
        String ll[] = city.getLl().split(",");
        return new LatLng(Double.parseDouble(ll[0]), Double.parseDouble(ll[1]));
    }

    private MarkerOptions getMarkerOptions(City city) {
        return new MarkerOptions().position(getLatLng(city)).title(city.getCity());
    }

    public void onSwitchFavourites() {
        if (city != null) {
            changeFavourite(city);
        }
    }

    private void changeFavourite(City city) {

    }

    private void onFavouritesChanged(City city) {
        setCity(city);
    }
}
