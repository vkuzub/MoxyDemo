package com.moxydemo.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moxydemo.R;
import com.moxydemo.base.BaseMvpViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailsActivity extends BaseMvpViewActivity implements DetailsView {

    public static final String CITY_ID_PARAM = "CITY_ID";

    @InjectPresenter
    DetailsPresenter detailsPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private GoogleMap googleMap;

    public static Intent getStartIntent(@NonNull Context context, long cityId) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(CITY_ID_PARAM, cityId);
        return intent;
    }

    @ProvidePresenter
    public DetailsPresenter provideDetailsPresenter() {
        return new DetailsPresenter(getIntent().getLongExtra(CITY_ID_PARAM, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        initViews();
        initMap();
    }


    private void initViews() {
        setSupportActionBar(toolbar);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    public void setTitle(String name) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(name);
    }

    @Override
    public void addMarker(MarkerOptions markerOptions) {
        Timber.i("[addMarker]");
        if (googleMap != null) {
            googleMap.addMarker(markerOptions);
        } else {
            Timber.e("google map null");
        }
    }

    @Override
    public void moveCam(LatLng latLng, int zoomLvl) {
        Timber.i("[moveCam]");
        if (googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLvl));
        } else {
            Timber.e("google map null");
        }
    }

    @Override
    protected void onStop() {
        googleMap.clear();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 1));
        super.onStop();
    }

    @Override
    public void onFabFavouriteClick() {
        detailsPresenter.onSwitchFavourites();
    }

    @Override
    public void showFabFavouriteProgress() {

    }

    @Override
    public void hideFabFavouriteProgress() {

    }

    @Override
    public void showFavourite() {
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate_white_18px));
    }

    @Override
    public void showNofavourite() {
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24px));
    }
}
