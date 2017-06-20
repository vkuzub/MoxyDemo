package com.moxydemo.ui.details;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moxydemo.base.view.HideShowContentView;

/**
 * Created by Vyacheslav on 20.06.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailsView extends MvpView,
        HideShowContentView, OnMapReadyCallback {

    void setTitle(String name);

    void addMarker(MarkerOptions markerOptions);

    void moveCam(LatLng latLng, int zoomLvl);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onFabFavouriteClick();

    void showFabFavouriteProgress();

    void hideFabFavouriteProgress();

    void showFavourite();

    void showNofavourite();
}
